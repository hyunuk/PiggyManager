package function;

import model.ChartData;
import model.ListOfRecords;
import model.Record;
import ui.ListeningButton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Helper {
	private final String[] EXPENDITURE_CATEGORY = {"Food", "Transport", "For fun", "Gift", "Clothes", "Other Expen."};

	private Helper() {}
	private static class Singleton {
		private static final Helper instance = new Helper();
	}
	public static Helper getInstance() {
		return Singleton.instance;
	}

	//MODIFIES: this
	//EFFECTS: Helper function to add GUI components to its appropriate location.
	public void attach(Container caller, JComponent abc, int x, int y, int width, int height) {
		caller.add(abc);
		abc.setBounds(x, y, width, height);
	}

	public void attach(Container caller, JComponent abc, Rectangle r) {
		caller.add(abc);
		abc.setBounds(r);
	}

	public void attach(Container caller, ListeningButton abc, int x, int y, int width, int height) {
		caller.add(abc.getButton());
		abc.getButton().setBounds(x, y, width, height);
	}

	public void updateRecordTable(ListOfRecords records, DefaultTableModel recordTableModel) {
		List<Record> recordArrayList = records.getRecords();
		if (recordTableModel != null) recordTableModel.getDataVector().removeAllElements();
		Object[] data = new Object[4];
		for (Record r : recordArrayList) {
			int amountForInt = r.getAmountInt();
			data[0] = r.getDate();
			data[1] = getCanDollarFormat((double) amountForInt/ 100);
			data[2] = r.getDescription();
			data[3] = r.getCategory();

			recordTableModel.addRow(data);
		}
	}

	public String getCanDollarFormat(Double value){
		NumberFormat canFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);
		return canFormat.format(value);
	}

	public ArrayList<ChartData> getChartData(ListOfRecords records) {
		ArrayList<ChartData> chartData = new ArrayList<>();
		for (int i = 0 ; i < EXPENDITURE_CATEGORY.length; i++) {
			double expense = 0;
			for (Record r : records.getRecords()) {
				if (r.getCategory().equals(EXPENDITURE_CATEGORY[i])) {
					expense += r.getAmountInt();
				}
			}
			chartData.add(new ChartData(EXPENDITURE_CATEGORY[i], expense));
		}
		return chartData;
	}
}
