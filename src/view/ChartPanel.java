package view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import model.ChartData;

import java.util.ArrayList;

public class ChartPanel extends JFXPanel{
	ChartPanel chartPanel = this;
	ArrayList<ChartData> chartData;
	PieChart.Data[] dataArray;
	Scene scene;

	public void initAndShowGUI(ArrayList<ChartData> chartData) {
		this.chartData = chartData;
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				initFX(chartPanel);
			}
		});
	}

	private PieChart.Data[] convertChartData() {
		int size = chartData.size();
		String name;
		double value;
		dataArray = new PieChart.Data[size];
		for (int i = 0; i < size; i++) {
			name = chartData.get(i).getName();
			value = chartData.get(i).getValue();
			dataArray[i] = new PieChart.Data(name, Math.abs(value));
		}
		return dataArray;
	}

	private void initFX(JFXPanel fxPanel) {
		// This method is invoked on the JavaFX thread
		scene = new Scene(new Group());
		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(convertChartData());
		final PieChart chart = new PieChart(pieChartData);
		((Group) scene.getRoot()).getChildren().add(chart);

		fxPanel.setScene(scene);
		chart.setTitle("Expense by Categories");
	}
}
