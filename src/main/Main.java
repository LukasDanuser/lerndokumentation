package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class Main {

	public static String role;
	public static String username = "";
	public static String adminPw = "";
	private static String password = "";

	public static void main(String[] args) throws IOException {
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		@SuppressWarnings("unused")
		MongoClientURI uri = new MongoClientURI(
				"mongodb://lukas:secret.8@cluster0-shard-00-00.ez8ii.mongodb.net:27017,cluster0-shard-00-01.ez8ii.mongodb.net:27017,cluster0-shard-00-02.ez8ii.mongodb.net:27017/lerndokumentation?ssl=true&replicaSet=atlas-atekpy-shard-0&authSource=admin&retryWrites=true&w=majority");
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient("localhost", 27017);
//		MongoClient client = new MongoClient(uri);

		@SuppressWarnings("deprecation")
		DB db = client.getDB("lerndokumentation");

		DBCollection collection = db.getCollection("users");

		String userNameValue = "";
		String passwordValue = "";

		boolean isValidLogin = false;

		JLabel jUserName = new JLabel("User Name");
		JTextField userName = new JTextField();
		JLabel jPassword = new JLabel("Password");
		JTextField password1 = new JPasswordField();
		Object[] ob = { jUserName, userName, jPassword, password1 };
		int resultLogin = JOptionPane.showConfirmDialog(null, ob, "Login", JOptionPane.OK_CANCEL_OPTION);

		if (resultLogin == JOptionPane.OK_OPTION) {
			userNameValue = userName.getText();
			passwordValue = password1.getText();
		} else {
			System.exit(1);
		}
		DBCursor results = collection.find(new BasicDBObject("username", userNameValue));

		try {
			username = results.one().get("username").toString();
			password = results.one().get("password").toString();
			if (checkPassword(passwordValue, password) == false) {
				isValidLogin = false;
				userName.setText("");
				password1.setText("");
				while (isValidLogin == false) {

					resultLogin = JOptionPane.showConfirmDialog(null, ob, "Invalid login",
							JOptionPane.OK_CANCEL_OPTION);

					if (resultLogin == JOptionPane.OK_OPTION) {
						userNameValue = userName.getText();
						passwordValue = password1.getText();
					} else {
						System.exit(1);
					}
					if (checkPassword(passwordValue, password) == true) {
						isValidLogin = true;
					}
					try {
						username = results.one().get("username").toString();
						password = results.one().get("password").toString();
					} catch (NullPointerException e1) {
						userName.setText("");
						password1.setText("");
						resultLogin = JOptionPane.showConfirmDialog(null, ob, "Invalid login",
								JOptionPane.OK_CANCEL_OPTION);

						if (resultLogin == JOptionPane.OK_OPTION) {
							userNameValue = userName.getText();
							passwordValue = password1.getText();
						} else {
							System.exit(1);
						}
						if (checkPassword(passwordValue, password) == true) {
							isValidLogin = true;
						}
					}
				}
			}
		} catch (NullPointerException e) {
			isValidLogin = false;

			while (isValidLogin == false) {
				userName.setText("");
				password1.setText("");
				resultLogin = JOptionPane.showConfirmDialog(null, ob, "Invalid login", JOptionPane.OK_CANCEL_OPTION);

				if (resultLogin == JOptionPane.OK_OPTION) {
					userNameValue = userName.getText();
					passwordValue = password1.getText();
				} else {
					System.exit(1);
				}
				try {
					results = collection.find(new BasicDBObject("username", userNameValue));
					username = results.one().get("username").toString();
					password = results.one().get("password").toString();
					if (checkPassword(passwordValue, password) == true) {
						isValidLogin = true;
					}
				} catch (NullPointerException e1) {
					userName.setText("");
					password1.setText("");
					resultLogin = JOptionPane.showConfirmDialog(null, ob, "Invalid login",
							JOptionPane.OK_CANCEL_OPTION);

					if (resultLogin == JOptionPane.OK_OPTION) {
						userNameValue = userName.getText();
						passwordValue = password1.getText();
					} else {
						System.exit(1);
					}
					if (checkPassword(passwordValue, password) == true) {
						isValidLogin = true;
					}
				}
			}

		}

		role = results.one().get("role").toString();
		if (role.equals("admin")) {
			adminPw = password;
		}

		collection = db.getCollection(username);

		int answer = JOptionPane.showConfirmDialog(null, "Create new file?", "", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (answer == JOptionPane.YES_OPTION) {
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
			collection = db.getCollection(username);
			DBCursor query = collection.find(new BasicDBObject("fileName", input));
			try {
				@SuppressWarnings("unused")
				String i = query.one().get("fileName").toString();
			} catch (NullPointerException e) {
				file = new BasicDBObject("fileName", input).append("content", "");
				collection.insert(file);
			}

		} else if (answer == JOptionPane.NO_OPTION) {
			Frame.newFile = false;
			Frame.fileName = JOptionPane.showInputDialog(null, "Enter file name", "Open file",
					JOptionPane.DEFAULT_OPTION);
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
		} else if (answer == JOptionPane.CANCEL_OPTION) {
			System.exit(1);
		}

		@SuppressWarnings("unused")
		Frame frame = new Frame();

	}

	public static boolean checkPassword(String plaintext, String hashed) {
		if (org.mindrot.jbcrypt.BCrypt.checkpw(plaintext, hashed)) {
			return true;
		} else {
			return false;
		}
	}

}
