import java.awt.Event;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import javafx.scene.paint.Color;
/*
 * Author: Michiel Maas
 * Deze class set de layout van het laat scherm. Eerste maakt het alle Elements aan, en daarna worden ze ingezet
 */

public class Layout extends Application implements EventHandler<ActionEvent> {
	// Checker
	static Scene checker;
	static BorderPane layout;
	static HBox top;
	static VBox right;
	static GridPane center;
	static Stage window;

	// TOP Bar
	static Image logo;
	static ImageView legerIcon;
	static Label welkom;
	static Label team;
	static Button closeButton;
	static String name = System.getProperty("user.name");
	static String eenheid;

	// Center met Rules
	static Button load;
	static Label bRules;
	static Label bRule1;
	static Label bRule2;
	static Label bRule3;
	static Label bRule4;
	static Label bRule5;
	static Label bRule6;
	static Label bRule7;
	static Label bRule8;
	static Label bRule9;
	static Label bRule10;
	static TextField bRuleText1;
	static TextField bRuleText2;
	static TextField bRuleText3;
	static TextField bRuleText4;
	static TextField bRuleText5;
	static TextField bRuleText6;
	static TextField bRuleText7;
	static TextField bRuleText8;
	static TextField bRuleText9;
	static TextField bRuleText10;
	static Optional<String> s;
	static Label rapport;
	static TextArea rapportText;

	// Impact
	static TableView<Gebruiker> impactTable;
	static Button impact;

	// Right
	static Button toCvs;
	static Button changeString;
	static Button sDatabase;
	static Dialog<Pair<Pair<String, String>, String>> dialog;

	@SuppressWarnings({ "unchecked", "resource" })
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Leger Des Heils: Database Checker");

		// Checker
		layout = new BorderPane();
		checker = new Scene(layout);

		top = new HBox();
		center = new GridPane();
		right = new VBox();

		// TOP
		logo = new Image("/legerIcon.png");
		legerIcon = new ImageView(logo);
		legerIcon.setFitWidth(90);
		legerIcon.setFitHeight(90);

		welkom = new Label("Welkom " + name);
		welkom.setFont(new Font(25));
		welkom.setTextFill(Color.WHITE);

                if (main.q.werkeenheidProfit.equals("")){
                    eenheid = "Admin";
                }else{
                    eenheid = main.q.werkeenheidProfit;
                }
                
                
		team = new Label("Eenheid: " + eenheid);
		team.setFont(new Font(25));
		team.setTextFill(Color.WHITE);

		closeButton = new Button("Afsluiten");
		closeButton.setOnAction(e -> action.closeProgram());
		closeButton.setMinWidth(100);
		closeButton.setMinHeight(50);
		closeButton.setFont(new Font(25));

		Region region1 = new Region();
		HBox.setHgrow(region1, Priority.ALWAYS);

		Region region2 = new Region();
		HBox.setHgrow(region2, Priority.ALWAYS);

		top.setMinHeight(100);
		top.setPadding(new Insets(10, 10, 10, 10));
		top.setAlignment(Pos.CENTER);
		top.getChildren().addAll(legerIcon, welkom, region1, team, region2, closeButton);
		top.setBackground(Background.EMPTY);
		String style = "-fx-background-color: #EF402F;";
		top.setSpacing(10);
		top.setStyle(style);

		// CENTER - BusinessRules
		load = new Button("Inladen Business Rules");
		load.setMinSize(600, 100);
		load.setFont(new Font(30));
		load.setOnAction(e -> action.writeRules(rapportText, window, primaryStage));

		bRules = new Label("Business Rules");
		rapport = new Label("Rapport");

