package ui.dialogs;

import function.Helper;
import model.ListOfRecords;
import model.Record;
import ui.Observer;
import ui.Subject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

abstract class Dialog extends JDialog implements ActionListener, Subject {
	final String[] EXPENDITURE_CATEGORY = {"Food", "Transport", "For fun", "Gift", "Clothes", "Other Expen."};
	final String[] INCOME_CATEGORY = {"Salary", "Pocket money", "Carried over", "Other Income"};

	JTextField amountText, descriptionText;
	JButton leftBtn, cancelBtn;
	Record record;
	ListOfRecords records;
	String category;
	boolean isDeposit;
	JToggleButton[] incomeBtns = new JToggleButton[INCOME_CATEGORY.length];
	JToggleButton[] expenditureBtns = new JToggleButton[EXPENDITURE_CATEGORY.length];
	private List<Observer> observers;
	Helper helper = Helper.getInstance();

	ButtonGroup incomeButtonGroup, expenditureButtonGroup;

	//MODIFIES: this
	//EFFECTS: Constructs the dialog popup. Loads necessary components.
	Dialog(ListOfRecords records) {
		this.records = records;
		this.observers = new ArrayList<>();
		loadGUI();
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

	//EFFECTS: Loads necessary components.
	private void loadGUI() {
		setLayout(null);

		//Labels
		JLabel infoLabel = new JLabel("Amount (C$)");
		JLabel descriptionLabel = new JLabel("Description");
		JLabel incomeCategoryLabel = new JLabel("Income Category");
		JLabel expenditureCategoryLabel = new JLabel("Expen. Category");
		helper.attach(this, infoLabel, 10, 10, 120, 25);
		helper.attach(this, descriptionLabel, 10, 40, 80, 25);
		helper.attach(this, incomeCategoryLabel, 10, 100, 120, 25);
		helper.attach(this, expenditureCategoryLabel, 10, 180, 120, 25);

		//TextField
		amountText = new JTextField();
		descriptionText = new JTextField();
		helper.attach(this, amountText, 120, 10, 250, 25);
		helper.attach(this, descriptionText, 120, 40, 250, 25);

		//incomeButtons
		incomeButtonGroup = new ButtonGroup();
		JPanel incomeButtonPanel = new JPanel();
		incomeButtonPanel.setLayout(new GridLayout(0,2));

		ActionListener listener = this::clearAnotherCategory;

		for (int i = 0; i < INCOME_CATEGORY.length; i++) {
			incomeBtns[i] = new JToggleButton();
			incomeBtns[i].setText(INCOME_CATEGORY[i]);
			incomeBtns[i].addActionListener(listener);
			incomeButtonGroup.add(incomeBtns[i]);
			incomeButtonPanel.add(incomeBtns[i]);
		}

		helper.attach(this, incomeButtonPanel, 120, 100, 250, 60);

		//expenditureButtons
		expenditureButtonGroup = new ButtonGroup();
		JPanel expenditureButtonPanel = new JPanel();
		expenditureButtonPanel.setLayout(new GridLayout(0,2));

		for (int i = 0; i < EXPENDITURE_CATEGORY.length; i++) {
			expenditureBtns[i] = new JToggleButton(EXPENDITURE_CATEGORY[i]);
			expenditureBtns[i].addActionListener(listener);
			expenditureButtonGroup.add(expenditureBtns[i]);
			expenditureButtonPanel.add(expenditureBtns[i]);
		}
		helper.attach(this, expenditureButtonPanel, 120, 180, 250, 90);

		//Buttons
		leftBtn = new JButton();
		cancelBtn = new JButton("Cancel");
		leftBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		helper.attach(this, leftBtn, 50, 300, 100, 40);
		helper.attach(this, cancelBtn, 200, 300, 100, 40);
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

	abstract void init();

	void initInputFields() {
		amountText.setText("");
		descriptionText.setText("");
		incomeButtonGroup.clearSelection();
		expenditureButtonGroup.clearSelection();
	}
}
