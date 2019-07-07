import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import measurePoints.iMeasureGroup;

public class FilterData {
	BorderPane border;
	Stage stage;
	iMeasureGroup virtual;

	public FilterData(BorderPane border, Stage stage, iMeasureGroup virtual) {
		this.border = border;
		this.stage = stage;
		this.virtual = virtual;
	}

	public VBox addVBox() {
		VBox vbox = new VBox();
		vbox.setPadding(new Insets(10));
		vbox.setSpacing(8);
		Hyperlink options[] = new Hyperlink[] { new Hyperlink("Range"),
				new Hyperlink("Constant"), new Hyperlink("Peak"),
				new Hyperlink("Pressure Integrity") };

		for (int i = 0; i < 4; i++) {
			VBox.setMargin(options[i], new Insets(0, 0, 0, 8));
			vbox.getChildren().add(options[i]);
		}

		options[0].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				DataSelection select = new DataSelection(virtual,
						"Range data filter");
				GridPane GridPane = select.selectFilter();
				border.setCenter(GridPane);
				stage.sizeToScene();

			}
		});
		options[1].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				DataSelection select = new DataSelection(virtual,
						"Constant data filter");
				GridPane GridPane = select.selectFilter();
				border.setCenter(GridPane);
				stage.sizeToScene();
			}
		});
		options[2].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				DataSelection select = new DataSelection(virtual,
						"Peak data filter");
				GridPane GridPane = select.selectFilter();
				border.setCenter(GridPane);
				stage.sizeToScene();
			}
		});
		options[3].setOnAction(new EventHandler() {
			@Override
			public void handle(Event arg0) {
				DataSelection select = new DataSelection(virtual,
						"Peak data filter");
				GridPane GridPane = select.integrity();
				border.setCenter(GridPane);
				stage.sizeToScene();
			}
		});

		return vbox;
	}

}
