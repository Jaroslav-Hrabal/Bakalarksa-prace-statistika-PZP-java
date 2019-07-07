import java.awt.Desktop;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import measurePoints.Group;
import measurePoints.iMeasureGroup;

public final class MainWindow extends Application {
	private Desktop desktop = Desktop.getDesktop();
	public iMeasureGroup virtual;

	public void start(final Stage stage) {
		virtual = new Group("virtual");
		stage.setTitle("Framework Example");
		BorderPane border = new BorderPane();
		Menu menu = new Menu(border, stage, virtual);
		border.setTop(menu.addHBox());
		stage.setScene(new Scene(border));
		stage.show();

	}

	public static void main(String[] args) {
		Application.launch(args);
	}

}
