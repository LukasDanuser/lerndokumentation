package main;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import main.Frame;

public class Frame implements ActionListener {

	MongoClientURI uri = new MongoClientURI(
			"mongodb://lukas:secret.8@cluster0-shard-00-00.ez8ii.mongodb.net:27017,cluster0-shard-00-01.ez8ii.mongodb.net:27017,cluster0-shard-00-02.ez8ii.mongodb.net:27017/lerndokumentation?ssl=true&replicaSet=atlas-atekpy-shard-0&authSource=admin&retryWrites=true&w=majority");
//	MongoClient client = new MongoClient("localhost", 27017);
	MongoClient client = new MongoClient(uri);
	@SuppressWarnings("deprecation")
	DB db = client.getDB("lerndokumentation");
	DBCollection collection = db.getCollection("users");

	public static boolean newFile;
	public static String fileName;
	public static String path;

	int WIDTH = 700;
	int HEIGHT = 700;

	public static JFrame frame;
	JButton buttonSave;
	JButton buttonClear;
	JButton buttenNewUser;
	JButton buttonRefresh;
	JButton buttonSearch;
	JButton buttonChangeFile;
	JButton buttonDelete;
	JTextField textField;
	static JTextArea textArea;

	public Frame() throws IOException {

		textArea = new JTextArea();
		textArea.setSize(WIDTH - 50, HEIGHT - 100);
		textArea.setBackground(null);
		textArea.setEditable(true);
		textArea.setBorder(null);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFocusable(true);

		textField = new JTextField(50);
		textField.addActionListener(this);

		buttonSave = new JButton("Save");
		buttonSave.setBounds(150, 50, 120, 60);
		buttonSave.addActionListener(this);
		buttonSave.setBackground(new Color(125, 125, 125));
		buttonSave.setFont(new Font("Comic Sans", Font.ITALIC, 15));
		buttonSave.setForeground(new Color(250, 250, 250));
		buttonSave.setBorder(BorderFactory.createEtchedBorder());
		buttonSave.setFocusable(false);

		buttenNewUser = new JButton("New User");
		buttenNewUser.setBounds(150, 50, 120, 60);
		buttenNewUser.addActionListener(this);
		buttenNewUser.setBackground(new Color(125, 125, 125));
		buttenNewUser.setFont(new Font("Comic Sans", Font.ITALIC, 15));
		buttenNewUser.setForeground(new Color(250, 250, 250));
		buttenNewUser.setBorder(BorderFactory.createEtchedBorder());
		buttenNewUser.setFocusable(false);

		buttonClear = new JButton("Clear File");
		buttonClear.setBounds(150, 50, 120, 60);
		buttonClear.addActionListener(this);
		buttonClear.setBackground(new Color(125, 125, 125));
		buttonClear.setFont(new Font("Comic Sans", Font.ITALIC, 15));
		buttonClear.setForeground(new Color(250, 250, 250));
		buttonClear.setBorder(BorderFactory.createEtchedBorder());
		buttonClear.setFocusable(false);

		buttonRefresh = new JButton("Refresh");
		buttonRefresh.setBounds(150, 50, 120, 60);
		buttonRefresh.addActionListener(this);
		buttonRefresh.setBackground(new Color(125, 125, 125));
		buttonRefresh.setFont(new Font("Comic Sans", Font.ITALIC, 15));
		buttonRefresh.setForeground(new Color(250, 250, 250));
		buttonRefresh.setBorder(BorderFactory.createEtchedBorder());
		buttonRefresh.setFocusable(false);

		buttonSearch = new JButton("Search");
		buttonSearch.setBounds(150, 50, 120, 60);
		buttonSearch.addActionListener(this);
		buttonSearch.setBackground(new Color(125, 125, 125));
		buttonSearch.setFont(new Font("Comic Sans", Font.ITALIC, 15));
		buttonSearch.setForeground(new Color(250, 250, 250));
		buttonSearch.setBorder(BorderFactory.createEtchedBorder());
		buttonSearch.setFocusable(false);

		buttonChangeFile = new JButton("Change file");
		buttonChangeFile.setBounds(150, 50, 120, 60);
		buttonChangeFile.addActionListener(this);
		buttonChangeFile.setBackground(new Color(125, 125, 125));
		buttonChangeFile.setFont(new Font("Comic Sans", Font.ITALIC, 15));
		buttonChangeFile.setForeground(new Color(250, 250, 250));
		buttonChangeFile.setBorder(BorderFactory.createEtchedBorder());
		buttonChangeFile.setFocusable(false);

		buttonDelete = new JButton("Delete file");
		buttonDelete.setBounds(150, 50, 120, 60);
		buttonDelete.addActionListener(this);
		buttonDelete.setBackground(new Color(125, 125, 125));
		buttonDelete.setFont(new Font("Comic Sans", Font.ITALIC, 15));
		buttonDelete.setForeground(new Color(250, 250, 250));
		buttonDelete.setBorder(BorderFactory.createEtchedBorder());
		buttonDelete.setFocusable(false);

		frame = new JFrame(path);
		frame.setSize(new Dimension(700, 700));
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(buttonSave);
		if (Main.role.equals("admin")) {
			frame.add(buttenNewUser);
		}
		frame.add(buttonClear);
		frame.add(buttonRefresh);
		frame.add(buttonSearch);
		frame.add(buttonChangeFile);
		frame.add(buttonDelete);
		frame.add(textField);
		frame.add(textArea);
		frame.setVisible(true);

		WIDTH = frame.getWidth();
		HEIGHT = frame.getHeight();

		path = fileName;
		frame.setTitle(path);
		collection = db.getCollection(Main.username);
		DBCursor query = collection.find(new BasicDBObject("fileName", path));
		textArea.setText(query.one().get("content").toString());

	}

