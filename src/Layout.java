import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;

import DataEntries.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;
import javafx.scene.paint.Color;

public class Layout extends Application implements EventHandler<ActionEvent> {

	// Login
	Scene login;
	Image logo;
	ImageView legerIcon;
	Label loginText;
	TextField loginName;
	Button closeButton;
	GridPane loginScreen;

	// Checker
	Scene checker;
	BorderPane layout;
	HBox top;
	GridPane right;
	GridPane center;

	Button button;
	static Stage window;

	// TOP Bar
	Label welkom;
	Label team;
	Button logOut;

	Button loadAD;
	Button loadProfit;
	Button loadClever;
	Button createReport;
	Button reset;
	Button email;
	Button runAll;
	TextField loadedAD;
	TextField loadedProfit;
	TextField loadedClever;
	Label profitLabel;
	Label ADLabel;
	Label CleverLabel;

	TableView<ProfitEntry> profitTable;
	TableView<mock> adTable;
	TableView<mock> cleverTable;
	TableColumn<ProfitEntry, String> nameColumn;
	TableColumn<ProfitEntry, Date> StartContract;
	TableColumn<ProfitEntry, Date> EndContract;

	// Right Rules bar
	Label bRules;
	Label bRule1;
	Label bRule2;
	Label bRule3;
	Label bRule4;
	Label bRule5;
	Label bRule6;
	Label bRule7;
	Label bRule8;
	Label bRule9;
	Label bRule10;
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

	Label rapport;
	TextArea rapportText;

	// center
	static Button load;
	static ProgressBar ProgBar;

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Leger Des Heils: Database Checker");

		// Checker
		layout = new BorderPane();
		checker = new Scene(layout);

		top = new HBox();
		center = new GridPane();
		right = new GridPane();

		// TOP
		logo = new Image("/legerIcon.png");
		legerIcon = new ImageView(logo);
		legerIcon.setFitWidth(90);
		legerIcon.setFitHeight(90);

		String name = "Michiel";
		welkom = new Label("Welkom " + name);
		welkom.setFont(new Font(25));
		welkom.setTextFill(Color.WHITE);

		String eenheid = "Programmer";
		team = new Label("Eenheid: " + eenheid);
		team.setFont(new Font(25));
		team.setTextFill(Color.WHITE);

		closeButton = new Button("Afsluiten");
		closeButton.setOnAction(e -> closeProgram());
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

		// RIGHT
		// right.setMinWidth(600);

		bRules = new Label("Business Rules");
		rapport = new Label("Rapport");

		bRule1 = new Label("   PROFIT <> PROFIT Medewerkernr en Dienstverbanden matchen niet: ");
		bRule2 = new Label("   PROFIT <> PROFIT Citrix naam niet ingevuld bij Extra Info Veld: ");
		bRule3 = new Label("   PROFIT <> AD Citrix naam in PROFIT bestaat niet in AD: ");
		bRule4 = new Label("   PROFIT <> AD Medewerker uit dienst in CleverNew, account in AD ");
		bRule5 = new Label("   CleverNew <> AD Citrix naam in CleverNew niet ingevuld");
		bRule6 = new Label("   CleverNew <> AD Citrix naam in CleverNew bestaat niet in AD");
		bRule7 = new Label("   CleverNew <> AD Medewerker uit dienst in CleverNew, account in AD");
		bRule8 = new Label("   AD <> PROFIT AD Account, onbekend in Profit");
		bRule9 = new Label("   AD <> CleverNew AD Account, onbekend in CleverNew");
		bRule10 = new Label("   test");

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
		rapport.setFont(new Font(15));

		rapportText = new TextArea();

		GridPane.setConstraints(bRules, 0, 0, 2, 1);
		GridPane.setConstraints(bRule1, 1, 1);
		GridPane.setConstraints(bRuleText1, 2, 1);
		GridPane.setConstraints(bRule2, 1, 2);
		GridPane.setConstraints(bRuleText2, 2, 2);
		GridPane.setConstraints(bRule3, 1, 3);
		GridPane.setConstraints(bRuleText3, 2, 3);
		GridPane.setConstraints(bRule4, 1, 4);
		GridPane.setConstraints(bRuleText4, 2, 4);
		GridPane.setConstraints(bRule5, 1, 5);
		GridPane.setConstraints(bRuleText5, 2, 5);
		GridPane.setConstraints(bRule6, 1, 6);
		GridPane.setConstraints(bRuleText6, 2, 6);
		GridPane.setConstraints(bRule7, 1, 7);
		GridPane.setConstraints(bRuleText7, 2, 7);
		GridPane.setConstraints(bRule8, 1, 8);
		GridPane.setConstraints(bRuleText8, 2, 8);
		GridPane.setConstraints(bRule9, 1, 9);
		GridPane.setConstraints(bRuleText9, 2, 9);
		GridPane.setConstraints(bRule10, 1, 10);
		GridPane.setConstraints(bRuleText10, 2, 10);
		GridPane.setConstraints(rapport, 0, 11, 2, 1);
		GridPane.setConstraints(rapportText, 0, 12, 3, 1);

		rapport.setPadding(new Insets(10, 0, 0, 0));

		rapportText.setMinHeight(600);

		rapportText.setEditable(false);

		// right.setGridLinesVisible(true);
		right.setHgap(20);
		right.setVgap(5);

		right.getChildren().addAll(bRules, bRule1, bRuleText1, bRule2, bRuleText2, bRule3, bRuleText3, bRule4,
				bRuleText4, bRule5, bRuleText5, bRule6, bRuleText6, bRule7, bRuleText7, bRule8, bRuleText8, bRule9,
				bRuleText9, bRule10, bRuleText10, rapport, rapportText);

		right.setPadding(new Insets(0, 10, 0, 10));

		// Center
		load = new Button("Inladen");
		load.setMinWidth(300);
		load.setMinHeight(300);

		center.setPadding(new Insets(25, 25, 25, 25));
		center.setHgap(10);
		center.setVgap(10);

		Button changeString = new Button("String");
		changeString.setMinWidth(300);
		changeString.setMinHeight(300);
		GridPane.setConstraints(changeString, 1, 0);

		Button toCvs = new Button("Write");
		toCvs.setMinSize(300, 300);
		GridPane.setConstraints(toCvs, 0, 1);
		toCvs.setOnAction(e -> action.write(window, primaryStage));
		
		

		Dialog<String> dialog = new Dialog<>();
		dialog.setTitle("Aanpassen van de Connection String");
		ButtonType urlSet = new ButtonType("Pas Aan", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(urlSet, ButtonType.CANCEL);

		dialog.initStyle(StageStyle.UTILITY);

		changeString.setOnAction(e -> {
			try {
				action.changeString(dialog);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
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

		// Optional<String> result = dialog.showAndWait();

		center.getChildren().addAll(load, changeString, toCvs);

		load.setOnAction(e -> action.writeRules(rapportText, window, primaryStage));

		layout.setRight(right);
		layout.setTop(top);
		layout.setCenter(center);

		window.setScene(checker);
		window.show();
		dialog.initOwner(primaryStage);

		window.setFullScreen(true);

		window.setOnCloseRequest(e -> closeProgram());

		primaryStage.setResizable(false);

	}

	public void loggedIn() {
		window.setScene(checker);
		window.setFullScreen(true);
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

	@Override
	public void handle(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}
