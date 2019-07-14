package model;

import helper.CurrencyFormatter;

public class Balance {
	private ListOfRecords records;

	public Balance(ListOfRecords records) {
		this.records = records;
	}

	public String getIncome() {
		int income = 0;
		for (Record r : records.getRecords()) {
			if (r.getAmountInt() > 0) income += r.getAmountInt();
		}

		return CurrencyFormatter.getCanDollarFormat((double) income / 100);
	}

	public String getExpense() {
		int expense = 0;
		for (Record r : records.getRecords()) {
			if (r.getAmountInt() < 0) expense += r.getAmountInt();
		}

		return CurrencyFormatter.getCanDollarFormat((double) expense / 100);
	}

	public String getBalance() {
		int balance = 0;
		for (Record r : records.getRecords()) {
			balance += r.getAmountInt();
		}

		return CurrencyFormatter.getCanDollarFormat((double) balance / 100);
	}
}
