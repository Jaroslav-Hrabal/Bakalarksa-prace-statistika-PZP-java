import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import measurePoints.AbstractGroup;
import measurePoints.iMeasureGroup;
import measurePoints.iMeasurePoint;
import functions.MeasureGroupClient;

public class Functions {
	BorderPane border;
	Stage stage;
	iMeasureGroup virtual;
	String selection;
	String name;
	TextField text1;
	TextField text2;
	TextField text3;
	ComboBox comboBox;
	ComboBox comboBox2;
	DatePicker from;
	DatePicker to;
	String results = null;
	String results2 = null;

	public Functions(BorderPane border, Stage stage, iMeasureGroup virtual) {
		super();
		this.border = border;
		this.stage = stage;
		this.virtual = virtual;
	}

	public VBox addVBox() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		Hyperlink options[] = new Hyperlink[] { new Hyperlink("Aggregate"),
				new Hyperlink("CuAggregate"),
				new Hyperlink("Weighted Average Pressure") };

		for (int i = 0; i < 3; i++) {
			VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
			vbox.getChildren().add(options[i]);
		}

		options[0].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				GridPane GridPane = aggregate();
				border.setCenter(GridPane);
				stage.sizeToScene();
			}
		});
		options[1].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				GridPane GridPane = cuAggregate();
				border.setCenter(GridPane);
				stage.sizeToScene();

			}
		});
		options[2].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				GridPane GridPane = weightedAverage();
				border.setCenter(GridPane);
				stage.sizeToScene();
			}
		});

		return vbox;
	}

	public GridPane aggregate() {
		Text title = new Text("Aggregate");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		Label label1 = new Label("Group selection: ");
		Label label2 = new Label("Quantity selection: ");
		Label label4 = new Label("From: ");
		Label label5 = new Label("to: ");

		text1 = new TextField();

		// TextField text2 = new TextField();
		comboBox = new ComboBox();
		comboBox.setEditable(true);
		Button buttonBrowse = new Button("Browse");
		Button buttonDraw = new Button("Show");
		Button buttonSave = new Button("Save to file");
		from = new DatePicker(LocalDate.of(2011, 1, 3));
		to = new DatePicker(LocalDate.of(2011, 2, 3));
		GridPane inputGridPane = new GridPane();
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(label1, 0, 1);
		GridPane.setConstraints(label2, 0, 2);

		GridPane.setConstraints(label4, 0, 3);
		GridPane.setConstraints(label5, 0, 4);

		GridPane.setConstraints(comboBox, 1, 2);

		GridPane.setConstraints(text1, 1, 1);
		GridPane.setConstraints(from, 1, 3);
		GridPane.setConstraints(to, 1, 4);

		GridPane.setConstraints(buttonBrowse, 2, 1);
		GridPane.setConstraints(buttonDraw, 0, 5);
		GridPane.setConstraints(buttonSave, 1, 5);

		inputGridPane.getColumnConstraints().add(new ColumnConstraints(100));
		inputGridPane.setPadding(new Insets(10));
		;
		inputGridPane.setHgap(6.0D);
		inputGridPane.setVgap(6.0D);
		inputGridPane.getChildren()
				.addAll(new Node[] { title, label1, label2, label4, label5,
						from, to, comboBox, text1, buttonBrowse, buttonDraw,
						buttonSave });

		buttonBrowse.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				GetBox selectWindow = new GetBox(virtual, text1);
				Platform.runLater(new Runnable() {
					public void run() {
						try {
							// new GetBox(virtual).start(new Stage());
							selectWindow.start(new Stage());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// text1.setText(selectWindow.selection);
					}
				});
			}
		});
		buttonSave.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				if (results2 == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("No result to be saved");
					String s = "Calculate results first by Show button ";
					alert.setContentText(s);
					alert.showAndWait();
				} else {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Save result");
					File file = fileChooser.showSaveDialog(stage);
					if (file != null) {
						try {
							FileWriter fw = new FileWriter(file);
							fw.write(results2);
							fw.close();
						} catch (IOException ex) {
							System.out.println(ex.getMessage());
						}
					}
				}
			}
		});
		buttonDraw.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Float result = (float) 0;
				if (checkForm()) {
					CategoryAxis xAxis = new CategoryAxis();
					NumberAxis yAxis = new NumberAxis();
					iMeasurePoint template = virtual.getMeasurePoint(comboBox
							.getSelectionModel().getSelectedItem().toString());

					String[] targets = text1.getText().split(", ");

					for (int i = 0; i < targets.length; i++) {
						try {
							iMeasureGroup target = (virtual)
									.getObject(targets[i]);
							MeasureGroupClient client = new MeasureGroupClient();

							Instant instant = from.getValue().atTime(6, 0)
									.atZone(ZoneId.systemDefault()).toInstant();
							Date fromDate = Date.from(instant);
							instant = to.getValue().atTime(6, 0)
									.atZone(ZoneId.systemDefault()).toInstant();
							Date toDate = Date.from(instant);

							result = client.aggregate(target, (String) comboBox
									.getSelectionModel().getSelectedItem(),
									fromDate, toDate);

						} catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Form not filled!");
							String s = "Filter, Quantity selections and filter parameters must be properly filled: "
									+ e;
							alert.setContentText(s);
							alert.showAndWait();

						}

					}
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Information Dialog");
					alert.setHeaderText("Aggregate result");
					alert.setContentText("Aggregate of " + text1.getText()
							+ " is " + result);
					results2 = result.toString();
					alert.showAndWait();

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Form not filled");
					String s = "Filter, Quantity selections and filter parameters must be properly filled  ";
					alert.setContentText(s);
					alert.showAndWait();
				}

			}
		});
		text1.textProperty().addListener((observable, oldValue, newValue) -> {
			changeCombobox(newValue);

		});
		return inputGridPane;
	}

	public GridPane cuAggregate() {
		Text title = new Text("Cumulative aggregate");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		Label label1 = new Label("Group selection: ");
		Label label2 = new Label("Quantity selection: ");
		Label label3 = new Label("Method to be used: ");
		Label label4 = new Label("From: ");
		Label label5 = new Label("to: ");

		text1 = new TextField();

		// TextField text2 = new TextField();
		comboBox = new ComboBox();
		comboBox.setEditable(true);
		comboBox2 = new ComboBox();
		String[] functions = { "CuSum", "Injected", "Produced", "Total" };
		comboBox2.getItems().addAll(functions);
		Button buttonBrowse = new Button("Browse");
		Button buttonDraw = new Button("Draw");
		Button buttonSave = new Button("Save to file");
		from = new DatePicker(LocalDate.of(2011, 1, 3));
		to = new DatePicker(LocalDate.of(2011, 2, 3));
		GridPane inputGridPane = new GridPane();
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(label1, 0, 1);
		GridPane.setConstraints(label2, 0, 2);
		GridPane.setConstraints(label3, 0, 3);
		GridPane.setConstraints(label4, 0, 4);
		GridPane.setConstraints(label5, 0, 5);

		GridPane.setConstraints(comboBox, 1, 2);
		GridPane.setConstraints(comboBox2, 1, 3);
		GridPane.setConstraints(text1, 1, 1);
		GridPane.setConstraints(from, 1, 4);
		GridPane.setConstraints(to, 1, 5);
		// GridPane.setConstraints(text2, 1, 2);
		GridPane.setConstraints(buttonBrowse, 2, 1);
		GridPane.setConstraints(buttonDraw, 0, 6);
		GridPane.setConstraints(buttonSave, 1, 6);

		inputGridPane.getColumnConstraints().add(new ColumnConstraints(100));
		inputGridPane.setPadding(new Insets(10));
		;
		inputGridPane.setHgap(6.0D);
		inputGridPane.setVgap(6.0D);
		inputGridPane.getChildren().addAll(
				new Node[] { title, label1, label2, label3, label4, label5,
						from, to, comboBox, comboBox2, text1, buttonBrowse,
						buttonDraw });

		buttonBrowse.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				GetBox selectWindow = new GetBox(virtual, text1);
				Platform.runLater(new Runnable() {
					public void run() {
						try {
							// new GetBox(virtual).start(new Stage());
							selectWindow.start(new Stage());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// text1.setText(selectWindow.selection);
					}
				});
			}
		});

		buttonDraw.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				if (checkForm()) {
					CategoryAxis xAxis = new CategoryAxis();
					NumberAxis yAxis = new NumberAxis();
					iMeasurePoint template = virtual.getMeasurePoint(comboBox
							.getSelectionModel().getSelectedItem().toString());
					yAxis.setLabel(template.getName() + " ["
							+ template.getUnits() + "]");
					xAxis.setLabel("time of measurement");
					LineChart<String, Number> lineChart = new LineChart(xAxis,
							yAxis);
					lineChart.setTitle((String) comboBox2.getSelectionModel()
							.getSelectedItem());
					String[] targets = text1.getText().split(", ");
					List<XYChart.Series> graphs = null;
					for (int i = 0; i < targets.length; i++) {
						try {
							iMeasureGroup target = (virtual)
									.getObject(targets[i]);
							MeasureGroupClient client = new MeasureGroupClient();

							Instant instant = from.getValue().atTime(6, 0)
									.atZone(ZoneId.systemDefault()).toInstant();
							Date fromDate = Date.from(instant);
							instant = to.getValue().atTime(6, 0)
									.atZone(ZoneId.systemDefault()).toInstant();
							Date toDate = Date.from(instant);

							SortedMap<Date, Float> result = client
									.cumulativeAggregate(target,
											(String) comboBox
													.getSelectionModel()
													.getSelectedItem(),
											fromDate, toDate,
											(String) comboBox2
													.getSelectionModel()
													.getSelectedItem());
							XYChart.Series series1 = new XYChart.Series();
							series1.setName(target.getName());
							for (Entry<Date, Float> entry : result.entrySet()) {
								series1.getData().add(
										new XYChart.Data(
												((Date) entry.getKey())
														.toString(), entry
														.getValue()));
							}

							lineChart.getData().add(series1);

						} catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Form not filled!");
							String s = "Filter, Quantity selections and filter parameters must be properly filled: "
									+ e;
							alert.setContentText(s);
							alert.showAndWait();

						}

					}
					Scene scene = new Scene(lineChart, 800.0D, 600.0D);
					Stage graph = new Stage();
					graph.setScene(scene);
					graph.show();

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Form not filled");
					String s = "Filter, Quantity selections and filter parameters must be properly filled  ";
					alert.setContentText(s);
					alert.showAndWait();
				}

			}
		});
		text1.textProperty().addListener((observable, oldValue, newValue) -> {
			changeCombobox(newValue);

		});
		return inputGridPane;

	}

	public GridPane weightedAverage() {
		Text title = new Text("Weighted Average Pressure");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		Label label1 = new Label("Group selection: ");
		Label label2 = new Label("Rate: ");
		Label label3 = new Label("Pressure: ");
		Label label4 = new Label("From: ");
		Label label5 = new Label("to: ");

		text1 = new TextField();

		Button buttonBrowse = new Button("Browse");
		Button buttonDraw = new Button("Show");
		Button buttonSave = new Button("Save to file");
		from = new DatePicker(LocalDate.of(2011, 1, 3));
		to = new DatePicker(LocalDate.of(2011, 2, 3));
		GridPane inputGridPane = new GridPane();
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(label1, 0, 1);
		comboBox = new ComboBox();
		comboBox.setEditable(true);
		comboBox2 = new ComboBox();
		comboBox2.setEditable(true);
		GridPane.setConstraints(label2, 0, 2);
		GridPane.setConstraints(label3, 0, 3);

		GridPane.setConstraints(label4, 0, 4);
		GridPane.setConstraints(label5, 0, 5);
		GridPane.setConstraints(comboBox, 1, 2);
		GridPane.setConstraints(comboBox2, 1, 3);

		GridPane.setConstraints(text1, 1, 1);
		GridPane.setConstraints(from, 1, 4);
		GridPane.setConstraints(to, 1, 5);

		GridPane.setConstraints(buttonBrowse, 2, 1);
		GridPane.setConstraints(buttonDraw, 0, 6);
		GridPane.setConstraints(buttonSave, 1, 6);

		inputGridPane.getColumnConstraints().add(new ColumnConstraints(100));
		inputGridPane.setPadding(new Insets(10));
		;
		inputGridPane.setHgap(6.0D);
		inputGridPane.setVgap(6.0D);
		inputGridPane.getChildren().addAll(
				new Node[] { title, label1, label2, label3, label4, label5,
						from, to, text1, buttonBrowse, buttonDraw, buttonSave,
						comboBox, comboBox2 });

		buttonBrowse.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				GetBox selectWindow = new GetBox(virtual, text1);
				Platform.runLater(new Runnable() {
					public void run() {
						try {
							// new GetBox(virtual).start(new Stage());
							selectWindow.start(new Stage());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						// text1.setText(selectWindow.selection);
					}
				});
			}
		});
		buttonSave.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				if (results == null) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("No result to be saved");
					String s = "Calculate results first by Show button ";
					alert.setContentText(s);
					alert.showAndWait();
				} else {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Save result");
					File file = fileChooser.showSaveDialog(stage);
					if (file != null) {
						try {
							FileWriter fw = new FileWriter(file);
							fw.write(results);
							fw.close();
						} catch (IOException ex) {
							System.out.println(ex.getMessage());
						}
					}
				}
			}
		});

		buttonDraw.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {

				HashMap<String, List<Float>> result = new HashMap<String, List<Float>>();
				if (checkForm()) {
					iMeasurePoint template = virtual.getMeasurePoint(comboBox
							.getSelectionModel().getSelectedItem().toString());
					String[] targets = text1.getText().split(", ");
					Instant instant = from.getValue().atTime(6, 0)
							.atZone(ZoneId.systemDefault()).toInstant();
					Date fromDate = Date.from(instant);
					instant = to.getValue().atTime(6, 0)
							.atZone(ZoneId.systemDefault()).toInstant();
					Date toDate = Date.from(instant);
					try {
						for (int i = 0; i < targets.length; i++) {
							iMeasureGroup target = (virtual)
									.getObject(targets[i]);
							MeasureGroupClient client = new MeasureGroupClient();
							List<Float> fl = client.weightedAverage(target,
									(String) comboBox.getSelectionModel()
											.getSelectedItem(),
									(String) comboBox2.getSelectionModel()
											.getSelectedItem(), fromDate,
									toDate);
							result.put(targets[i], fl);

						}
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Form not filled!");
						String s = "Filter, Quantity selections and filter parameters must be properly filled: "
								+ e;
						alert.setContentText(s);
						alert.showAndWait();

					}
					Stage check = new Stage();
					check.setTitle("Weighted Average Pressure");
					VBox vbox = new VBox();
					vbox.setPadding(new Insets(5));
					vbox.setSpacing(8);
					results = "";
					int j = 0;
					for (Entry e : result.entrySet()) {
						List<Float> values = (List<Float>) e.getValue();
						for (Float f : values) {
							results = results + e.getKey() + " = "
									+ f.toString() + "\n";
						}

					}
					Label r = new Label(results);
					VBox.setMargin(r, new Insets(0, 0, 0, 8));
					vbox.getChildren().add(r);
					check.setScene(new Scene(vbox));
					check.show();

				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Form not filled");
					String s = "Filter, Quantity selections and filter parameters must be properly filled  ";
					alert.setContentText(s);
					alert.showAndWait();
				}

			}
		});
		text1.textProperty().addListener((observable, oldValue, newValue) -> {
			changeCombobox(newValue);
			changeCombobox2(newValue);

		});
		return inputGridPane;
	}

	private void changeCombobox(String newValue) {

		String[] targets = newValue.split(", ");

		for (int i = 0; i < targets.length; i++) {
			try {
				AbstractGroup target = (AbstractGroup) (virtual)
						.getObject(targets[i]);
				List<String> points = target.getPointNames();
				for (String quant : points) {

					if (!comboBox.getItems().contains(quant)) {
						comboBox.getItems().add(quant);
					}
				}

			} catch (Exception e) {
			}
		}

	}

	private void changeCombobox2(String newValue) {

		String[] targets = newValue.split(", ");

		for (int i = 0; i < targets.length; i++) {
			try {
				AbstractGroup target = (AbstractGroup) (virtual)
						.getObject(targets[i]);
				List<String> points = target.getPointNames();
				for (String quant : points) {

					if (!comboBox2.getItems().contains(quant)) {
						comboBox2.getItems().add(quant);
					}
				}

			} catch (Exception e) {
			}
		}

	}

	public Boolean checkForm() {
		if (comboBox.getSelectionModel().getSelectedItem() == null) {
			return false;
		}
		if (text1.getText() == null | (text1.getText() == "")) {
			return false;
		}

		return true;
	}

}
