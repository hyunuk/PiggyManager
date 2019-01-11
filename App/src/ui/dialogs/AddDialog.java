package ui.dialogs;

import model.ListOfRecords;
import model.Record;
import model.exceptions.NullAmountException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

public class AddDialog extends Dialog implements ActionListener {
	private NumberFormat canFormat = NumberFormat.getCurrencyInstance(Locale.CANADA);

	//MODIFIES: this
	//EFFECTS: Constructs the dialog popup. Loads necessary components.
	public AddDialog(ListOfRecords records) {
		super(records);
		init();
	}

	@Override
	void init() {
		setTitle("Add a record");
		leftBtn.setText("Add");

		amountText.setText("");
		descriptionText.setText("");
		incomeButtonGroup.clearSelection();
		expenditureButtonGroup.clearSelection();
	}

	//REQUIRES: Needs user's mouse click event.
	//MODIFIES: this
	//EFFECTS: Each if statement is allocated to its button's function.
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.leftBtn == e.getSource()) {
			try {
				addRecord();
				init();
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
			init();
			this.dispose();
		}
	}

	//MODIFIES: Record class
	//EFFECTS: AddRecordFunc income/expenditureRBtn statement to Record class and put it in the ArrayList.
	private void addRecord() throws NumberFormatException, NullAmountException, NullPointerException {
		double amountDouble = Double.parseDouble(amountText.getText()) * 100;
		String amount = canFormat.format(amountDouble);
		if (amount == null) {
			throw new NullAmountException("Please input amount.");
		}
		String category = super.category;
		String description = descriptionText.getText();
		boolean isDeposit = super.isDeposit;

		record = new Record(amount, description, category, isDeposit);
		records.addRecord(record);
		notifyObserver();
	}
}
