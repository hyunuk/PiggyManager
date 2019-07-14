package view;

import model.exceptions.NullAmountException;
import viewModel.AppManager;

import javax.swing.*;

class AddDialog extends Dialog {
	private AppManager appManager;
	private final String NUMBER_FORMAT_MSG = "Please input the integers";
	private final String ERROR_MSG = "Error! Something is missing.";
	private final String CATEGORY_MISSING_MSG = "Please select a category.";

	AddDialog(AppManager appManager) {
		super();
		this.appManager = appManager;
		initView();
	}

	private void initView() {
		setTitle("Add a record");
		leftBtn.setText("Add");

		amountText.setText("");
		descriptionText.setText("");
		incomeButtonGroup.clearSelection();
		expenditureButtonGroup.clearSelection();

		leftBtn.addActionListener(e -> {
			String amount = amountText.getText();
			String category = super.category;
			String description = descriptionText.getText();
			boolean isDeposit = super.isDeposit;

			try {
				appManager.addRecord(amount, category, description, isDeposit);
			} catch (NumberFormatException e1) {
				JOptionPane.showMessageDialog(this, NUMBER_FORMAT_MSG, "ERROR!", JOptionPane.ERROR_MESSAGE);
			} catch (NullAmountException e1) {
				JOptionPane.showMessageDialog(this, ERROR_MSG, "ERROR!", JOptionPane.ERROR_MESSAGE);
			} catch (NullPointerException e1) {
				JOptionPane.showMessageDialog(this, CATEGORY_MISSING_MSG, "ERROR!", JOptionPane.ERROR_MESSAGE);
			}
			initInputFields();
			this.dispose();
		});
		cancelBtn.addActionListener(e -> {
			initInputFields();
			this.dispose();
		});
	}
}
