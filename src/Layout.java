import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Layout extends Application implements EventHandler<ActionEvent> {

    Button button;
    static Stage window;



    @Override
    public void start(Stage primaryStage) {
        window = primaryStage;
    	
        window.setTitle("Project LDH");
        button = new Button();
        button.setText("Load Data");

        //This class will handle the button events
        button.setOnAction(e ->
        	action.Load()
        		);

        StackPane layout = new StackPane();
        layout.getChildren().add(button);
        Scene scene = new Scene(layout, 300, 250);
        window.setScene(scene);
        window.show();
        
        window.setOnCloseRequest(e -> closeProgram());
        
    }

    //When button is clicked, handle() gets called
    //Button click is an ActionEvent (also MouseEvents, TouchEvents, etc...)
    @Override
    public void handle(ActionEvent event) {
        if (event.getSource() == button) 
            System.out.println("Hey Charlie!");
    }
    
    public static void closeProgram() {
    	try {
			main.conn.close();
			window.close();
			System.out.println("Programma afgesloten, en connectie uit");
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }

    
   
}



