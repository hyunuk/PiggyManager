package model;

import function.Helper;

public class Balance {
	private ListOfRecords records;
	private Helper helper = Helper.getInstance();

	public Balance(ListOfRecords records) {
		this.records = records;
	}

	public String getIncome() {
		int income = 0;
		for (Record r : records.getRecords()) {
			if (r.getAmountInt() > 0) income += r.getAmountInt();
		}
		String returnIncome = helper.getCanDollarFormat((double) income / 100);

		return returnIncome;
	}

	public String getExpense() {
		int expense = 0;
		for (Record r : records.getRecords()) {
			if (r.getAmountInt() < 0) expense += r.getAmountInt();
		}
		String returnExpense = helper.getCanDollarFormat((double) expense / 100);

		return returnExpense;
	}

	public String getBalance() {
		int balance = 0;
		for (Record r : records.getRecords()) {
			balance += r.getAmountInt();
		}
		String returnBalance = helper.getCanDollarFormat((double) balance / 100);

		return returnBalance;
	}
}
