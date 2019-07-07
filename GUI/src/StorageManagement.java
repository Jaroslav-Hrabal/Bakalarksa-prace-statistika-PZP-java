import java.io.File;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
import measurePoints.Group;
import measurePoints.Quantity;
import measurePoints.iMeasureGroup;
import readers.ReaderJSON;
import readers.ReaderTBD;

public class StorageManagement {
	BorderPane border;
	Stage stage;
	iMeasureGroup virtual;

	public StorageManagement(BorderPane border, Stage stage,
			iMeasureGroup virtual) {
		this.border = border;
		this.stage = stage;
		this.virtual = virtual;
	}

	// this is the left side menu after clicking Storage Management
	public VBox addVBox() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		Hyperlink options[] = new Hyperlink[] { new Hyperlink("Read TBD"),
				new Hyperlink("Add TBD data"), new Hyperlink("Read JSON"),
				new Hyperlink("Remove") };

		for (int i = 0; i < 4; i++) {
			VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
			vbox.getChildren().add(options[i]);
		}
		options[0].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				GridPane GridPane = addTBDStorage();
				border.setCenter(GridPane);
				stage.sizeToScene();
			}
		});
		options[1].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				GridPane GridPane = addTBDData();
				border.setCenter(GridPane);
				stage.sizeToScene();
			}
		});
		options[2].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				GridPane GridPane = addJSONStorage();
				border.setCenter(GridPane);
				stage.sizeToScene();
			}
		});
		options[3].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				GridPane GridPane = remove();
				border.setCenter(GridPane);
				stage.sizeToScene();
			}
		});

		return vbox;
	}

	// this is the center of main application for creating TBD storages
	public GridPane addTBDStorage() {
		Text title = new Text("Create storage from TBD file");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		Label label1 = new Label("Storage Name: ");
		Label label2 = new Label("Group File: ");

		TextField text1 = new TextField();
		TextField text2 = new TextField();
		Button buttonBrowse = new Button("Browse");
		Button buttonCreate = new Button("Create Storage");
		GridPane inputGridPane = new GridPane();
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(label1, 0, 1);
		GridPane.setConstraints(label2, 0, 2);
		GridPane.setConstraints(text1, 1, 1);
		GridPane.setConstraints(text2, 1, 2);
		GridPane.setConstraints(buttonBrowse, 2, 2);
		GridPane.setConstraints(buttonCreate, 1, 3);
		inputGridPane.getColumnConstraints().add(new ColumnConstraints(100));
		inputGridPane.setPadding(new Insets(10));
		;
		inputGridPane.setHgap(6.0D);
		inputGridPane.setVgap(6.0D);
		inputGridPane.getChildren().addAll(
				new Node[] { title, label1, label2, text1, text2, buttonBrowse,
						buttonCreate });

		buttonBrowse.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open TBD file");
				File file = fileChooser.showOpenDialog(stage);
				String address = file.getAbsolutePath();
				text2.setText(address);
			}
		});
		buttonCreate.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				if (text1.getText().isEmpty() || text2.getText().isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Form not filled");
					String s = "Storage name and file address need to be properly filled  ";
					alert.setContentText(s);
					alert.showAndWait();
				} else {
					try {
						Group storage = new Group(text1.getText());
						ReaderTBD tbd = new ReaderTBD();
						tbd.read(storage, text2.getText());
						virtual.addObject(storage);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("Creation succesfull");
						alert.setContentText("Storage has been created");
						alert.showAndWait();
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Address is invalid");
						String s = "Address is wrong or TBD group file is in wrong format";
						alert.setContentText(s);
						alert.showAndWait();
					}
				}
			}
		});

		return inputGridPane;
	}

	// this is the center of main application for adding data to already
	// existing TBD storages
	public GridPane addTBDData() {
		Text title = new Text("Add data to previously created TBD storage");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		Label label1 = new Label("Choose Storage: ");
		Label label2 = new Label("Head File: ");
		Label label3 = new Label("Data File: ");
		Label label4 = new Label("Quantity Name: ");
		Label label5 = new Label("Quantity Units: ");
		Label label6 = new Label("Aggregate (Sum/Average): ");
		Label label7 = new Label("Skip first collum (0/1): ");

		TextField text1 = new TextField();
		TextField text2 = new TextField();
		TextField text3 = new TextField();
		TextField text4 = new TextField();
		TextField text5 = new TextField("Sum");
		TextField text6 = new TextField("0");

		Button buttonBrowse1 = new Button("Browse");
		Button buttonBrowse2 = new Button("Browse");
		Button buttonCreate = new Button("Add Data");

		ObservableList<Object> options = FXCollections
				.observableArrayList(((Group) virtual).ObjectList);
		final ComboBox comboBox = new ComboBox(options);

		GridPane inputGridPane = new GridPane();
		inputGridPane.getColumnConstraints().add(new ColumnConstraints(150));
		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(label1, 0, 1);
		GridPane.setConstraints(label2, 0, 2);
		GridPane.setConstraints(label3, 0, 3);
		GridPane.setConstraints(label4, 0, 4);
		GridPane.setConstraints(label5, 0, 5);
		GridPane.setConstraints(label6, 0, 6);
		GridPane.setConstraints(label7, 0, 7);
		GridPane.setConstraints(comboBox, 1, 1);
		GridPane.setConstraints(text1, 1, 2);
		GridPane.setConstraints(text2, 1, 3);
		GridPane.setConstraints(text3, 1, 4);
		GridPane.setConstraints(text4, 1, 5);
		GridPane.setConstraints(text5, 1, 6);
		GridPane.setConstraints(text6, 1, 7);
		GridPane.setConstraints(buttonBrowse1, 2, 2);
		GridPane.setConstraints(buttonBrowse2, 2, 3);
		GridPane.setConstraints(buttonCreate, 1, 8);
		inputGridPane.getColumnConstraints().add(new ColumnConstraints(100));
		inputGridPane.setPadding(new Insets(10));
		;
		inputGridPane.setHgap(6.0D);
		inputGridPane.setVgap(6.0D);
		inputGridPane.getChildren().addAll(
				new Node[] { title, label1, label2, label3, label4, label5,
						label6, label7, comboBox, text1, text2, text3, text4,
						text5, text6, buttonBrowse1, buttonBrowse2,
						buttonCreate });

		buttonBrowse1.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open head file");
				File file = fileChooser.showOpenDialog(stage);
				String address = file.getAbsolutePath();
				text1.setText(address);
			}
		});
		buttonBrowse2.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open data file");
				File file = fileChooser.showOpenDialog(stage);
				String address = file.getAbsolutePath();
				text2.setText(address);
			}
		});
		buttonCreate.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				if (text1.getText().isEmpty() || text2.getText().isEmpty()
						|| text3.getText().isEmpty()
						|| text4.getText().isEmpty()
						|| text5.getText().isEmpty()
						|| text6.getText().isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Form not filled");
					String s = "Both addresses, name and units must be filled correctly";
					alert.setContentText(s);
					alert.showAndWait();
				} else {
					if (comboBox.getSelectionModel().isEmpty()) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Form not filled");
						String s = "Choose storage from combobox";
						alert.setContentText(s);
						alert.showAndWait();
					} else {
						try {
							Group storage = (Group) comboBox
									.getSelectionModel().getSelectedItem();
							ReaderTBD tbd = new ReaderTBD();
							if (text6.getText().equals("1")) {
								tbd.skipCollum = true;
							}
							tbd.readData(storage, new Quantity(text3.getText(),
									text4.getText(), text5.getText()), text1
									.getText(), text2.getText(),
									"dd.MM.yy:HH:mm");
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Information Dialog");
							alert.setHeaderText("Creation succesfull");
							alert.setContentText("Storage has been filled with data");
							alert.showAndWait();
						} catch (Exception e) {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Address is invalid");
							String s = "Head and data file or address is in wrong format";
							alert.setContentText(s);
							alert.showAndWait();
						}
					}
				}
			}
		});
		return inputGridPane;
	}

	// this is the center of main application for creating JSON storages
	public GridPane addJSONStorage() {
		Text title = new Text("Create storage from JSON file and read data");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));

		Label label1 = new Label("Storage Name: ");
		Label label2 = new Label("Configuration File: ");
		TextField text1 = new TextField();
		TextField text2 = new TextField();
		Button buttonBrowse = new Button("Browse");
		Button buttonCreate = new Button("Create Storage");
		GridPane inputGridPane = new GridPane();

		GridPane.setConstraints(title, 0, 0);
		GridPane.setConstraints(label1, 0, 1);
		GridPane.setConstraints(label2, 0, 2);
		GridPane.setConstraints(text1, 1, 1);
		GridPane.setConstraints(text2, 1, 2);
		GridPane.setConstraints(buttonBrowse, 2, 2);
		GridPane.setConstraints(buttonCreate, 1, 3);
		inputGridPane.getColumnConstraints().add(new ColumnConstraints(100));
		inputGridPane.setPadding(new Insets(10));
		;
		inputGridPane.setHgap(6.0D);
		inputGridPane.setVgap(6.0D);
		inputGridPane.getChildren().addAll(
				new Node[] { title, label1, label2, text1, text2, buttonBrowse,
						buttonCreate });

		buttonBrowse.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open JSON file");
				File file = fileChooser.showOpenDialog(stage);
				String address = file.getAbsolutePath();
				text2.setText(address);
			}
		});

		buttonCreate.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				if (text1.getText().isEmpty() || text2.getText().isEmpty()) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Form not filled");
					String s = "Storage name and file address need to be properly filled  ";
					alert.setContentText(s);
					alert.showAndWait();
				} else {
					try {
						Group storage = new Group(text1.getText());
						ReaderJSON json = new ReaderJSON();
						json.read(storage, text2.getText());
						virtual.addObject(storage);
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Information Dialog");
						alert.setHeaderText("Creation succesfull");
						alert.setContentText("Storage has been created and filled with data");
						alert.showAndWait();
					} catch (Exception e) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Address is invalid");
						String s = "Address is wrong or main JSON configuration file is in wrong format";
						alert.setContentText(s);
						alert.showAndWait();
					}
				}
			}
		});
		return inputGridPane;
	}

	public GridPane remove() {
		GetBox remove = new GetBox(virtual);
		GridPane inputGridPane = remove.select();

		return inputGridPane;
	}

}