		bRule1 = new Label("   AD Account, onbekend in Profit:");
		bRule2 = new Label("   Medewerker uit dienst in Profit, account is in AD actief: ");
		bRule3 = new Label("   RDS User naam in Profit bestaat niet in de AD: ");
		bRule4 = new Label("   RDS naam in Clevernew is niet ingevuld: ");
		bRule5 = new Label("   RDS naam in CleverNew bestaat niet in AD: ");
		bRule6 = new Label("   Medewerker uit dienst in CleverNew, account in AD actief: ");
		bRule7 = new Label("   AD Account, onbekend in Clever: ");
		bRule8 = new Label("   RDS User naam in Profit bestaat niet in Clever: ");
		bRule9 = new Label("   Medewerker uit dienst in Profit, account is in Clever actief: ");
		bRule10 = new Label("   RDS User naam in Clever bestaat niet in Afas Profit: ");

		bRuleText1 = new TextField();
		bRuleText2 = new TextField();
		bRuleText3 = new TextField();
		bRuleText4 = new TextField();
		bRuleText5 = new TextField();
		bRuleText6 = new TextField();
		bRuleText7 = new TextField();
		bRuleText8 = new TextField();
		bRuleText9 = new TextField();
		bRuleText10 = new TextField();

		bRuleText1.setEditable(false);
		bRuleText2.setEditable(false);
		bRuleText3.setEditable(false);
		bRuleText4.setEditable(false);
		bRuleText5.setEditable(false);
		bRuleText6.setEditable(false);
		bRuleText7.setEditable(false);
		bRuleText8.setEditable(false);
		bRuleText9.setEditable(false);
		bRuleText10.setEditable(false);

		bRules.setFont(new Font(15));
		bRule1.setFont(new Font(15));
		bRule2.setFont(new Font(15));
		bRule3.setFont(new Font(15));
		bRule4.setFont(new Font(15));
		bRule5.setFont(new Font(15));
		bRule6.setFont(new Font(15));
		bRule7.setFont(new Font(15));
		bRule8.setFont(new Font(15));
		bRule9.setFont(new Font(15));
		bRule10.setFont(new Font(15));
		rapport.setFont(new Font(15));

		rapportText = new TextArea();

		GridPane.setConstraints(load, 0, 0, 5, 1);
		GridPane.setConstraints(bRules, 0, 1, 2, 1);
		GridPane.setConstraints(bRule1, 1, 2);
		GridPane.setConstraints(bRuleText1, 2, 2);
		GridPane.setConstraints(bRule2, 1, 3);
		GridPane.setConstraints(bRuleText2, 2, 3);
		GridPane.setConstraints(bRule3, 1, 4);
		GridPane.setConstraints(bRuleText3, 2, 4);
		GridPane.setConstraints(bRule4, 1, 5);
		GridPane.setConstraints(bRuleText4, 2, 5);
		GridPane.setConstraints(bRule5, 1, 6);
		GridPane.setConstraints(bRuleText5, 2, 6);
		GridPane.setConstraints(bRule6, 1, 7);
		GridPane.setConstraints(bRuleText6, 2, 7);
		GridPane.setConstraints(bRule7, 1, 8);
		GridPane.setConstraints(bRuleText7, 2, 8);
		GridPane.setConstraints(bRule8, 1, 9);
		GridPane.setConstraints(bRuleText8, 2, 9);
		GridPane.setConstraints(bRule9, 1, 10);
		GridPane.setConstraints(bRuleText9, 2, 10);
		GridPane.setConstraints(bRule10, 1, 11);
		GridPane.setConstraints(bRuleText10, 2, 11);
		GridPane.setConstraints(rapport, 0, 12, 2, 1);
		GridPane.setConstraints(rapportText, 0, 13, 3, 1);

		rapport.setPadding(new Insets(10, 0, 0, 0));
		rapportText.setMinHeight(400);
		rapportText.setEditable(false);
		center.setPadding(new Insets(25, 25, 25, 25));
		center.setHgap(20);
		center.setVgap(10);

		center.getChildren().addAll(load, bRules, bRule1, bRuleText1, bRule2, bRuleText2, bRule3, bRuleText3, bRule4,
				bRuleText4, bRule5, bRuleText5, bRule6, bRuleText6, bRule7, bRuleText7, bRule8, bRuleText8, bRule9,
				bRuleText9, bRule10, bRuleText10, rapport, rapportText);

