import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import measurePoints.iMeasureGroup;

public class Menu {
	BorderPane border;
	Stage stage;
	iMeasureGroup virtual;

	public Menu(BorderPane border, Stage stage, iMeasureGroup virtual) {
		this.border = border;
		this.stage = stage;
		this.virtual = virtual;
	}

	public HBox addHBox() {
		HBox hbox = new HBox();
		hbox.setPadding(new Insets(15, 12, 15, 12));
		hbox.setSpacing(20);
		hbox.setStyle("-fx-background-color: #336699;");

		Button buttonManager = new Button("Storage Management");
		buttonManager.setPrefSize(150, 20);

		Button buttonFilterData = new Button("Filter Data");
		buttonFilterData.setPrefSize(100, 20);

		Button buttonFunctions = new Button("Functions");
		buttonFunctions.setPrefSize(100, 20);

		hbox.getChildren().addAll(buttonManager, buttonFilterData,
				buttonFunctions);

		buttonManager.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				StorageManagement createStorage = new StorageManagement(border,
						stage, virtual);
				VBox vbox = createStorage.addVBox();
				vbox.setStyle("-fx-background-color: #E0F8F7;");
				border.setLeft(vbox);
				border.setCenter(new VBox());
				stage.sizeToScene();

			}
		});
		buttonFilterData.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				FilterData filterData = new FilterData(border, stage, virtual);
				VBox vbox = filterData.addVBox();
				vbox.setStyle("-fx-background-color: #E0F8F7;");
				border.setLeft(vbox);
				border.setCenter(new VBox());
				stage.sizeToScene();

			}
		});
		buttonFunctions.setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				Functions calculate = new Functions(border, stage, virtual);
				VBox vbox = calculate.addVBox();
				vbox.setStyle("-fx-background-color: #E0F8F7;");
				border.setLeft(vbox);
				border.setCenter(new VBox());
				stage.sizeToScene();

			}
		});

		return hbox;
	}

}
