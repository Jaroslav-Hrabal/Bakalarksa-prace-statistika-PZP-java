import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import measurePoints.AbstractGroup;
import measurePoints.Group;
import measurePoints.iMeasureGroup;

public class GetBox {
	iMeasureGroup virtual;
	TextField selection;

	public GetBox(iMeasureGroup virtual) {
		this.virtual = virtual;
	}

	public GetBox(iMeasureGroup virtual, TextField selection) {
		this.virtual = virtual;
		this.selection = selection;
	}

	ComboBox comboBox1;

	public GridPane select() {
		GridPane inputGridPane = new GridPane();
		Label label1 = new Label("Storage Name: ");
		Label label2 = new Label("Group Name: ");
		Label label3 = new Label("Well Name: ");
		ObservableList<Object> options1 = FXCollections
				.observableArrayList(((Group) virtual).ObjectList);
		comboBox1 = new ComboBox(options1);

		ComboBox comboBox2 = new ComboBox();

		ComboBox comboBox3 = new ComboBox();
		GridPane.setConstraints(label1, 0, 0);
		GridPane.setConstraints(label2, 1, 0);
		GridPane.setConstraints(label3, 2, 0);
		GridPane.setConstraints(comboBox1, 0, 1);
		GridPane.setConstraints(comboBox2, 1, 1);
		GridPane.setConstraints(comboBox3, 2, 1);
		inputGridPane.setPadding(new Insets(20));
		;
		inputGridPane.setHgap(6.0D);
		inputGridPane.setVgap(6.0D);
		inputGridPane.getChildren().addAll(
				new Node[] { label1, label2, label3, comboBox1, comboBox2,
						comboBox3 });
		Label label4 = new Label("Object to be removed: ");
		TextField text4 = new TextField();
		Button buttonRemove = new Button("Remove");

		GridPane.setConstraints(label4, 0, 2);

		GridPane.setConstraints(text4, 1, 2);

		GridPane.setConstraints(buttonRemove, 2, 2);

		inputGridPane.getChildren().addAll(
				new Node[] { label4, text4, buttonRemove });
		ComboBox comb1 = (ComboBox) inputGridPane.getChildren().get(3);

		comboBox1.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				if (arg2 != null) {
					AbstractGroup obj = (AbstractGroup) arg2;
					text4.setText(obj.getName());
					comboBox2.getItems().clear();
					comboBox2.getItems().addAll(obj.getObjectList());
				}
			}
		});

		comboBox2.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				if (arg2 != null) {
					AbstractGroup obj = (AbstractGroup) arg2;
					text4.setText(obj.getName());
					comboBox3.getItems().clear();
					comboBox3.getItems().addAll(obj.getObjectList());
				}
			}
		});
		comboBox3.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				if (arg2 != null) {
					AbstractGroup obj = (AbstractGroup) arg2;

					text4.setText(obj.getName());
				}

			}
		});

		buttonRemove.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				iMeasureGroup obj = null;
				try {
					comboBox1.getItems().clear();
					comboBox2.getItems().clear();
					comboBox2.valueProperty().set(null);
					comboBox3.getItems().clear();
					comboBox3.valueProperty().set(null);
					obj = virtual.removeObject(text4.getText());
				} catch (Exception e) {
				}
				comboBox1.getItems().addAll(((Group) virtual).ObjectList);
				comboBox1.valueProperty().set(null);
				if (obj == null) {

				} else {

				}
			}
		});
		return inputGridPane;

	}

	public void start(final Stage stage) throws Exception {
		stage.setTitle("Select measure group");
		GridPane inputGridPane = new GridPane();
		Label label1 = new Label("Storage Name: ");
		Label label2 = new Label("Group Name: ");
		Label label3 = new Label("Well Name: ");
		ObservableList<Object> options1 = FXCollections
				.observableArrayList(((Group) virtual).ObjectList);
		ComboBox comboBox1 = new ComboBox(options1);

		ComboBox comboBox2 = new ComboBox();

		ComboBox comboBox3 = new ComboBox();
		GridPane.setConstraints(label1, 0, 0);
		GridPane.setConstraints(label2, 1, 0);
		GridPane.setConstraints(label3, 2, 0);
		GridPane.setConstraints(comboBox1, 0, 1);
		GridPane.setConstraints(comboBox2, 1, 1);
		GridPane.setConstraints(comboBox3, 2, 1);
		inputGridPane.setPadding(new Insets(20));
		;
		inputGridPane.setHgap(6.0D);
		inputGridPane.setVgap(6.0D);
		inputGridPane.getChildren().addAll(
				new Node[] { label1, label2, label3, comboBox1, comboBox2,
						comboBox3 });
		Label label4 = new Label("Object to be removed: ");
		TextField text4 = new TextField();
		Button buttonRemove = new Button("Add");

		GridPane.setConstraints(label4, 0, 2);

		GridPane.setConstraints(text4, 1, 2);

		GridPane.setConstraints(buttonRemove, 2, 2);

		inputGridPane.getChildren().addAll(
				new Node[] { label4, text4, buttonRemove });
		ComboBox comb1 = (ComboBox) inputGridPane.getChildren().get(3);

		comboBox1.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				if (arg2 != null) {
					AbstractGroup obj = (AbstractGroup) arg2;
					text4.setText(obj.getName());
					comboBox2.getItems().clear();
					comboBox2.getItems().addAll(obj.getObjectList());
				}
			}
		});

		comboBox2.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				if (arg2 != null) {
					AbstractGroup obj = (AbstractGroup) arg2;
					text4.setText(obj.getName());
					comboBox3.getItems().clear();
					comboBox3.getItems().addAll(obj.getObjectList());
				}
			}
		});
		comboBox3.valueProperty().addListener(new ChangeListener<Object>() {
			@Override
			public void changed(ObservableValue<? extends Object> arg0,
					Object arg1, Object arg2) {
				if (arg2 != null) {
					AbstractGroup obj = (AbstractGroup) arg2;

					text4.setText(obj.getName());
				}

			}
		});

		buttonRemove.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				selection.setText(selection.getText() + text4.getText() + ", ");
				stage.close();
			}
		});
		stage.setScene(new Scene(inputGridPane));
		stage.show();

	}

}
