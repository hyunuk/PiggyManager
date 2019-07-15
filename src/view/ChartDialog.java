package view;

import helper.ComponentAttacher;
import viewModel.AppManager;

import javax.swing.*;
import java.awt.*;

class ChartDialog extends JDialog {
	private final Rectangle BUTTON_RECT = new Rectangle(200, 420, 80, 30);
	private ChartDialog chartDialog;
	private AppManager appManager;

	ChartDialog(AppManager appManager) {
		this.appManager = appManager;
		chartDialog = this;
		setTitle("Expense Chart");
		initView();
	}

	private void initView() {
		this.setLayout(null);
		JButton closeBtn = new JButton("Close");
		ComponentAttacher.attach(chartDialog, closeBtn, BUTTON_RECT);

		closeBtn.addActionListener(e -> chartDialog.dispose());
	}

	void showDialog() {
		ChartPanel chartPanel = new ChartPanel();
		ComponentAttacher.attach(chartDialog, chartPanel, 0, 0, 500, 400);
		chartPanel.initAndShowGUI(appManager.getChartData());
	}

}
