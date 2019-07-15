package viewModel;

import helper.CurrencyFormatter;
import helper.Observable;
import helper.Observer;
import model.Balance;
import model.ChartData;
import model.ListOfRecords;
import model.Record;
import model.exceptions.NullAmountException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class AppManager implements Observable {
	private final String[] EXPENDITURE_CATEGORY = {"Food", "Transport", "For fun", "Gift", "Clothes", "Other Expen."};

	private ListOfRecords records;
	private Balance balance;
	private List<Observer> observers;
	private NumberFormat canFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);

	public AppManager() {
		this.records = new ListOfRecords();
		this.balance = new Balance(records);
		this.observers = new ArrayList<>();
	}

	public void addRecord(String amount, String category, String description, boolean isDeposit) throws NumberFormatException, NullAmountException, NullPointerException {
		double amountDouble = Double.parseDouble(amount) * 100;
		String amountStr = canFormat.format(amountDouble);
		if (amountStr == null) {
			throw new NullAmountException("Please input amount.");
		}

		records.addRecord(new Record(amountStr, description, category, isDeposit));
		notifyObserver();
	}

	public void editRecord(int editIndex, String amount, String category, String description, boolean isDeposit) throws NumberFormatException, NullAmountException, NullPointerException {
		if (amount == null) {
			throw new NullAmountException("Please input amount.");
		}
		double amountDouble = Double.parseDouble(amount) * 100;
		amount = CurrencyFormatter.getCanDollarFormat(amountDouble);

		Record editedRecord = new Record(amount, description, category, isDeposit);
		records.getRecords().set(editIndex, editedRecord);

		notifyObserver();
	}

	public void updateRecordTable(DefaultTableModel recordTableModel) {
		List<Record> recordArrayList = records.getRecords();
		if (recordTableModel != null) recordTableModel.getDataVector().removeAllElements();
		Object[] data = new Object[4];
		for (Record r : recordArrayList) {
			int amountForInt = r.getAmountInt();
			data[0] = r.getDate();
			data[1] = CurrencyFormatter.getCanDollarFormat((double) amountForInt/ 100);
			data[2] = r.getDescription();
			data[3] = r.getCategory();

			assert recordTableModel != null;
			recordTableModel.addRow(data);
		}
	}

	public void loadRecords() {
		records.clearRecords();

		JFileChooser jf = new JFileChooser();
		int response = jf.showOpenDialog(null);
		if (response == JFileChooser.APPROVE_OPTION) {
			File f = jf.getSelectedFile();
			Scanner s;
			try {
				s = new Scanner(new FileReader(f));

				String[] item;
				while (s.hasNextLine()) {
					item = s.nextLine().split(",");
					Record record = new Record(item[1], item[2], item[3], Boolean.parseBoolean(item[4]));
					record.setDate(item[0]);
					records.addRecord(record);
				}
			} catch (FileNotFoundException e) {
				JOptionPane.showMessageDialog(null, "File not found.", "ERROR!", JOptionPane.ERROR_MESSAGE);
			} catch (ArrayIndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(null, "You have selected a wrong file", "ERROR!", JOptionPane.ERROR_MESSAGE);
			}
		}
		notifyObserver();
	}

	public void saveRecords() {
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

				for (Record r : records.getRecords()) {
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

	public ArrayList<ChartData> getChartData() {
		ArrayList<ChartData> chartData = new ArrayList<>();
		for (String s : EXPENDITURE_CATEGORY) {
			double expense = 0;
			for (Record record : records.getRecords()) {
				if (record.getCategory().equals(s)) {
					expense += record.getAmountInt();
				}
			}
			chartData.add(new ChartData(s, expense));
		}
		return chartData;
	}

	@Override
	public void addObserver(Observer o) {
		if (!observers.contains(o)) observers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		observers.remove(o);
	}

	@Override
	public void notifyObserver() {
		for (Observer o : observers) {
			o.update();
		}
	}

	public String getIncome() {
		return balance.getIncome();
	}

	public String getExpense() {
		return balance.getExpense();
	}

	public String getBalance() {
		return balance.getBalance();
	}

	public Record getRecord(int editIndex) {
		return records.getRecords().get(editIndex);
	}
}
