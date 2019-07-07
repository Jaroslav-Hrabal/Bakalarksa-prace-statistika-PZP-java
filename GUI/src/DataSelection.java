import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;
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
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import measurePoints.AbstractGroup;
import measurePoints.Group;
import measurePoints.iMeasureGroup;
import measurePoints.iMeasurePoint;
import functions.MeasureGroupClient;

public class DataSelection {
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

	public DataSelection(iMeasureGroup virtual, String title) {
		this.virtual = virtual;
		this.name = title;
	}

	// Center part of the main window when selecting Range/Constant/Peak data
	// filters.
	@SuppressWarnings("unchecked")
	public GridPane selectFilter() {
		Text title = new Text(name);
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		Label label1 = new Label("Filter selection: ");
		Label label2 = new Label("Quantity selection: ");
		Label label3 = new Label("From: ");
		Label label4 = new Label("to: ");
		Label label5 = new Label("First filter parameter: ");
		Label label6 = new Label("Second filter parameter: ");
		text1 = new TextField();
		text2 = new TextField();
		text3 = new TextField();
		// TextField text2 = new TextField();
		comboBox = new ComboBox();
		comboBox.setEditable(true);
		Button buttonBrowse = new Button("Browse");
		Button buttonCheck = new Button("Check");
		Button buttonRemove = new Button("Remove");
		Button buttonReplace = new Button("Replace");
		from = new DatePicker(LocalDate.of(2011, 1, 3));
		to = new DatePicker(LocalDate.of(2011, 2, 3));
		// from.setDayCellFactory();
		GridPane inputGridPane = new GridPane();
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(label1, 0, 1);
		GridPane.setConstraints(label2, 0, 2);
		GridPane.setConstraints(label3, 0, 3);
		GridPane.setConstraints(label4, 0, 4);
		GridPane.setConstraints(label5, 0, 5);
		GridPane.setConstraints(label6, 0, 6);
		GridPane.setConstraints(text2, 1, 5);
		GridPane.setConstraints(text3, 1, 6);
		GridPane.setConstraints(comboBox, 1, 2);
		GridPane.setConstraints(text1, 1, 1);
		GridPane.setConstraints(from, 1, 3);
		GridPane.setConstraints(to, 1, 4);
		// GridPane.setConstraints(text2, 1, 2);
		GridPane.setConstraints(buttonBrowse, 2, 1);
		GridPane.setConstraints(buttonCheck, 0, 7);
		GridPane.setConstraints(buttonRemove, 1, 7);
		GridPane.setConstraints(buttonReplace, 2, 7);
		inputGridPane.getColumnConstraints().add(new ColumnConstraints(100));
		inputGridPane.setPadding(new Insets(10));
		;
		inputGridPane.setHgap(6.0D);
		inputGridPane.setVgap(6.0D);
		inputGridPane.getChildren()
				.addAll(new Node[] { title, label1, label2, label3, label4,
						label5, label6, from, to, comboBox, text1, text2,
						text3, buttonBrowse, buttonCheck, buttonRemove,
						buttonReplace });

		text1.textProperty().addListener((observable, oldValue, newValue) -> {
			changeCombobox(newValue);

		});

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

		buttonCheck.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Messages mes = new Messages();
				if (checkForm2()) {
					/*
					 * Platform.runLater(new Runnable() { public void run() {
					 * try {
					 * 
					 * mes.filterCheck(new Stage()); } catch (Exception e) { //
					 * TODO Auto-generated catch block e.printStackTrace(); } }
					 * });
					 */
					String[] targets = text1.getText().split(", ");
					for (int i = 0; i < targets.length; i++) {
						List<Date> result = null;
						try {
							iMeasureGroup target = (virtual)
									.getObject(targets[i]);
							MeasureGroupClient client = new MeasureGroupClient();
							String[] splitTitle = name.split(" ");
							String method = splitTitle[0];
							Instant instant = from.getValue().atTime(6, 0)
									.atZone(ZoneId.systemDefault()).toInstant();
							Date fromDate = Date.from(instant);
							instant = to.getValue().atTime(6, 0)
									.atZone(ZoneId.systemDefault()).toInstant();
							Date toDate = Date.from(instant);
							result = client.filterData(target,
									(comboBox.getValue()).toString(), fromDate,
									toDate, method, "",
									Float.parseFloat(text2.getText()),
									Float.parseFloat(text3.getText()));
						} catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Form not filled");
							String s = "Filter, Quantity selections and filter parameters must be properly filled: "
									+ e;
							alert.setContentText(s);
							alert.showAndWait();
						}
						if (result != null) {
							/*
							 * Alert alert = new Alert(AlertType.INFORMATION);
							 * alert.setTitle("Data filter was succesfull");
							 * alert
							 * .setHeaderText("Following dates contain data errors:"
							 * ); String results = ""; for(Date e : result){
							 * results = results + ", " + e.toString(); }
							 * alert.setContentText(results);
							 * alert.showAndWait();
							 */
							Stage check = new Stage();
							check.setTitle("Filtered dates");
							VBox vbox = new VBox();
							vbox.setPadding(new Insets(5));
							vbox.setSpacing(8);
							String results = "";
							int j = 0;
							for (Date e : result) {
								results = results + e.toString() + ", ";
								if (j > 5) {
									results = results + "\n";
									j = 0;
								}
								j++;
							}
							Label r = new Label(results);
							VBox.setMargin(r, new Insets(0, 0, 0, 8));
							vbox.getChildren().add(r);
							check.setScene(new Scene(vbox));
							check.show();

						}

					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Form not filled");
					String s = "Filter, Quantity selections and filter parameters must be properly filled  ";
					alert.setContentText(s);
					alert.showAndWait();
				}

			}
		});

		buttonRemove.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				if (checkForm2()) {
					String[] targets = text1.getText().split(", ");
					for (int i = 0; i < targets.length; i++) {
						List<Date> result = null;
						try {
							iMeasureGroup target = (virtual)
									.getObject(targets[i]);
							MeasureGroupClient client = new MeasureGroupClient();
							String[] splitTitle = name.split(" ");
							String method = splitTitle[0];
							Instant instant = from.getValue().atTime(6, 0)
									.atZone(ZoneId.systemDefault()).toInstant();
							Date fromDate = Date.from(instant);
							instant = to.getValue().atTime(6, 0)
									.atZone(ZoneId.systemDefault()).toInstant();
							Date toDate = Date.from(instant);
							client.filterData(target, (String) comboBox
									.getSelectionModel().getSelectedItem(),
									fromDate, toDate, method, "Remove", Float
											.parseFloat(text2.getText()), Float
											.parseFloat(text3.getText()));
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Dialog");
							alert.setHeaderText(null);
							alert.setContentText("Data have been removed!");
							alert.showAndWait();
						} catch (Exception e) {
							System.out.print(e);
							/*
							 * Alert alert = new Alert(AlertType.ERROR);
							 * alert.setTitle("Form not filled!"); String s =
							 * "Filter, Quantity selections and filter parameters must be properly filled: "
							 * + e; alert.setContentText(s);
							 * alert.showAndWait();
							 */
						}

					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Form not filled");
					String s = "Filter, Quantity selections and filter parameters must be properly filled  ";
					alert.setContentText(s);
					alert.showAndWait();
				}

			}
		});

		buttonReplace.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				String interpolation = null;
				if (checkForm2()) {
					List<String> choices = new ArrayList<>();
					choices.add("Average");
					choices.add("Linear");
					choices.add("Spline");

					ChoiceDialog<String> dialog = new ChoiceDialog<>("Linear",
							choices);
					dialog.setTitle("Choice Dialog");
					dialog.setHeaderText("Choose function for data calculation");
					dialog.setContentText("Choose method:");
					Optional<String> res = dialog.showAndWait();
					if (res.isPresent()) {
						interpolation = res.get();
					}

					String[] targets = text1.getText().split(", ");
					for (int i = 0; i < targets.length; i++) {
						List<Date> result = null;
						try {
							iMeasureGroup target = (virtual)
									.getObject(targets[i]);
							MeasureGroupClient client = new MeasureGroupClient();
							String[] splitTitle = name.split(" ");
							String method = splitTitle[0];
							Instant instant = from.getValue().atTime(6, 0)
									.atZone(ZoneId.systemDefault()).toInstant();
							Date fromDate = Date.from(instant);
							instant = to.getValue().atTime(6, 0)
									.atZone(ZoneId.systemDefault()).toInstant();
							Date toDate = Date.from(instant);
							client.filterData(target,
									(comboBox.getValue()).toString(), fromDate,
									toDate, method, "Replace " + interpolation,
									Float.parseFloat(text2.getText()),
									Float.parseFloat(text3.getText()));
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Dialog");
							alert.setHeaderText(null);
							alert.setContentText("Data have been replaced!");
							alert.showAndWait();
						} catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Form not filled");
							String s = "Filter, Quantity selections and filter parameters must be properly filled: "
									+ e;
							alert.setContentText(s);
							alert.showAndWait();
						}

					}
				} else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Form not filled");
					String s = "Filter, Quantity selections and filter parameters must be properly filled  ";
					alert.setContentText(s);
					alert.showAndWait();
				}

			}
		});

		return inputGridPane;
	}

	public GridPane integrity() {
		Text title = new Text("Rate and pressure integrity");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		Label label1 = new Label("Group selection: ");
		Label label2 = new Label("Rate: ");
		Label label3 = new Label("Pressure: ");
		Label label4 = new Label("From: ");
		Label label5 = new Label("to: ");
		Label label6 = new Label("length: ");

		text1 = new TextField();

		text2 = new TextField("5");
		comboBox = new ComboBox();
		comboBox.setEditable(true);
		comboBox2 = new ComboBox();
		comboBox2.setEditable(true);
		Button buttonBrowse = new Button("Browse");
		Button buttonDraw = new Button("Draw");

		from = new DatePicker(LocalDate.of(2011, 1, 3));
		to = new DatePicker(LocalDate.of(2011, 2, 3));
		GridPane inputGridPane = new GridPane();
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(label1, 0, 1);
		GridPane.setConstraints(label2, 0, 2);
		GridPane.setConstraints(label3, 0, 3);
		GridPane.setConstraints(label4, 0, 4);
		GridPane.setConstraints(label5, 0, 5);
		GridPane.setConstraints(label6, 0, 6);
		GridPane.setConstraints(comboBox, 1, 2);
		GridPane.setConstraints(comboBox2, 1, 3);
		GridPane.setConstraints(text1, 1, 1);
		GridPane.setConstraints(text2, 1, 6);
		GridPane.setConstraints(from, 1, 4);
		GridPane.setConstraints(to, 1, 5);
		// GridPane.setConstraints(text2, 1, 2);
		GridPane.setConstraints(buttonBrowse, 2, 1);
		GridPane.setConstraints(buttonDraw, 0, 7);

		inputGridPane.getColumnConstraints().add(new ColumnConstraints(100));
		inputGridPane.setPadding(new Insets(10));
		;
		inputGridPane.setHgap(6.0D);
		inputGridPane.setVgap(6.0D);
		inputGridPane.getChildren().addAll(
				new Node[] { title, label1, label2, label3, label4, label5,
						label6, from, to, comboBox, comboBox2, text1, text2,
						buttonBrowse, buttonDraw });

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

							SortedMap<Date, Float> result = client.integrity(
									target, (String) comboBox
											.getSelectionModel()
											.getSelectedItem(),
									(String) comboBox2.getSelectionModel()
											.getSelectedItem(), toDate, toDate,
									Float.parseFloat(text2.getText()));
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

	public Date getFromDate() {
		Date date = null;
		iMeasurePoint point = (iMeasurePoint) comboBox.getSelectionModel()
				.getSelectedItem();
		String quant = point.getName();
		for (Object obj : ((Group) virtual).ObjectList) {
			AbstractGroup object = (AbstractGroup) obj;
			Date tmp = object.getMeasurePoint(quant).getData(null, null)
					.firstKey();
			if (date == null) {
				date = tmp;
			} else {
				if (tmp.before(date)) {
					date = tmp;
				}
			}
		}

		return date;
	}

	public Date getToDate() {
		Date date = null;
		iMeasurePoint point = (iMeasurePoint) comboBox.getSelectionModel()
				.getSelectedItem();
		String quant = point.getName();
		for (Object obj : ((Group) virtual).ObjectList) {
			AbstractGroup object = (AbstractGroup) obj;
			Date tmp = object.getMeasurePoint(quant).getData(null, null)
					.lastKey();
			if (date == null) {
				date = tmp;
			} else {
				if (tmp.after(date)) {
					date = tmp;
				}
			}
		}

		return date;
	}

	public Boolean checkForm() {
		if (comboBox.getSelectionModel().getSelectedItem() == null) {
			return false;
		}
		if (comboBox2.getSelectionModel().getSelectedItem() == null) {
			return false;
		}
		if (text1.getText() == null | (text1.getText() == "")) {
			return false;
		}
		if (text2.getText() == null | (text2.getText() == "")) {
			return false;
		}

		return true;
	}
	public Boolean checkForm2() {
		if (comboBox.getSelectionModel().getSelectedItem() == null) {
			return false;
		}

		if (text1.getText() == null | (text1.getText() == "")) {
			return false;
		}
		if (text2.getText() == null | (text2.getText() == "")) {
			return false;
		}

		return true;
	}
}
