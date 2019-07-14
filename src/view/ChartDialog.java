package view;

import helper.ComponentAttacher;
import helper.Helper;
import model.ChartData;
import model.ListOfRecords;
import model.Record;
import viewModel.AppManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChartDialog extends JDialog {
	private final String[] EXPENDITURE_CATEGORY = {"Food", "Transport", "For fun", "Gift", "Clothes", "Other Expen."};

	private final Rectangle BUTTON_RECT = new Rectangle(200, 420, 80, 30);
	private ChartDialog chartDialog;
	private ListOfRecords records;
	private Helper helper = Helper.getInstance();
	private AppManager appManager;

	public ChartDialog(AppManager appManager) {
		this.appManager = appManager;
		chartDialog = this;
		setTitle("Expense Chart");
//		this.records = records;
		loadGUI();
	}

	private void loadGUI() {
		this.setLayout(null);
		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(e -> chartDialog.dispose());
		ComponentAttacher.attach(chartDialog, closeBtn, BUTTON_RECT);
	}

	public void showDialog() {
		ChartPanel chartPanel = new ChartPanel();
		ComponentAttacher.attach(chartDialog, chartPanel, 0, 0, 500, 400);
		ArrayList<ChartData> chartData = getChartData(records);
		chartPanel.initAndShowGUI(chartData);
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