	public void changeFile() throws IOException {
		DBCursor query = collection.find(new BasicDBObject("fileName", path));

		newFile = false;
		String fileName2 = fileName;
		fileName = JOptionPane.showInputDialog(null, "Enter file name", "Open file", JOptionPane.DEFAULT_OPTION);

		if (fileName == null) {
			fileName = fileName2;
			return;
		}

		if (newFile == false) {

			query = collection.find(new BasicDBObject("fileName", fileName));
			try {
				@SuppressWarnings("unused")
				String i = query.one().get("content").toString();
				path = fileName;
				textArea.setText(query.one().get("content").toString());
				frame.setTitle(fileName);
			} catch (NullPointerException e) {
				boolean exists = false;
				while (exists == false) {
					fileName = JOptionPane.showInputDialog(null, "Enter file name", "File not found!",
							JOptionPane.WARNING_MESSAGE);

					try {
						@SuppressWarnings("unused")
						String i = query.one().get(fileName).toString();
						exists = true;
					} catch (NullPointerException e1) {

					}
				}
			}

		}
		path = fileName;
		textArea.setText(query.one().get("content").toString());
		frame.setTitle(path);
	}

	public void createNewFile(String name) throws IOException {
		path = name;
		DBObject file;
		collection = db.getCollection(Main.username);
		DBCursor query = collection.find(new BasicDBObject("fileName", path));
		try {
			@SuppressWarnings("unused")
			String i = query.one().get("fileName").toString();
			collection.remove(query.getQuery());
		} catch (NullPointerException e) {

		}

		file = new BasicDBObject("fileName", path).append("content", "");
		collection.insert(file);
		textArea.setText(query.one().get("content").toString());
		frame.setTitle(fileName);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		DBCursor query = collection.find(new BasicDBObject("fileName", path));
		boolean isNormal = true;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();

		String text = textField.getText();
		boolean isFieldEmpty = false;
		boolean isFileEmpty = false;
		if (textField.getText().equals("")) {
			isFieldEmpty = true;
		}
		path = fileName;

		if (event.getSource() == buttenNewUser) {
			collection = db.getCollection("users");

			String username = "";
			String[] options = { "Admin", "Default", "Cancel" };
			int option = JOptionPane.showOptionDialog(null, "Role", "", JOptionPane.DEFAULT_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			String role = "";

			if (option == 0) {
				role = "admin";
			} else if (option == 1) {
				role = "default";
			} else if (option == 2) {

			}
			if (option != 2) {
				boolean isValid = false;
				boolean unTaken = false;

				username = JOptionPane.showInputDialog(null, "Enter username", "", JOptionPane.DEFAULT_OPTION);

				DBCursor results = collection.find(new BasicDBObject("username", username));
				String usernameDB = "";
				try {
					usernameDB = results.one().get("username").toString();
					if (username.equals(usernameDB)) {
						unTaken = true;
						while (unTaken == true) {
							username = JOptionPane.showInputDialog(null, "Enter username", "Username taken!",
									JOptionPane.DEFAULT_OPTION);
							if (!username.equals(usernameDB)) {
								unTaken = false;
							}
						}
					}
				} catch (NullPointerException e) {
					isNormal = false;
					usernameDB = "";
				}

				if (isNormal) {
					if (username == null) {
						isValid = true;
					}

					if (isValid == false && username.equals("")) {
						isValid = false;
						while (isValid == false) {
							if (username == null) {
								break;
							}
							username = JOptionPane.showInputDialog(null, "Enter username", "Invalid!",
									JOptionPane.DEFAULT_OPTION);
							if (username == null) {
								break;
							}
							if (username.equals("")) {
								username = JOptionPane.showInputDialog(null, "Enter username", "Invalid!",
										JOptionPane.DEFAULT_OPTION);
								if (username == null) {
									break;
								}
							}

							if (!username.equals("")) {
								isValid = true;
							}

						}
					} else {
						isValid = true;
					}
					String password = "";
					if (username != null) {
						password = JOptionPane.showInputDialog(null, "Enter new password", "",
								JOptionPane.DEFAULT_OPTION);
					}

					if (password == null) {
						isValid = true;
					}

					if (isValid == false && password.equals("")) {
						isValid = false;
						while (isValid == false) {
							if (password == null) {
								break;
							}
							password = JOptionPane.showInputDialog(null, "Enter new password", "Invalid!",
									JOptionPane.DEFAULT_OPTION);
							if (password == null) {
								break;
							}
							if (password.equals("")) {
								password = JOptionPane.showInputDialog(null, "Enter new password", "Invalid!",
										JOptionPane.DEFAULT_OPTION);
								if (password == null) {
									break;
								}
							}

							if (!password.equals("")) {
								isValid = true;
							}

						}
					} else {
						isValid = true;
					}

					if (isValid && username != null && password != null) {
						DBObject user = new BasicDBObject("username", username).append("password", password)
								.append("role", role);
						collection.insert(user);
						JOptionPane.showMessageDialog(null, "User added", "New user", JOptionPane.INFORMATION_MESSAGE);
					}

					DBObject dbo = new BasicDBObject("", "");
					db.createCollection(username, dbo);

				}

			}

		}

		if (event.getSource() == buttonChangeFile) {
			try {
				if (JOptionPane.showConfirmDialog(null, "Create new file?", "", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					newFile = true;
					String name = JOptionPane.showInputDialog(null, "File name");

					if (name == null) {

					} else {
						fileName = name;
						createNewFile(fileName);
					}

				} else {
					newFile = false;
					changeFile();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (event.getSource() == buttonDelete) {
			if (JOptionPane.showConfirmDialog(null, "This action can't be undo!\n Do you want to proceed",
					"Delete file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				try {
					collection.remove(query.getQuery());
					textArea.setText("");
					collection = db.getCollection(Main.username);
					try {
						@SuppressWarnings("unused")
						String i = query.one().get("fileName").toString();
						collection.remove(query.getQuery());
					} catch (NullPointerException e) {

					}
					int input = JOptionPane.showConfirmDialog(null, "Create new file?", "",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (input == JOptionPane.YES_OPTION) {
						newFile = true;
						fileName = JOptionPane.showInputDialog(null, "File name");

						if (fileName == null) {
							System.exit(1);
						}
						createNewFile(fileName);
					} else if (input == JOptionPane.NO_OPTION) {
						newFile = false;
						changeFile();
					} else if (input == JOptionPane.CANCEL_OPTION) {
						System.exit(1);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (event.getSource() == buttonSave) {
			DBObject file;
			collection = db.getCollection(Main.username);
			try {
				@SuppressWarnings("unused")
				String i = query.one().get("fileName").toString();
				collection.remove(query.getQuery());
			} catch (NullPointerException e) {

			}

			file = new BasicDBObject("fileName", path).append("content", textArea.getText());
			collection.insert(file);

		}

		if (event.getSource() == textField) {

			if (!query.one().get("content").toString().equals("")) {
				isFileEmpty = false;
			} else {
				isFileEmpty = true;
			}

			if (isFieldEmpty == true) {
				query = collection.find(new BasicDBObject("fileName", path));
				if (isFileEmpty == true) {

					textArea.setText(textArea.getText() + text);
				} else {

					textArea.setText(textArea.getText() + text);
				}

			} else if (isFieldEmpty == false) {
				if (isFileEmpty == true) {

					textArea.setText(textArea.getText() + formatter.format(date) + ": " + text);
				} else {

					textArea.setText(textArea.getText() + "\n\n" + formatter.format(date) + ": " + text);

				}

			}

			DBObject file;
			collection = db.getCollection(Main.username);
			query = collection.find(new BasicDBObject("fileName", path));
			try {
				@SuppressWarnings("unused")
				String i = query.one().get("fileName").toString();
				collection.remove(query.getQuery());
			} catch (NullPointerException e) {

			}

			file = new BasicDBObject("fileName", path).append("content", textArea.getText());
			collection.insert(file);
			textArea.setText(query.one().get("content").toString());
			textField.setText("");

		}

		if (event.getSource() == buttonClear) {
			if (JOptionPane.showConfirmDialog(null, "This action can't be undo!\n Do you want to proceed?",
					"Clear file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
				collection = db.getCollection(Main.username);
				try {
					@SuppressWarnings("unused")
					String i = query.one().get("fileName").toString();
					collection.remove(query.getQuery());
				} catch (NullPointerException e) {

				}
				DBObject file;
				file = new BasicDBObject("fileName", path).append("content", "");
				collection.insert(file);
				textArea.setText("");
			}
		}

		if (event.getSource() == buttonSearch) {
			try {
				String search = JOptionPane.showInputDialog("");
				if (search != null) {
					if (search.equals("")) {
						search = JOptionPane.showInputDialog("Invalid!");

					} else {
						Scanner(search);
					}
				}

			} catch (HeadlessException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}

		if (event.getSource() == buttonRefresh) {

			WIDTH = frame.getWidth();
			HEIGHT = frame.getHeight();
			textArea.setSize(WIDTH - 50, HEIGHT - 100);
			textArea.setText(query.one().get("content").toString());

			collection = db.getCollection(Main.username);
			try {
				@SuppressWarnings("unused")
				String i = query.one().get("fileName").toString();
			} catch (NullPointerException e) {

			}

		}

		WIDTH = frame.getWidth();
		HEIGHT = frame.getHeight();
		textArea.setSize(WIDTH - 50, HEIGHT - 100);

	}

	@SuppressWarnings("resource")
	public void Scanner(String string) throws IOException {
		DBCursor query = collection.find(new BasicDBObject("fileName", path));
		final Scanner scanner = new Scanner(query.one().get("content").toString());
		int i = 0;
		String[] results = new String[2000000];
		while (scanner.hasNextLine()) {
			final String lineFromFile = scanner.nextLine();
			String search = lineFromFile.toUpperCase();

			if (search.contains(string.toUpperCase())) {
				if (!lineFromFile.isEmpty()) {
					search.equalsIgnoreCase(string);
					results[i++] = lineFromFile;

				}
			}
		}
		JOptionPane.showMessageDialog(null, results, "Results", JOptionPane.INFORMATION_MESSAGE);
	}

}
