package model;

import java.util.ArrayList;
import java.util.List;

public class ListOfRecords {
	private List<Record> records;

	public ListOfRecords() {
		records = new ArrayList<>();
	}

	public List<Record> getRecords() {
		return records;
	}

	public void addRecord(Record record) {
		records.add(record);
	}

	public void clearRecords() {
		records.clear();
	}

	public void removeRecord(Record record) {
		records.remove(record);
	}
}
