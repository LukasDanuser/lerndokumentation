package main;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

public class Main {

	public static String role;
	public static String username = "";
	public static String adminPw = "";
	private static String password = "";

	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	static JFrame frame;
	static JTextArea textArea;

	public static void main(String[] args) throws IOException {
		DbConnector connector = DbConnector.getInstance();
		connector.connectDB();
		@SuppressWarnings("unused")
		DB db = connector.getDB();
		DBCollection collection = db.getCollection("users");

		String userNameValue = "";
		String passwordValue = "";

		boolean isValidLogin = false;

		JLabel jUserName = new JLabel("User Name");
		JTextField userName = new JTextField();
		JLabel jPassword = new JLabel("Password");
		JTextField password1 = new JPasswordField();
		Object[] ob = { jUserName, userName, jPassword, password1 };
		byte resultLogin = (byte) JOptionPane.showConfirmDialog(null, ob, "Login", JOptionPane.OK_CANCEL_OPTION);

		if (resultLogin == JOptionPane.OK_OPTION) {
			userNameValue = userName.getText();
			passwordValue = password1.getText();
		} else {
			System.exit(1);
		}
		DBCursor results = collection.find(new BasicDBObject("username", userNameValue));

		while (isValidLogin == false) {
			results = collection.find(new BasicDBObject("username", userNameValue));
			if (results.size() != 0) {
				username = results.one().get("username").toString();
				password = results.one().get("password").toString();

				if (checkPassword(passwordValue, password) == true) {
					isValidLogin = true;
					break;
				}
			}
			userName.setText("");
			password1.setText("");
			resultLogin = (byte) JOptionPane.showConfirmDialog(null, ob, "Invalid login", JOptionPane.OK_CANCEL_OPTION);
			if (resultLogin == JOptionPane.OK_OPTION) {
				userNameValue = userName.getText();
				passwordValue = password1.getText();
			} else {
				System.exit(1);
			}
		}

		role = results.one().get("role").toString();
		if (role.equals("admin")) {
			adminPw = password;
		}

		collection = db.getCollection(username);

		byte answer = (byte) JOptionPane.showConfirmDialog(null, "Create new file?", "",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (answer == JOptionPane.YES_OPTION) {
			createFile();
		} else if (answer == JOptionPane.NO_OPTION) {
			openFile();
		} else if (answer == JOptionPane.CANCEL_OPTION) {
			System.exit(1);
		}

		@SuppressWarnings("unused")
		Frame frame = new Frame();

	}

	public static boolean checkPassword(String plaintext, String hashed) {
		return ((org.mindrot.jbcrypt.BCrypt.checkpw(plaintext, hashed)));

	}

	@SuppressWarnings({ "resource", "deprecation" })
	public static void openFile() {

		int WIDTH = (int) screenSize.getWidth();
		int HEIGHT = (int) screenSize.getHeight();
		String files = "";
		MongoClient client = new MongoClient("localhost", 27017);
		DB db = client.getDB("lerndokumentation");
		DBCollection collection = db.getCollection(username);
		DBCursor cursor = collection.find();
		textArea = new JTextArea();
		textArea.setSize(WIDTH - 50, HEIGHT - 100);
		textArea.setBackground(null);
		textArea.setEditable(false);
		textArea.setBorder(null);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFocusable(true);

		while (cursor.hasNext()) {
			DBObject obj = cursor.next();
			files = obj.get("fileName").toString();
			textArea.setText(textArea.getText() + "\n" + files + "\n");
		}
		frame = new JFrame();
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setLayout(new FlowLayout());
		frame.add(textArea);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		Frame.newFile = false;
		Frame.fileName = JOptionPane.showInputDialog(null, "Enter file name", "Open file", JOptionPane.DEFAULT_OPTION);
		String input = Frame.fileName;
		DBCursor query = collection.find(new BasicDBObject("fileName", input));
		try {
			@SuppressWarnings("unused")
			String i = query.one().get("content").toString();
			Frame.path = Frame.fileName;
		} catch (NullPointerException e) {
			boolean exists = false;
			while (exists == false) {
				input = Frame.fileName;
				if (input == null) {
					System.exit(1);
				}
				Frame.fileName = JOptionPane.showInputDialog(null, "Enter file name", "File not found!",
						JOptionPane.WARNING_MESSAGE);
				input = Frame.fileName;
				if (input == null) {
					System.exit(1);
				}
				query = collection.find(new BasicDBObject("fileName", input));

				try {
					@SuppressWarnings("unused")
					String i = query.one().get("content").toString();
					Frame.path = input;
					exists = true;
				} catch (NullPointerException e1) {

				}
			}
		}
		if (input == null) {
			System.exit(1);
		}
		frame.hide();
	}

	@SuppressWarnings({ "resource", "deprecation" })
	private static void createFile() {
		MongoClient client = new MongoClient("localhost", 27017);
		DB db = client.getDB("lerndokumentation");
		DBCollection collection = db.getCollection(username);
		Frame.newFile = true;
		Frame.fileName = JOptionPane.showInputDialog(null, "File name");
		if (Frame.fileName.equals("")) {
			boolean valid = false;
			while (valid == false) {
				Frame.fileName = JOptionPane.showInputDialog(null, "File name", "Invalid!",
						JOptionPane.INFORMATION_MESSAGE);
				if (Frame.fileName == null) {
					System.exit(1);
				}
				if (!Frame.fileName.equals("")) {
					valid = true;
				}
			}
		}
		String input = Frame.fileName;

		if (input == null) {
			System.exit(1);
		}
		DBObject file;
		DBCursor query = collection.find(new BasicDBObject("fileName", input));
		try {
			@SuppressWarnings("unused")
			String i = query.one().get("fileName").toString();
		} catch (NullPointerException e) {
			file = new BasicDBObject("fileName", input).append("content", "");
			collection.insert(file);
		}

	}

}
