package main;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

import javax.swing.*;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import main.Frame;

public class Frame implements ActionListener {

	MongoClientURI uri = new MongoClientURI(
			"mongodb://lukas:secret.8@cluster0-shard-00-00.ez8ii.mongodb.net:27017,cluster0-shard-00-01.ez8ii.mongodb.net:27017,cluster0-shard-00-02.ez8ii.mongodb.net:27017/lerndokumentation?ssl=true&replicaSet=atlas-atekpy-shard-0&authSource=admin&retryWrites=true&w=majority");
	MongoClient client = new MongoClient("localhost" , 27017);
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
	JButton buttonOpen;
	JTextField textField;
	JTextArea textArea;

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

		buttonOpen = new JButton("Open path");
		buttonOpen.setBounds(150, 50, 120, 60);
		buttonOpen.addActionListener(this);
		buttonOpen.setBackground(new Color(125, 125, 125));
		buttonOpen.setFont(new Font("Comic Sans", Font.ITALIC, 15));
		buttonOpen.setForeground(new Color(250, 250, 250));
		buttonOpen.setBorder(BorderFactory.createEtchedBorder());
		buttonOpen.setFocusable(false);

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
		frame.add(buttonOpen);
		frame.add(buttonDelete);
		frame.add(textField);
		frame.add(textArea);
		frame.setVisible(true);

		WIDTH = frame.getWidth();
		HEIGHT = frame.getHeight();

		path = fileName + ".txt";
		frame.setTitle(path);

		if (Files.exists(Paths.get(path)) == true) {
			try {
				textArea.setText(Files.readString(Paths.get(path)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void changeFile() throws IOException {

		newFile = false;
		String fileName2 = fileName;
		fileName = JOptionPane.showInputDialog(null, "Enter file name", "Open file", JOptionPane.DEFAULT_OPTION);

		if (fileName == null) {
			fileName = fileName2;
			return;
		}

		if (newFile == false && Files.exists(Paths.get(fileName + ".txt")) == false) {
			boolean exists = false;
			while (exists == false) {
				fileName = JOptionPane.showInputDialog(null, "Enter file name", "File not found!",
						JOptionPane.WARNING_MESSAGE);

				if (newFile == false && Files.exists(Paths.get(fileName + ".txt")) == true) {
					exists = true;

				}
			}
		}
		path = fileName + ".txt";
		textArea.setText(Files.readString(Paths.get(path)));
		frame.setTitle(path);
	}

	public void createNewFile(String name) throws IOException {
		path = name + ".txt";
		if (Files.exists(Paths.get(path)) == false) {
			Path fileToCreatePath = Paths.get(path);
			System.out.println("File to create path: " + fileToCreatePath);
			Path newFilePath = Files.createFile(fileToCreatePath);
			System.out.println("New file created: " + newFilePath);
			System.out.println("New File exits: " + Files.exists(newFilePath));
			textArea.setText(Files.readString(Paths.get(path)));
			frame.setTitle(path);
		}
	}

	@Override
	public void actionPerformed(ActionEvent event) {

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date date = new Date();

		String text = textField.getText();
		String text2 = textArea.getText();
		boolean isFieldEmpty = false;
		boolean isFileEmpty = false;
		if (textField.getText().equals("")) {
			isFieldEmpty = true;
		}
		path = fileName + ".txt";

		if (event.getSource() == buttenNewUser) {
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
				String username = JOptionPane.showInputDialog(null, "Enter username", "", JOptionPane.DEFAULT_OPTION);
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
					password = JOptionPane.showInputDialog(null, "Enter new password", "", JOptionPane.DEFAULT_OPTION);
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
					DBObject user = new BasicDBObject("username", username).append("password", password).append("role",
							role);
					collection.insert(user);
					JOptionPane.showMessageDialog(null, "User added", "New user", JOptionPane.INFORMATION_MESSAGE);
				}

			}

		}

		if (event.getSource() == buttonOpen) {
			String[] options = { "File", "Directory" };
			int i = JOptionPane.showOptionDialog(null, "Open file or directory?", "", JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (i == 0) {
				String absolutePath = Paths.get(path).toAbsolutePath().toString();
				try {
					Desktop.getDesktop().open(new File(absolutePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				String absolutePath = Paths.get(path).toAbsolutePath().getParent().toString();
				try {
					Desktop.getDesktop().open(new File(absolutePath));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (event.getSource() == buttonChangeFile) {
			try {
				if (JOptionPane.showConfirmDialog(null, "Create new file?", "", JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == 0) {
					newFile = true;
					String name = JOptionPane.showInputDialog(null, "File name");

					if (name == null) {

					} else {
						fileName = name;
						createNewFile(fileName);
					}

				} else {
					changeFile();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (event.getSource() == buttonDelete) {
			if (JOptionPane.showConfirmDialog(null, "This action can't be undo!\n Do you want to proceed",
					"Delete file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
				try {
					Files.delete(Paths.get(path));
					textArea.setText("");
					int input = JOptionPane.showConfirmDialog(null, "Create new file?", "",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
					if (input == 0) {
						newFile = true;
						fileName = JOptionPane.showInputDialog(null, "File name");

						if (fileName == null) {
							System.exit(1);
						}
						createNewFile(fileName);
					} else if (input == 1) {
						newFile = false;
						changeFile();
					} else if (input == 2) {
						System.exit(1);
					}

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		if (event.getSource() == buttonSave) {
			try {
				Files.writeString(Paths.get(path), text2);
				textArea.setText(Files.readString(Paths.get(path)));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (event.getSource() == textField) {

			try {

				createNewFile(fileName);

				if (Files.size(Paths.get(path)) != 0) {
					isFileEmpty = false;
				} else {
					isFileEmpty = true;
				}

				if (isFieldEmpty == true) {
					if (isFileEmpty == true) {
						Files.writeString(Paths.get(path), Files.readString(Paths.get(path)) + text);
					} else {
						Files.writeString(Paths.get(path), Files.readString(Paths.get(path)) + text);
					}

				} else if (isFieldEmpty == false) {
					if (isFileEmpty == true) {
						Files.writeString(Paths.get(path),
								Files.readString(Paths.get(path)) + formatter.format(date) + ": " + text);
					} else {
						Files.writeString(Paths.get(path),
								Files.readString(Paths.get(path)) + "\n\n" + formatter.format(date) + ": " + text);
					}

				}

				textArea.setText(Files.readString(Paths.get(path)));
				textField.setText("");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		if (event.getSource() == buttonClear) {
			if (JOptionPane.showConfirmDialog(null, "This action can't be undo!\n Do you want to proceed?",
					"Clear file", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == 0) {
				try {
					Files.writeString(Paths.get(path), "");
					textArea.setText(Files.readString(Paths.get(path)));
				} catch (IOException e) {
					e.printStackTrace();
				}
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
			try {

				WIDTH = frame.getWidth();
				HEIGHT = frame.getHeight();
				textArea.setSize(WIDTH - 50, HEIGHT - 100);
				textArea.setText(Files.readString(Paths.get(path)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		WIDTH = frame.getWidth();
		HEIGHT = frame.getHeight();
		textArea.setSize(WIDTH - 50, HEIGHT - 100);

	}

	@SuppressWarnings("resource")
	public void Scanner(String string) throws IOException {
		final Scanner scanner = new Scanner(Paths.get(path));
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