		// Center Impact
		impact = new Button("Impact laden");
		impact.setFont(new Font(30));
		impact.setOnAction(e -> action.setImpactTable(primaryStage));
		impact.setMinSize(600, 100);
		impact.setDisable(true);
		GridPane.setConstraints(impact, 7, 0, 5, 1);

		TableColumn<Gebruiker, String> usernameCol = new TableColumn<>("Gebruikersnaam");
		usernameCol.setMinWidth(200);
		usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));

		TableColumn<Gebruiker, String> idCol = new TableColumn<>("ID");
		idCol.setMinWidth(200);
		idCol.setCellValueFactory(new PropertyValueFactory<>("userID"));

		TableColumn<Gebruiker, String> numberCol = new TableColumn<>("Aantal overtredingen");
		numberCol.setMinWidth(200);
		numberCol.setCellValueFactory(new PropertyValueFactory<>("aantalOvertredingen"));

		impactTable = new TableView<>();
		impactTable.getColumns().addAll(usernameCol, idCol, numberCol);

		GridPane.setConstraints(impactTable, 7, 2, 5, 12);

		center.getChildren().addAll(impact, impactTable);

		// Right VBox

		right.setPadding(new Insets(10));
		right.setSpacing(20);

		toCvs = new Button("Schrijf de Business Rules naar bestand");
		toCvs.setFont(new Font(25));
		toCvs.setWrapText(true);
		toCvs.setMinSize(550, 300);
		toCvs.setOnAction(e -> action.write(primaryStage));
		toCvs.setDisable(true);

		changeString = new Button("Aanpassen van de Connection String");
		changeString.setFont(new Font(25));
		changeString.setWrapText(true);
		changeString.setMinSize(550, 300);

		sDatabase = new Button("Schrijf weg naar Signalen Database");
		sDatabase.setFont(new Font(25));
		sDatabase.setWrapText(true);
		sDatabase.setMinSize(550, 300);
		sDatabase.setOnAction(e -> action.writeSDatabase(primaryStage));
		sDatabase.setDisable(true);

		changeString.setOnAction(e -> {
			try {
				action.changeString(dialog);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		// Change String Dialogue
		dialog = new Dialog<>();
		dialog.setTitle("Aanpassen van de Connection String");
		ButtonType urlSet = new ButtonType("Pas Aan", ButtonData.OK_DONE);
		ButtonType stop = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(urlSet, stop);
		dialog.initStyle(StageStyle.UTILITY);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		String promptURL = "";
		String promptUsername = "";
		String promptPassword = "";

		try {
			InputStream is = new FileInputStream("src/connection.txt");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			promptURL = buf.readLine();
			promptUsername = buf.readLine();
			promptPassword = buf.readLine();
		} catch (IOException e) {

		}
		
		

		TextField URL = new TextField();
		URL.setText(promptURL);
		URL.setMinWidth(400);
		grid.add(new Label("URL:"), 0, 0);
		grid.add(URL, 1, 0);
		
		TextField Username = new TextField();
		Username.setText(promptUsername);
		Username.setMinWidth(400);
		grid.add(new Label("Username:"), 0, 1);
		grid.add(Username, 1, 1);
		
		PasswordField Password = new PasswordField();
		Password.setText(promptPassword);
		Password.setMinWidth(400);	
		grid.add(new Label("Password:"), 0, 2);
		grid.add(Password, 1, 2);


		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == urlSet) {
				Pair<String, String> first = new Pair<>(URL.getText(), Username.getText());
				Pair<Pair<String, String>, String> second = new Pair<>(first, Password.getText());
				return second;
			}
			return null;
		});

		right.getChildren().addAll(toCvs, changeString, sDatabase);

		layout.setRight(right);
		layout.setTop(top);
		layout.setCenter(center);

		window.setScene(checker);
		window.show();
		dialog.initOwner(primaryStage);

		window.setFullScreen(true);
		window.setOnCloseRequest(e -> action.closeProgram());
		primaryStage.setResizable(false);

	}


	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
