package main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class Main {

	public static void main(String[] args) throws IOException {

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
