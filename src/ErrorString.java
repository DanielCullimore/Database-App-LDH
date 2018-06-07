import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ErrorString extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @SuppressWarnings("resource")
	@Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Foute String");
		Image logo = new Image("/legerIcon.png");
		ImageView legerIcon = new ImageView(logo);
		legerIcon.setFitWidth(300);
		legerIcon.setFitHeight(300);
        
       
        VBox layout = new VBox();
        layout.setAlignment(Pos.CENTER);
        layout.setSpacing(10);
        Scene scene = new Scene(layout, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Label fout = new Label("De connection string kan geen verbinding maken met de server. \n Vul de string opnieuw in en start het programma opnieuw.");
    	fout.setFont(new Font(20));
        
        
        Button changeString = new Button("Aanpassen van de Connection String");
		changeString.setFont(new Font(25));
		changeString.setWrapText(true);
		changeString.setMinSize(300, 100);

		// Change String Dialogue
		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Aanpassen van de Connection String");
		ButtonType urlSet = new ButtonType("Pas Aan", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(urlSet, ButtonType.CANCEL);
		dialog.initStyle(StageStyle.UTILITY);

		
		
		
		changeString.setOnAction(e -> {
			try {
				action.changeString(dialog);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});



		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		String prompt = "";

		try {
			InputStream is = new FileInputStream("src/connection.txt");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			prompt = buf.readLine();
		} catch (IOException e) {

		}

		TextField URL = new TextField();
		URL.setText(prompt);
		URL.setMinWidth(400);

		grid.add(new Label("URL:"), 0, 0);
		grid.add(URL, 1, 0);

		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == urlSet) {
				return new String(URL.getText());
			}
			return null;
		});
		
		
        layout.getChildren().addAll(legerIcon, fout, changeString);
	
    }

}