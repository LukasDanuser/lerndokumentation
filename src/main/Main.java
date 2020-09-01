package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

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

	public static void main(String[] args) throws IOException {
		boolean isValidUsername = false;
		boolean isValidPassword = false;
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

		String username2 = JOptionPane.showInputDialog(null, "Enter username", "", JOptionPane.INFORMATION_MESSAGE);

		DBCursor results = collection.find(new BasicDBObject("username", username2));

		boolean usernameExists = false;

		if (username2 == null) {
			System.exit(1);
		}

		try {
			username = results.one().get("username").toString();
		} catch (NullPointerException e1) {
			usernameExists = false;
			while (usernameExists == false) {
				if (username2 == null) {
					System.exit(1);
				}
				try {
					if (username2 == null) {
						System.exit(1);
					}
					username2 = JOptionPane.showInputDialog(null, "Enter username", "Username does not exist!",
							JOptionPane.INFORMATION_MESSAGE);
					results = collection.find(new BasicDBObject("username", username2));
					username = results.one().get("username").toString();
					if (username != null && username != "" && username.equals(username2)) {
						isValidUsername = true;
						usernameExists = true;
					}
				} catch (NullPointerException e2) {
					if (username2 == null) {
						System.exit(1);
					}
					usernameExists = false;
					username2 = JOptionPane.showInputDialog(null, "Enter username", "Username does not exist!",
							JOptionPane.INFORMATION_MESSAGE);
					results = collection.find(new BasicDBObject("username", username2));
					try {
						if (username2 == null) {
							System.exit(1);
						}
						username2 = JOptionPane.showInputDialog(null, "Enter username", "Username does not exist!",
								JOptionPane.INFORMATION_MESSAGE);
						results = collection.find(new BasicDBObject("username", username2));
						username = results.one().get("username").toString();
						if (username != null && username != "" && username.equals(username2)) {
							isValidUsername = true;
							usernameExists = true;
						}
					} catch (NullPointerException e3) {
						username = "";
						if (username2 == null) {
							System.exit(1);
						}
					}
					if (username != null && username != "" && username.equals(username2)) {
						isValidUsername = true;
						usernameExists = true;
					}
				}
			}
		}
		usernameExists = true;
		isValidUsername = true;

		boolean isPasswordCorrect = false;

		String password2 = JOptionPane.showInputDialog(null, "Enter password", username,
				JOptionPane.INFORMATION_MESSAGE);

		if (isValidUsername == true) {
			String password = results.one().get("password").toString();

			if (password2 == null) {
				System.exit(1);
			}

			if (!password2.equals(password)) {
				isPasswordCorrect = false;
				while (isPasswordCorrect == false) {
					password2 = JOptionPane.showInputDialog(null, "Invalid password!", username,
							JOptionPane.INFORMATION_MESSAGE);
					if (password2 == null) {
						System.exit(1);
					}
					if (!password2.equals(password)) {
						password2 = JOptionPane.showInputDialog(null, "Invalid password!", username,
								JOptionPane.INFORMATION_MESSAGE);
						if (password2 == null) {
							System.exit(1);
						}
						if (password.equals(password2)) {
							isPasswordCorrect = true;
							isValidPassword = true;
						}
					}

					if (password.equals(password2)) {
						isPasswordCorrect = true;
						isValidPassword = true;
					}

					if (password2 == null) {
						System.exit(1);
					}

				}

			} else if (password2.equals(password)) {
				isValidPassword = true;
			}
		}

		if (isValidPassword == false && isValidUsername == false) {
			JOptionPane.showMessageDialog(null, "Username or password invalid!", "Login Error!",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

		role = results.one().get("role").toString();

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
			DBCursor query = collection.find(new BasicDBObject("fileName", input ));
			try {
				@SuppressWarnings("unused")
				String i = query.one().get("fileName").toString();
				collection.remove(query.getQuery());
			} catch (NullPointerException e) {

			}

			file = new BasicDBObject("fileName", input ).append("content", "");
			collection.insert(file);
		} else if (answer == JOptionPane.NO_OPTION) {
			Frame.newFile = false;
			Frame.fileName = JOptionPane.showInputDialog(null, "Enter file name", "Open file",
					JOptionPane.DEFAULT_OPTION);
			String input = Frame.fileName;

			if (input == null) {
				System.exit(1);
			}
		} else if (answer == JOptionPane.CANCEL_OPTION) {
			System.exit(1);
		}

		if (Frame.newFile == false && Files.exists(Paths.get(Frame.fileName )) == false) {
			boolean exists = false;
			while (exists == false) {
				Frame.fileName = JOptionPane.showInputDialog(null, "Enter file name", "File not found!",
						JOptionPane.WARNING_MESSAGE);

				String input = Frame.fileName;

				if (input == null) {
					System.exit(1);
				}

				if (Frame.newFile == false && Files.exists(Paths.get(Frame.fileName )) == true) {
					exists = true;
				}
			}
		}

		@SuppressWarnings("unused")
		Frame frame = new Frame();

	}

}
