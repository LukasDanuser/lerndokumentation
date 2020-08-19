package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

public class Main {

	public static String role;
	
	public static void main(String[] args) throws IOException {
		Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
		mongoLogger.setLevel(Level.SEVERE);
		@SuppressWarnings("resource")
		MongoClient client = new MongoClient("localhost", 27017);
		@SuppressWarnings("deprecation")
		DB db = client.getDB("lerndokumentation");

		DBCollection collection = db.getCollection("users");

		String username2 = JOptionPane.showInputDialog(null, "Enter username", "", JOptionPane.INFORMATION_MESSAGE);

		DBCursor results = collection.find(new BasicDBObject("username", username2));

		String username = "";
		boolean usernameExists = false;

		if (username2 == null) {
			System.exit(1);
		}

		try {
			username = results.one().get("username").toString();
		} catch (NullPointerException e1) {
			usernameExists = false;
			while (usernameExists == false) {
				try {
					username2 = JOptionPane.showInputDialog(null, "Enter username", "Username does not exist!",
							JOptionPane.INFORMATION_MESSAGE);
					results = collection.find(new BasicDBObject("username", username2));
					username = results.one().get("username").toString();
				} catch (NullPointerException e2) {
					usernameExists = false;
					username2 = JOptionPane.showInputDialog(null, "Enter username", "Username does not exist!",
							JOptionPane.INFORMATION_MESSAGE);
					results = collection.find(new BasicDBObject("username", username2));
					username = results.one().get("username").toString();
					if (username != null) {
						usernameExists = true;
					}
				}
			}
		}

		boolean isPasswordCorrect = false;

		String password2 = JOptionPane.showInputDialog(null, "Enter password", "", JOptionPane.INFORMATION_MESSAGE);
		String password = results.one().get("password").toString();

		if (password2 == null) {
			System.exit(1);
		}

		if (!password2.equals(password)) {
			isPasswordCorrect = false;
			while (isPasswordCorrect == false) {
				password2 = JOptionPane.showInputDialog(null, "Enter password", "Invalid password!",
						JOptionPane.INFORMATION_MESSAGE);

				if (!password2.equals(password)) {
					password2 = JOptionPane.showInputDialog(null, "Enter password", "Invalid password!",
							JOptionPane.INFORMATION_MESSAGE);

					if (password.equals(password2)) {
						isPasswordCorrect = true;
					}
				}

				if (password.equals(password2)) {
					isPasswordCorrect = true;
				}

				if (password2 == null) {
					System.exit(1);
				}

			}

		}
		role = results.one().get("role").toString();

		int answer = JOptionPane.showConfirmDialog(null, "Create new file?", "", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		if (answer == 0) {
			Frame.newFile = true;
			Frame.fileName = JOptionPane.showInputDialog(null, "File name");
			String input = Frame.fileName;

			if (input == null) {
				System.exit(1);
			}
			if (Files.exists(Paths.get(Frame.fileName + ".txt")) == false) {
				Frame.path = Frame.fileName + ".txt";
				Path fileToCreatePath = Paths.get(Frame.path);
				System.out.println("File to create path: " + fileToCreatePath);
				Path newFilePath = Files.createFile(fileToCreatePath);
				System.out.println("New file created: " + newFilePath);
				System.out.println("New File exits: " + Files.exists(newFilePath));
			}
		} else if (answer == 1) {
			Frame.newFile = false;
			Frame.fileName = JOptionPane.showInputDialog(null, "Enter file name", "Open file",
					JOptionPane.DEFAULT_OPTION);
			String input = Frame.fileName;

			if (input == null) {
				System.exit(1);
			}
		} else if (answer == 2) {
			System.exit(1);
		}

		if (Frame.newFile == false && Files.exists(Paths.get(Frame.fileName + ".txt")) == false) {
			boolean exists = false;
			while (exists == false) {
				Frame.fileName = JOptionPane.showInputDialog(null, "Enter file name", "File not found!",
						JOptionPane.WARNING_MESSAGE);

				String input = Frame.fileName;

				if (input == null) {
					System.exit(1);
				}

				if (Frame.newFile == false && Files.exists(Paths.get(Frame.fileName + ".txt")) == true) {
					exists = true;
				}
			}
		}

		@SuppressWarnings("unused")
		Frame frame = new Frame();

	}

}
