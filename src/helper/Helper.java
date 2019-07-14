package helper;

import model.ChartData;
import model.ListOfRecords;
import model.Record;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Helper {

	private Helper() {}
	private static class Singleton {
		private static final Helper instance = new Helper();
	}
	public static Helper getInstance() {
		return Singleton.instance;
	}

}
