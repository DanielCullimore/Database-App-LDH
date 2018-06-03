import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import DataEntries.*;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Layout extends Application implements EventHandler<ActionEvent> {

	// Login
	Scene login;
	Image logo;
	ImageView legerIcon;
	Label loginText;
	TextField loginName;
	Button loginButton;

	// Checker
	Scene checker;
	BorderPane outer;
	BorderPane layout;

	Button button;
	static Stage window;

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

	GridPane loginScreen;

	Label bRule1;
	Label bRule2;
	static Label bRule3;
	static Label bRule4;
	Label bRule5;
	Label bRule6;
	Label bRule7;
	Label bRule8;
	Label bRule9;
	Label rapport;
	TextArea rapportText;

	static Rectangle r;

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		window.setTitle("Leger Des Heils: Database Checker");

		// Login Setup
		loginScreen = new GridPane();
		loginScreen.setVgap(10);
		loginScreen.setHgap(10);

		logo = new Image("/legerIcon.png");
		legerIcon = new ImageView(logo);

		GridPane.setConstraints(legerIcon, 0, 0, 4, 1);

		loginText = new Label("Gebruikersnaam:");
		loginText.setMinWidth((logo.getWidth() / 2) - 5);

		loginText.setFont(new Font(25));
		GridPane.setConstraints(loginText, 0, 1);

		loginName = new TextField();
		loginName.setMinWidth((logo.getWidth() / 2) - 5);
		GridPane.setConstraints(loginName, 1, 1);

		loginButton = new Button("Login");
		loginButton.setMinWidth(logo.getWidth() + 10);
		loginButton.setOnAction(e -> window.setScene(checker));
		GridPane.setConstraints(loginButton, 0, 2, 2, 1);

		loginScreen.getChildren().addAll(legerIcon, loginText, loginName, loginButton);

		loginScreen.setAlignment(Pos.CENTER);
		login = new Scene(loginScreen);

		// Set log in
		window.setScene(login);
		window.show();

		window.setOnCloseRequest(e -> closeProgram());

		primaryStage.setResizable(false);

		window.setMaximized(true);
		window.setFullScreen(true);

		// More info dingen en adere shit
		// Flowpane op top met alle knoppen
		FlowPane top = new FlowPane();
		loadAD = new Button("Ophalen AD (Indirecte Connectie)");
		loadAD.setMinWidth(200);

		loadProfit = new Button("Ophalen Profit");
		loadProfit.setMinWidth(200);
		loadProfit.setOnAction(e -> setTableProfit());

		loadClever = new Button("Ophalen CleverNew");
		loadClever.setMinWidth(200);

		createReport = new Button("Creeër Rapport");
		createReport.setMinWidth(200);

		reset = new Button("Rest");
		reset.setMinWidth(200);

		loadedAD = new TextField();
		loadedAD.setMinWidth(200);
		loadedAD.setEditable(false);

		loadedProfit = new TextField();
		loadedProfit.setMinWidth(200);
		loadedProfit.setEditable(false);

		loadedClever = new TextField();
		loadedClever.setMinWidth(200);
		loadedClever.setEditable(false);

		email = new Button("E-mail");
		email.setMinWidth(200);

		runAll = new Button("Alles Runnen");
		runAll.setMinWidth(200);
		runAll.setOnAction(e -> action.writeRules(rapportText));

		top.getChildren().addAll(loadAD, loadProfit, loadClever, createReport, reset, loadedAD, loadedProfit,
				loadedClever, email, runAll);

		top.setPadding(new Insets(10, 10, 10, 10));
		top.setVgap(5);
		top.setHgap(5);

		button = new Button();
		button.setText("Load Data");

		outer = new BorderPane();

		button.setOnAction(e -> action.LoadProfit());

		layout = new BorderPane();
		layout.getChildren().add(button);
		checker = new Scene(outer);

		layout.setTop(top);

		// Mid voor alle Tabellen
		VBox mid = new VBox();

		// Profit Table
		profitLabel = new Label("Profit");

		nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("EmployeeUsername"));

		StartContract = new TableColumn<>("Start Contract");
		StartContract.setMinWidth(200);
		StartContract.setCellValueFactory(new PropertyValueFactory<>("StartContractDate"));

		EndContract = new TableColumn<>("End Contract");
		EndContract.setMinWidth(200);
		EndContract.setCellValueFactory(new PropertyValueFactory<>("EndContractDate"));

		profitTable = new TableView<>();

		profitTable.getColumns().addAll(nameColumn, StartContract, EndContract);

		// AD Table
		ADLabel = new Label("AD");

		nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		adTable = new TableView<>();

		// Clever Table
		CleverLabel = new Label("Clever");

		nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(200);
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

		cleverTable = new TableView<>();

		// Full mid field
		mid.getChildren().addAll(ADLabel, adTable, profitLabel, profitTable, CleverLabel, cleverTable);

		mid.setPadding(new Insets(10, 10, 10, 10));

		layout.setCenter(mid);

		VBox Rules = new VBox();
		Rules.setMinWidth(500);

		Label bRules = new Label("Business Rules");

		bRule1 = new Label("   PROFIT <> PROFIT Medewerkernr en Dienstverbanden matchen niet: ");
		bRule2 = new Label("   PROFIT <> PROFIT Citrix naam niet ingevuld bij Extra Info Veld: ");
		bRule3 = new Label("   PROFIT <> AD Citrix naam in PROFIT bestaat niet in AD: ");
		bRule4 = new Label("   PROFIT <> AD Medewerker uit dienst in CleverNew, account in AD ");
		bRule5 = new Label("   CleverNew <> AD Citrix naam in CleverNew niet ingevuld");
		bRule6 = new Label("   CleverNew <> AD Citrix naam in CleverNew bestaat niet in AD");
		bRule7 = new Label("   CleverNew <> AD Medewerker uit dienst in CleverNew, account in AD");
		bRule8 = new Label("   AD <> PROFIT AD Account, onbekend in Profit");
		bRule9 = new Label("   AD <> CleverNew AD Account, onbekend in CleverNew");

		rapport = new Label("Rapport");

		rapport.setPadding(new Insets(10, 0, 0, 0));

		rapportText = new TextArea();

		rapportText.setMaxWidth(480);
		rapportText.setMinHeight(350);

		rapportText.setEditable(false);

		Rules.getChildren().addAll(bRules, bRule1, bRule2, bRule3, bRule4, bRule5, bRule6, bRule7, bRule8, bRule9,
				rapport, rapportText);

		Rules.setPadding(new Insets(0, 10, 0, 10));

		outer.setCenter(layout);
		outer.setRight(Rules);

	}

	private void setTableProfit() {

		ArrayList<ProfitEntry> profit = action.fillProfitTable();

		for (ProfitEntry p : profit) {
			profitTable.getItems().add(p);
			System.out.println("Dit zit er in P = " + p.getEmployeeUsername());
		}

		loadedProfit.setText(String.valueOf(profit.size()));

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
