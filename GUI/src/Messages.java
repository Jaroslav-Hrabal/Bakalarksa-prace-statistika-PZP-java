import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBoxBuilder;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Messages {
	public void filterCheck(final Stage stage) throws Exception {

	}

	public void filterForm(final Stage stage) throws Exception {
		stage.initModality(Modality.WINDOW_MODAL);
		stage.setScene(new Scene(
				VBoxBuilder
						.create()
						.children(
								new Text(
										"Filter and Quantity selections must be properly filled"),
								new Button("Ok.")).alignment(Pos.CENTER)
						.padding(new Insets(5)).build()));
		stage.show();
	}
}
