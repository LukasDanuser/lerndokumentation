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
	MongoClient client = new MongoClient("localhost", 27017);
//	MongoClient client = new MongoClient(uri);
	@SuppressWarnings("deprecation")
	DB db = client.getDB("lerndokumentation");
	DBCollection collection = db.getCollection("users");

	public static boolean newFile;
	public static String fileName;
	public static String path;
	
	Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

	private String hashedPassword = "";

	int WIDTH = (int) screenSize.getWidth();
	int HEIGHT = (int) screenSize.getHeight();

	public static JFrame frame;
	JButton buttonSave;
	JButton buttonClear;
	JButton buttenNewUser;
	JButton buttenRemoveUser;
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

		buttenRemoveUser = new JButton("Remove User");
		buttenRemoveUser.setBounds(150, 50, 120, 60);
		buttenRemoveUser.addActionListener(this);
		buttenRemoveUser.setBackground(new Color(125, 125, 125));
		buttenRemoveUser.setFont(new Font("Comic Sans", Font.ITALIC, 15));
		buttenRemoveUser.setForeground(new Color(250, 250, 250));
		buttenRemoveUser.setBorder(BorderFactory.createEtchedBorder());
		buttenRemoveUser.setFocusable(false);

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
		frame.setSize(new Dimension(WIDTH, HEIGHT));
		frame.setLayout(new FlowLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(buttonSave);
		if (Main.role.equals("admin")) {
			frame.add(buttenNewUser);
			frame.add(buttenRemoveUser);
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

	@SuppressWarnings("resource")
	@Override
	public void actionPerformed(ActionEvent event) {
		String userNameValue = "";
		String passwordValue = "";
		String pwConfirmValue = "";
		String adminPasswordValue = "";

		boolean isValidLogin = false;

		DBCursor query = collection.find(new BasicDBObject("fileName", path));
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();

		DBCursor results = collection.find(new BasicDBObject("username", userNameValue));
		String text = textField.getText();
		boolean isFieldEmpty = false;
		boolean isFileEmpty = false;
		if (textField.getText().equals("")) {
			isFieldEmpty = true;
		}
		path = fileName;

		if (event.getSource() == buttenRemoveUser) {
			boolean userExists = true;
			boolean adminPwValid = true;
			collection = db.getCollection("users");

			JLabel jUserName = new JLabel("Enter the name of the user you want to remove");
			JTextField userName = new JTextField();
			JLabel jAdminPassword = new JLabel("Enter admin password");
			JTextField adminPassword = new JPasswordField();
			Object[] ob = { jUserName, userName, jAdminPassword, adminPassword };
			userName.setText("");
			adminPassword.setText("");
			int resultLogin = JOptionPane.showConfirmDialog(null, ob, "Remove user. This action can't be undo!",
					JOptionPane.OK_CANCEL_OPTION);
			if (resultLogin == JOptionPane.OK_OPTION) {
				userNameValue = userName.getText();
				adminPasswordValue = adminPassword.getText();
				query = collection.find(new BasicDBObject("username", userNameValue));
				if (!Main.checkPassword(adminPasswordValue, Main.adminPw) && !Main.adminPw.equals("")) {
					adminPwValid = false;
				}
				try {
					@SuppressWarnings("unused")
					String i = query.one().get("username").toString();
					userExists = true;
				} catch (NullPointerException e) {
					userExists = false;
				}

				while (adminPwValid == false | userExists == false) {
					try {
						@SuppressWarnings("unused")
						String i = query.one().get("username").toString();
						userExists = true;
					} catch (NullPointerException e) {
						userName.setText("");
						adminPassword.setText("");
						resultLogin = JOptionPane.showConfirmDialog(null, ob, "Invalid data!",
								JOptionPane.OK_CANCEL_OPTION);
						if (resultLogin == JOptionPane.OK_OPTION) {
							userNameValue = userName.getText();
							adminPasswordValue = adminPassword.getText();
							try {
								@SuppressWarnings("unused")
								String i = query.one().get("username").toString();
								userExists = true;
								if (Main.checkPassword(adminPasswordValue, Main.adminPw)
										&& !adminPasswordValue.equals("")) {
									adminPwValid = true;

								}
							} catch (NullPointerException e1) {

							}
						} else {
							break;
						}
					}
					if (Main.checkPassword(adminPasswordValue, Main.adminPw) && !adminPasswordValue.equals("")) {
						adminPwValid = true;

					}
					try {
						query = collection.find(new BasicDBObject("username", userNameValue));
						@SuppressWarnings("unused")
						String i = query.one().get("username").toString();
						userExists = true;
					} catch (NullPointerException e) {

					}
				}

				if (adminPwValid == true && userExists == true) {
					query = collection.find(new BasicDBObject("username", userNameValue));
					collection.remove(query.getQuery());
					collection = db.getCollection(userNameValue);
					collection.drop();
					JOptionPane.showMessageDialog(null, "User removed", "", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		}

		if (event.getSource() == buttenNewUser) {
			collection = db.getCollection("users");

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
				boolean validPassword = true;
				boolean validUsername = true;
				JLabel jUserName = new JLabel("User Name");
				JTextField userName = new JTextField();
				JLabel jPassword = new JLabel("Password");
				JTextField password1 = new JPasswordField();
				JLabel jPasswordConfirm = new JLabel("Confirm password");
				JTextField passwordConfirm = new JPasswordField();
				Object[] ob = { jUserName, userName, jPassword, password1, jPasswordConfirm, passwordConfirm };
				int resultLogin = JOptionPane.showConfirmDialog(null, ob, "Login", JOptionPane.OK_CANCEL_OPTION);

				collection = db.getCollection("users");
				@SuppressWarnings("unused")
				String user1 = "";
				if (resultLogin == JOptionPane.OK_OPTION) {
					boolean proceed = true;
					userNameValue = userName.getText();
					passwordValue = password1.getText();
					pwConfirmValue = passwordConfirm.getText();
					results = collection.find(new BasicDBObject("username", userNameValue));

					try {
						user1 = results.one().get("username").toString();
						validUsername = false;
						if (userNameValue.equals("")) {
							validUsername = false;
						}
						while (validUsername == false) {
							userName.setText("");
							password1.setText("");
							passwordConfirm.setText("");
							results = collection.find(new BasicDBObject("username", userNameValue));
							resultLogin = JOptionPane.showConfirmDialog(null, ob, "Username already exists!",
									JOptionPane.OK_CANCEL_OPTION);
							if (resultLogin == JOptionPane.OK_OPTION) {
								userNameValue = userName.getText();
								passwordValue = password1.getText();
								pwConfirmValue = passwordConfirm.getText();
							} else {
								isValidLogin = false;
								proceed = false;
								break;
							}
							results = collection.find(new BasicDBObject("username", userNameValue));
							try {
								user1 = results.one().get("username").toString();
								validUsername = false;
							} catch (NullPointerException e1) {
								validUsername = true;
								isValidLogin = true;
							}
						}

					} catch (NullPointerException e) {
						if (userNameValue.equals("")) {
							validUsername = false;
							while (validUsername == false) {
								userName.setText("");
								password1.setText("");
								passwordConfirm.setText("");
								results = collection.find(new BasicDBObject("username", userNameValue));
								resultLogin = JOptionPane.showConfirmDialog(null, ob, "Username can't be empty!",
										JOptionPane.OK_CANCEL_OPTION);
								if (resultLogin == JOptionPane.OK_OPTION) {
									userNameValue = userName.getText();
									passwordValue = password1.getText();
									pwConfirmValue = passwordConfirm.getText();
								} else {
									isValidLogin = false;
									proceed = false;
									break;
								}
								results = collection.find(new BasicDBObject("username", userNameValue));
								try {
									user1 = results.one().get("username").toString();
									validUsername = false;
								} catch (NullPointerException e1) {
									validUsername = true;
									isValidLogin = true;
									if (userNameValue.equals("")) {
										validUsername = false;
										isValidLogin = false;
									}
								}
							}
						} else {
							validUsername = true;
						}
					}
					if (!passwordValue.equals(pwConfirmValue) | passwordValue.equals("")) {
						validPassword = false;
						while (validPassword == false) {
							if (proceed == false) {
								break;
							}
							password1.setText("");
							passwordConfirm.setText("");
							resultLogin = JOptionPane.showConfirmDialog(null, ob, "Password confirmation incorrect!",
									JOptionPane.OK_CANCEL_OPTION);
							if (resultLogin == JOptionPane.OK_OPTION) {
								userNameValue = userName.getText();
								passwordValue = password1.getText();
								pwConfirmValue = passwordConfirm.getText();
							} else {
								break;
							}
							if (passwordValue.equals(pwConfirmValue) && !passwordValue.equals("")) {
								validPassword = true;
								isValidLogin = true;
							}
						}
					} else {
						if (proceed == true) {
							if (!passwordValue.equals("")) {
								validPassword = true;
								isValidLogin = true;

							}
						}
					}
					if (isValidLogin && userNameValue != null && passwordValue != null) {
						hashedPassword = org.mindrot.jbcrypt.BCrypt.hashpw(passwordValue,
								org.mindrot.jbcrypt.BCrypt.gensalt(10));

						DBObject user = new BasicDBObject("username", userNameValue).append("password", hashedPassword)
								.append("role", role);
						collection.insert(user);
						JOptionPane.showMessageDialog(null, "User added", "New user", JOptionPane.INFORMATION_MESSAGE);
						DBObject dbo = new BasicDBObject("", "");
						db.createCollection(userNameValue, dbo);
					}
				} else {

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
