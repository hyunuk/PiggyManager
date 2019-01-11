package model;

import java.util.Calendar;

public class Record {
	private String date;
	private String amountForEdit;
	private int amountInt;
	private String description;
	private String category;
	private boolean isDeposit;
	private String amountModStr;
	private Calendar cal = Calendar.getInstance();

	//REQUIRES: amount should be input as string and casted to integer. Needs to be checked when input.
	//MODIFIES: this
	//EFFECTS: Constructs the record.
	public Record(String amountStr, String description, String category, boolean isDeposit) {
		this.amountModStr = amountModify(amountStr); // 15000
		this.amountInt = Integer.parseInt(amountModStr) / 100; // 150
		this.description = description;
		this.category = category;
		this.isDeposit = isDeposit;
		this.date = today();

		this.amountForEdit = String.valueOf((double) amountInt / 100);
		this.amountInt = (isDeposit) ? amountInt : amountInt * -1;
	}

	public String amountModify(String amountStr) {
		String modStr1 = amountStr.replace(".", "").replace(",", "");
		String modStr2 = modStr1.contains("$") ? modStr1.substring(1) : modStr1;

		return modStr2;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDate() {
		return date;
	}

	public String getAmountModStr() {
		return amountModStr;
	}

	public int getAmountInt() {
		return amountInt;
	}

	public String getAmountForEdit() {
		return amountForEdit;
	}

	public String getDescription() {
		return description;
	}

	public String getCategory() {
		return category;
	}

	public boolean isDeposit() {
		return isDeposit;
	}

	//EFFECTS: Calculate the day that the record was instantiated.
	private String today() {
		int year = cal.get(Calendar.YEAR) % 100;
		String month = String.format("%02d", cal.get(Calendar.MONTH) + 1);
		String date = String.format("%02d", cal.get(Calendar.DATE));
		String today = year + "/" + month + "/" + date;
		return today;
	}

	public String saveString() {
		String saveString = "";
		String date = this.getDate();
		String amount = this.getAmountModStr();
		String category = this.getCategory();
		String description = this.getDescription();
		String isDeposit = "" + this.isDeposit();

		saveString = date + "," + amount + "," + description + "," + category + "," + isDeposit + ",\r\n";
		return saveString;
	}
}