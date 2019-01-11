package function;

import model.Record;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManager implements Loadable, Savable {
	@Override
	public void loadFunc(List<Record> recordArrayList) {
		if (recordArrayList == null) {
			recordArrayList = new ArrayList<>();
		} else {
			recordArrayList.clear();
		}

		JFileChooser jf = new JFileChooser();
		int response = jf.showOpenDialog(null);
		if (response == JFileChooser.APPROVE_OPTION) {
			File f = jf.getSelectedFile();
			Scanner s;
			try {
				s = new Scanner(new FileReader(f));

				String item[];
				while (s.hasNextLine()) {
					item = s.nextLine().split(",");
					Record record = new Record(item[1], item[2], item[3], Boolean.parseBoolean(item[4]));
					record.setDate(item[0]);
					recordArrayList.add(record);
				}
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "File not found.", "ERROR!", 0);
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "You have selected a wrong file", "ERROR!", 0);
			}
		}

	}

	@Override
	public void saveFunc(List<Record> recordArrayList) {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select a file or make a new text file to save");
		int userSelection = fileChooser.showSaveDialog(null);

		FileWriter writer = null;
		BufferedWriter bWriter = null;

		if (userSelection == JFileChooser.APPROVE_OPTION) {
			try {
				writer = new FileWriter(fileChooser.getSelectedFile() + ".txt");
				bWriter = new BufferedWriter(writer);
				String str;

				for (Record r : recordArrayList) {
					str = r.saveString();
					bWriter.append(str);
				}
				bWriter.flush();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bWriter != null) bWriter.close();
					if (writer != null) writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
