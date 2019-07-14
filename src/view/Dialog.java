package view;

import helper.ComponentAttacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract class Dialog extends JDialog {
	final String[] EXPENDITURE_CATEGORY = {"Food", "Transport", "For fun", "Gift", "Clothes", "Other Expen."};
	final String[] INCOME_CATEGORY = {"Salary", "Pocket money", "Carried over", "Other Income"};

	JTextField amountText, descriptionText;
	JButton leftBtn, cancelBtn;
	String category;
	boolean isDeposit;
	JToggleButton[] incomeBtns = new JToggleButton[INCOME_CATEGORY.length];
	JToggleButton[] expenditureBtns = new JToggleButton[EXPENDITURE_CATEGORY.length];

	ButtonGroup incomeButtonGroup, expenditureButtonGroup;

	Dialog() {
		initView();
	}

	private void initView() {
		setLayout(null);

		JLabel infoLabel = new JLabel("Amount (C$)");
		JLabel descriptionLabel = new JLabel("Description");
		JLabel incomeCategoryLabel = new JLabel("Income Category");
		JLabel expenditureCategoryLabel = new JLabel("Expen. Category");
		ComponentAttacher.attach(this, infoLabel, 10, 10, 120, 25);
		ComponentAttacher.attach(this, descriptionLabel, 10, 40, 80, 25);
		ComponentAttacher.attach(this, incomeCategoryLabel, 10, 100, 120, 25);
		ComponentAttacher.attach(this, expenditureCategoryLabel, 10, 180, 120, 25);

		amountText = new JTextField();
		descriptionText = new JTextField();
		ComponentAttacher.attach(this, amountText, 120, 10, 250, 25);
		ComponentAttacher.attach(this, descriptionText, 120, 40, 250, 25);

		ActionListener listener = this::clearAnotherCategory;
		ComponentAttacher.attach(this, incomeButtonPanel(listener), 120, 100, 250, 60);
		ComponentAttacher.attach(this, expenditureButtonPanel(listener), 120, 180, 250, 90);

		leftBtn = new JButton();
		cancelBtn = new JButton("Cancel");
		ComponentAttacher.attach(this, leftBtn, 50, 300, 100, 40);
		ComponentAttacher.attach(this, cancelBtn, 200, 300, 100, 40);
	}

	private JPanel incomeButtonPanel(ActionListener listener) {
		JPanel incomeButtonPanel = new JPanel();
		incomeButtonGroup = new ButtonGroup();
		incomeButtonPanel.setLayout(new GridLayout(0,2));

		for (int i = 0; i < INCOME_CATEGORY.length; i++) {
			incomeBtns[i] = new JToggleButton();
			incomeBtns[i].setText(INCOME_CATEGORY[i]);
			incomeBtns[i].addActionListener(listener);
			incomeButtonGroup.add(incomeBtns[i]);
			incomeButtonPanel.add(incomeBtns[i]);
		}

		return incomeButtonPanel;
	}

	private JPanel expenditureButtonPanel(ActionListener listener) {
		JPanel expenditureButtonPanel = new JPanel();
		expenditureButtonGroup = new ButtonGroup();
		expenditureButtonPanel.setLayout(new GridLayout(0,2));

		for (int i = 0; i < EXPENDITURE_CATEGORY.length; i++) {
			expenditureBtns[i] = new JToggleButton(EXPENDITURE_CATEGORY[i]);
			expenditureBtns[i].addActionListener(listener);
			expenditureButtonGroup.add(expenditureBtns[i]);
			expenditureButtonPanel.add(expenditureBtns[i]);
		}

		return expenditureButtonPanel;
	}

	private void clearAnotherCategory(ActionEvent actionEvent) {
		category = actionEvent.getActionCommand();
		for (String income : INCOME_CATEGORY) {
			if (category.equals(income)) {
				expenditureButtonGroup.clearSelection();
				isDeposit = true;
			}
		}
		for (String expense : EXPENDITURE_CATEGORY) {
			if (category.equals(expense)) {
				incomeButtonGroup.clearSelection();
				isDeposit = false;
			}
		}
	}

	void initInputFields() {
		amountText.setText("");
		descriptionText.setText("");
		incomeButtonGroup.clearSelection();
		expenditureButtonGroup.clearSelection();
	}
}
