package view;

import model.Record;
import model.exceptions.NullAmountException;
import viewModel.AppManager;

import javax.swing.*;

class EditDialog extends Dialog {
	private AppManager appManager;
	private int editIndex;

	EditDialog(AppManager appManager) {
		super();
		this.appManager = appManager;
		initView();
	}

	private void initView() {
		setTitle("Edit a record");
		leftBtn.setText("Edit");
		initInputFields();

		leftBtn.addActionListener(e -> {
			String amount = amountText.getText();
			String category = super.category;
			String description = descriptionText.getText();
			boolean isDeposit = super.isDeposit;

			try {
				appManager.editRecord(editIndex, amount, category, description, isDeposit);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this, "Please input the integers", "ERROR!", JOptionPane.ERROR_MESSAGE);
			} catch (NullAmountException e1) {
				JOptionPane.showMessageDialog(this, e1, "ERROR!", JOptionPane.ERROR_MESSAGE);
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(this, "Please select a category", "ERROR!", JOptionPane.ERROR_MESSAGE);
			}
			initInputFields();
			this.dispose();
		});

		cancelBtn.addActionListener(e -> {
			initInputFields();
			this.dispose();
		});
	}

	void initEditInputFields(int editIndex) {
		this.editIndex = editIndex;
		Record editingRecord = appManager.getRecord(editIndex);

		String category = editingRecord.getCategory();

		descriptionText.setText(editingRecord.getDescription());
		amountText.setText(editingRecord.getAmountForEdit());

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
}
