package ui.dialogs;

import model.ListOfRecords;
import model.Record;
import model.exceptions.NullAmountException;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class EditDialog extends Dialog {
	private int editIndex;

	//MODIFIES: this
	//EFFECTS: Constructs the dialog popup. Loads necessary components.
	public EditDialog(ListOfRecords records) {
		super(records);
		init();
		initInputFields();
	}

	@Override
	void init() {
		setTitle("Edit a record");
		leftBtn.setText("Edit");
	}

	public void initEditInputFields(int editIndex) {
		this.editIndex = editIndex;
		Record editingRecord = records.getRecords().get(editIndex);

		String description = editingRecord.getDescription();
		String category = editingRecord.getCategory();
		String amount = editingRecord.getAmountForEdit();

		descriptionText.setText(description);
		amountText.setText(amount);
		doClickCategories(editingRecord, category);
	}

	private void doClickCategories(Record editingRecord, String category) {
		if (editingRecord.isDeposit()) {
			for (int i = 0; i < INCOME_CATEGORY.length; i++) {
				if (category.equals(incomeBtns[i].getText())) {
					incomeBtns[i].doClick();
				}
			}
		} else {
			for (int i = 0; i < EXPENDITURE_CATEGORY.length; i++) {
				if (category.equals(expenditureBtns[i].getText())) {
					expenditureBtns[i].doClick();
				}
			}
		}
	}

	private void editRecord() throws NumberFormatException, NullAmountException, NullPointerException {
		double amountDouble = Double.parseDouble(amountText.getText()) * 100;
		String amount = helper.getCanDollarFormat(amountDouble);
		if (amount == null) {
			throw new NullAmountException("Please input amount.");
		}

		String description = descriptionText.getText();
		String category = super.category;
		boolean isDeposit = super.isDeposit;

		Record editedRecord = new Record(amount, description, category, isDeposit);
		records.getRecords().set(editIndex, editedRecord);
		notifyObserver();
	}

	//REQUIRES: Needs user's mouse click event.
	//MODIFIES: this
	//EFFECTS: Each if statement is allocated to its button's function.
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.leftBtn == e.getSource()) {
			try {
				editRecord();
				initInputFields();
				this.dispose();
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this, "Please input the integers", "ERROR!", 0);
			} catch (NullAmountException e1) {
				JOptionPane.showMessageDialog(this, e1, "ERROR!", 0);
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(this, "Please select a category", "ERROR!", 0);
			}
		}

		if (this.cancelBtn == e.getSource()) {
			initInputFields();
			this.dispose();
		}
	}
}