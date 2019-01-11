package model;

public class ChartData {
	private String name;
	private double value;

	public ChartData(String name, double value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public double getValue() {
		return value;
	}
}
