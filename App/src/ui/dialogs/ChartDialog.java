package ui.dialogs;

import function.Helper;
import model.ChartData;
import model.ListOfRecords;
import ui.ChartPanel;
import ui.ListeningButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChartDialog extends JDialog {
	private final Dimension BUTTON_DIM = new Dimension(80, 30);
	private ChartDialog chartDialog;
	private ListOfRecords records;
	private Helper helper = Helper.getInstance();

	public ChartDialog(ListOfRecords records) {
		chartDialog = this;
		setTitle("Expense Chart");
		this.records = records;
		loadGUI();
	}

	private void loadGUI() {
		this.setLayout(null);
		ListeningButton closeBtn = new ListeningButton("Close", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chartDialog.dispose();
			}
		}, BUTTON_DIM);
		helper.attach(chartDialog, closeBtn, 200, 420, 80, 30);
	}

	public void showDialog() {
		ChartPanel chartPanel = new ChartPanel();
		helper.attach(chartDialog, chartPanel, 0, 0, 500, 400);
		ArrayList<ChartData> chartData = helper.getChartData(records);
		chartPanel.initAndShowGUI(chartData);
	}
}
