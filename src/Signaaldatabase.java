
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Daniel Cullimore en Michiel Maas Deze Class heeft alle queries die
 *         worden gebruikt om over te schrijven naar de Signaal Database
 */
public class Signaaldatabase {
	Statement stObj;
	Connection conn;

	public String updateFK() {
		String update = "Update A " + "SET A.Impact = I.Nr, A.Status = S.Nr "
				+ "FROM Afwijking A JOIN Impact I ON A.Ba_account = I.Ba_account "
				+ "JOIN Status S ON S.Ba_account = A.Ba_account ";
		return update;
	}

	// "AD Account, onbekend in Profit"

	public String insertData() {
		String query1 = "INSERT INTO[SignaalDatabase].[dbo].Afwijking(Ba_account, tekstmelding) "
				+ "SELECT username_pre2000, 'AD Account onbekend in Profit' "
				+ "FROM [AuditBlackBox].[dbo].[AD-Export] A " + "WHERE A.username_pre2000 " + "not in "
				+ "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export]) ";

		String query2 = "INSERT INTO[SignaalDatabase].[dbo].Impact(Prioriteit, tekstmelding, Ba_account) "
				+ "SELECT 0, 'Variabele signaaltekst' ,username_pre2000 " + "FROM [AuditBlackBox].[dbo].[AD-Export] A "
				+ "WHERE A.username_pre2000 " + "not in "
				+ "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export]) ";

		String query3 = "INSERT INTO[SignaalDatabase].[dbo].Status(Datum_opgelost, Ba_account) "
				+ "SELECT NULL, A.Username_Pre2000 " + "FROM [AuditBlackBox].[dbo].[AD-Export] A "
				+ "WHERE A.username_pre2000 " + "not in "
				+ "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export]) ";
		return query1 + query2 + query3;
	}

	// "Medewerker uit dienst in Profit, account is in AD actief"
	public String insertData1() {
		String query1 = "INSERT INTO Afwijking (Ba_account, tekstmelding) "
				+ "SELECT PR.EmployeeUsername, 'Medewerker uit dienst in Profit, account is in AD actief' "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR "
				+ "WHERE PR.ContractEndDate < GETDATE() AND PR.EmployeeUsername " + "IN (SELECT A.Username_Pre2000 "
				+ "FROM [AuditBlackBox].[dbo].[AD-Export] A) ";

		String query2 = "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_Account) "
				+ "SELECT 0, '?', PR.EmployeeUsername " + "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR "
				+ "WHERE PR.ContractEndDate < GETDATE() AND PR.EmployeeUsername " + "IN (SELECT A.Username_Pre2000 "
				+ "FROM [AuditBlackBox].[dbo].[AD-Export] A) ";

		String query3 = "INSERT INTO Status(Datum_opgelost, Ba_account) " + "SELECT NULL, PR.EmployeeUsername "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR "
				+ "WHERE PR.ContractEndDate < GETDATE() AND PR.EmployeeUsername " + "IN (SELECT A.Username_Pre2000 "
				+ "FROM [AuditBlackBox].[dbo].[AD-Export] A) ";
		return query1 + query2 + query3;
	}

	// "RDS User naam in Profit bestaat niet in de AD"
	public String insertData2() {
		String query1 = "INSERT INTO Afwijking (Ba_account, tekstmelding) "
				+ "SELECT PR.EmployeeUsername, 'RDS User naam in Profit bestaat niet in de AD' "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR " + "WHERE EmployeeUsername not in "
				+ "(select Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export]) ";

		String query2 = "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account) "
				+ "SELECT 1, '?', PR.EmployeeUsername " + "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR "
				+ "WHERE EmployeeUsername not in "
				+ "(select Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export]) ";

		String query3 = "INSERT INTO Status( Datum_opgelost, Ba_account) " + "SELECT NULL, PR.EmployeeUsername "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR " + "WHERE EmployeeUsername not in "
				+ "(select Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export]) ";
		return query1 + query2 + query3;
	}

	// �RDS naam in Clevernew is niet ingevuld;�
	public String insertData3() {
		String query1 = "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding) "
				+ "SELECT DISTINCT PC.Code, 'RDS naam in Clevernew is niet ingevuld' "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P JOIN [AuditBlackBox].[dbo].[Persooncodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team] t on t.ID = tl.TeamID "
				+ "JOIN [AuditBlackBox].[dbo].[Organisatieeenheid] o on  o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.code = 'Andere Code'";

		String query2 = "INSERT INTO [SignaalDatabase].[dbo].Impact (Prioriteit, Tekstmelding, Ba_account) "
				+ "SELECT DISTINCT 0, 'Variabele signaaltekst', PC.Code "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P JOIN [AuditBlackBox].[dbo].[Persooncodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team] t on t.ID = tl.TeamID "
				+ "JOIN [AuditBlackBox].[dbo].[Organisatieeenheid] o on  o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.code = 'Andere Code'";

		String query3 = "INSERT INTO Status(Datum_opgelost, Ba_account) " + "SELECT DISTINCT NULL, PC.Code "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P JOIN [AuditBlackBox].[dbo].[Persooncodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team] t on t.ID = tl.TeamID "
				+ "JOIN [AuditBlackBox].[dbo].[Organisatieeenheid] o on  o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.code = 'Andere Code'";
		return query1 + query2 + query3;
	}

	// RDS naam in CleverNew bestaat niet in AD
	public String insertData4() {
		String query1 = "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding)"
				+ "SELECT DISTINCT PC.Code, 'RDS naam in CleverNew bestaat niet in AD'"
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team] t on t.ID = tl.ID "
				+ "JOIN [AuditBlackBox].[dbo].[OrganisatieEenheid]o on o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.code NOT IN (SELECT A.Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export] A)";

		String query2 = "INSERT INTO [SignaalDatabase].[dbo].Impact(Prioriteit, tekstmelding, Ba_account) "
				+ "SELECT 0, 'Variabele signaaltekst', PC.Code "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team] t on t.ID = tl.ID "
				+ "JOIN [AuditBlackBox].[dbo].[OrganisatieEenheid]o on o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.code NOT IN (SELECT A.Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export] A)";

		String query3 = "INSERT INTO Status(Datum_opgelost, Ba_account) " + "SELECT NULL, PC.Code "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team] t on t.ID = tl.ID "
				+ "JOIN [AuditBlackBox].[dbo].[OrganisatieEenheid]o on o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.code NOT IN (SELECT A.Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export] A)";
		return query1 + query2 + query3;
	}

	// �Medewerker uit dienst in CleverNew, account in AD actief�
	public String insertData5() {
		String query1 = "INSERT INTO [SignaalDatabase].[dbo].Afwijking(Ba_account, tekstmelding) "
				+ "SELECT PC.Code, 'Medewerker uit dienst in CleverNew account in AD actief' "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P "
				+ "JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID  "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team ]t on t.ID = tl.ID "
				+ "JOIN [AuditBlackBox].[dbo].[Organisatieeenheid] o on o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.CodesoortenID NOT LIKE 981 AND PC.Code IN(SELECT A.Username_Pre2000 "
				+ "FROM [AuditBlackBox].[dbo].[AD-Export] A) ";

		String query2 = "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account) " + "SELECT 0, '?', PC.Code "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P "
				+ "JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID  "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team ]t on t.ID = tl.ID "
				+ "JOIN [AuditBlackBox].[dbo].[Organisatieeenheid] o on o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.CodesoortenID NOT LIKE 981 AND PC.Code IN(SELECT A.Username_Pre2000 "
				+ "FROM [AuditBlackBox].[dbo].[AD-Export] A) ";

		String query3 = "INSERT INTO Status(Datum_opgelost, Ba_account) " + "SELECT NULL, PC.code "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P "
				+ "JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID  "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team ]t on t.ID = tl.ID "
				+ "JOIN [AuditBlackBox].[dbo].[Organisatieeenheid] o on o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.CodesoortenID NOT LIKE 981 AND PC.Code IN(SELECT A.Username_Pre2000 "
				+ "FROM [AuditBlackBox].[dbo].[AD-Export] A) ";
		return query1 + query2 + query3;
	}

	// AD Account, onbekend in Clever
	public String insertData6() {
		String query1 = "INSERT INTO [SignaalDatabase].[dbo].Afwijking(Ba_account, tekstmelding) "
				+ "SELECT A.Username_Pre2000, 'AD Account onbekend in Clever' "
				+ "FROM [AuditBlackBox].[dbo].[AD-Export] A " + "WHERE A.Username_Pre2000 " + "NOT IN"
				+ "(SELECT PC.Code " + "FROM [AuditBlackBox].[dbo].[Persoon] P "
				+ "JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID) ";

		String query2 = "INSERT INTO [SignaalDatabase].[dbo].Impact(Prioriteit, tekstmelding, Ba_account)"
				+ "SELECT 0, 'Variabele signaaltekst', Username_Pre2000 " + "FROM [AuditBlackBox].[dbo].[AD-Export] A "
				+ "WHERE A.Username_Pre2000 " + "NOT IN " + "(SELECT PC.Code "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P "
				+ "JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID) ";

		String query3 = "INSERT INTO Status( Datum_opgelost, Ba_account) " + "SELECT NULL, A.Username_Pre2000 "
				+ "FROM [AuditBlackBox].[dbo].[AD-Export] A " + "WHERE A.Username_Pre2000 " + "NOT IN "
				+ "(SELECT PC.Code " + "FROM [AuditBlackBox].[dbo].[Persoon] P "
				+ "JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker] M ON P.id = M.PersoonID) ";
		return query1 + query2 + query3;
	}

	// invalid object name
	// RDS User naam in Profit bestaat niet in Clever
	public String insertData7() {
		String query1 = "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding) "
				+ "SELECT PR.EmployeeUsername, 'RDS User naam in Profit bestaat niet in Clever' "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON PC.Code = PR.EmployeeUsername "
				+ "JOIN [AuditBlackBox].[dbo].[Persoon] P ON PC.PersoonID = P.ID JOIN [AuditBlackBox].[dbo].[Medewerker] M ON M.PersoonID = P.ID "
				+ "WHERE PR.EmployeeUsername NOT IN (SELECT PC.code " + "FROM [AuditBlackBox].[dbo].Persooncodes) ";

		String query2 = "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account) "
				+ "SELECT 0, '?', PR.EmployeeUsername "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON PC.Code = PR.EmployeeUsername "
				+ "JOIN [AuditBlackBox].[dbo].[Persoon] P ON PC.PersoonID = P.ID JOIN [AuditBlackBox].[dbo].[Medewerker] M ON M.PersoonID = P.ID "
				+ "WHERE PR.EmployeeUsername NOT IN (SELECT PC.code " + "FROM [AuditBlackBox].[dbo].Persooncodes) ";

		String query3 = "INSERT INTO Status(Datum_opgelost, Ba_account) " + "SELECT NULL, PR.EmployeeUsername "
				+ "FROM [AuditBlackBox].dbo.[AfasProfit-Export] PR "
				+ "JOIN [AuditBlackBox].dbo.PersoonCodes PC ON PC.Code = PR.EmployeeUsername "
				+ "JOIN [AuditBlackBox].dbo.Persoon P ON PC.PersoonID = P.ID JOIN [AuditBlackBox].dbo.Medewerker M ON M.PersoonID = P.ID "
				+ "WHERE PR.EmployeeUsername NOT IN (SELECT PC.code \" + \"FROM [AuditBlackBox].[dbo].Persooncodes)";
		return query1 + query2 + query3;
	}

	// invalid PersoonCodes
	// Medewerker uit dienst in Profit, account is in Clever actief
	public String insertData8() {
		String query1 = "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding) "
				+ "SELECT DISTINCT PR.EmployeeUsername, 'Medewerker uit dienst in Profit, account is in Clever actief' "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON PC.Code = PR.EmployeeUsername "
				+ "JOIN [AuditBlackBox].[dbo].[Persoon] P ON PC.PersoonID = P.ID JOIN [AuditBlackBox].[dbo].[Medewerker] M ON M.PersoonID = P.ID "
				+ "WHERE Pr.ContractEndDate < GETDATE() AND PC.CodesoortenID = 981 "
				+ "AND PR.EmployeeUsername IN (SELECT PC.code " + "FROM [AuditBlackBox].[dbo].Persooncodes) ";

		String query2 = "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account)"
				+ "SELECT 0, 'Variabele signaaltekst', PR.EmployeeUsername "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON PC.Code = PR.EmployeeUsername "
				+ "JOIN [AuditBlackBox].[dbo].[Persoon]P ON PC.PersoonID = P.ID JOIN [AuditBlackBox].[dbo].[Medewerker] M ON M.PersoonID = P.ID "
				+ "WHERE Pr.ContractEndDate < GETDATE() AND PC.CodesoortenID = 981 "
				+ "AND PR.EmployeeUsername IN (SELECT PC.code " + "FROM [AuditBlackBox].[dbo].Persooncodes)";

		String query3 = "INSERT INTO Status(Datum_opgelost, Ba_account) " + "SELECT NULL, PR.EmployeeUsername "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON PC.Code = PR.EmployeeUsername "
				+ "JOIN [AuditBlackBox].[dbo].[Persoon]P ON PC.PersoonID = P.ID JOIN [AuditBlackBox].[dbo].[Medewerker] M ON M.PersoonID = P.ID "
				+ "WHERE Pr.ContractEndDate < GETDATE() AND PC.CodesoortenID = 981 "
				+ "AND PR.EmployeeUsername IN (SELECT PC.code " + "FROM [AuditBlackBox].[dbo].Persooncodes) ";
		return query1 + query2 + query3;

	}

	// RDS User naam in Clever bestaat niet in Afas Profit
	public String insertData9() {
		String query1 = "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding)"
				+ "SELECT DISTINCT PC.Code, 'RDS User naam in Clever bestaat niet in Afas Profit' "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P "
				+ "JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker]M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team] t on t.ID = tl.ID "
				+ "JOIN [AuditBlackBox].[dbo].[Organisatieeenheid] o on o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.Code NOT IN (SELECT PR.EmployeeUsername "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR)";

		String query2 = "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account) " + "SELECT 0, '?', PC.Code "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P "
				+ "JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker]M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team] t on t.ID = tl.ID "
				+ "JOIN [AuditBlackBox].[dbo].[Organisatieeenheid] o on o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.Code NOT IN (SELECT PR.EmployeeUsername "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR) ";

		String query3 = "INSERT INTO Status(Datum_opgelost, Ba_account)" + "SELECT NULL, PC.Code "
				+ "FROM [AuditBlackBox].[dbo].[Persoon] P "
				+ "JOIN [AuditBlackBox].[dbo].[PersoonCodes] PC ON P.id = PC.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Medewerker]M ON P.id = M.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Teamlid] tl on tl.PersoonID = pc.PersoonID "
				+ "JOIN [AuditBlackBox].[dbo].[Team] t on t.ID = tl.ID "
				+ "JOIN [AuditBlackBox].[dbo].[Organisatieeenheid] o on o.OrganisatieID = t.OrganisatieEenheidID "
				+ "WHERE PC.Code NOT IN (SELECT PR.EmployeeUsername "
				+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR) ";
		return query1 + query2 + query3;
	}

	// Leeg maken van de Database
	public String emptyDB() {
		String delete2 = "DELETE FROM afwijking ";
		String delete3 = "DELETE FROM Impact ";
		String delete1 = "DELETE FROM Status ";
		return delete2 + delete3 + delete1;
	}

	// Omdat het overschrijven zo lang duurt wordt er een laadscherm getoond met een
	// THread
	//
	public static void start(Stage primaryStage) {

		try {

			InputStream is = new FileInputStream("src/SignaalConnection.txt");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String url = buf.readLine();
			String username = buf.readLine();
			String password = buf.readLine();

			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("Verbinding gemaakt " + conn.getCatalog());
			Task<?> writer1 = createWriter(conn);
			Thread ThreadWriter = new Thread(writer1);

			Alert dialog = new Alert(Alert.AlertType.INFORMATION);
			dialog.setHeaderText(null);
			dialog.setGraphic(null);
			dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
			dialog.initOwner(primaryStage);

			dialog.setTitle("Laden");

			ProgressIndicator p = new ProgressIndicator(-1);

			dialog.initStyle(StageStyle.UTILITY);

			StackPane sp = new StackPane();
			sp.getChildren().add(p);
			dialog.getDialogPane().setContent(sp);

			StackPane done = new StackPane();
			Label doneL = new Label("Alles is overgeschreven naar \n de Signalen Database");
			done.getChildren().add(doneL);

			writer1.addEventFilter(WorkerStateEvent.WORKER_STATE_RUNNING, new EventHandler<WorkerStateEvent>() {

				@Override
				public void handle(WorkerStateEvent t) {

					dialog.showAndWait();
				}
			});

			writer1.addEventFilter(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {

				@Override
				public void handle(WorkerStateEvent t) {
					dialog.getDialogPane().setContent(done);
					dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				}
			});

			ThreadWriter.start();

		}

		catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Deze methode is de Thread die wordt gebruikt bij het overschrijven
	public static Task<?> createWriter(Connection conn) {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {

				try {
					Signaaldatabase dat = new Signaaldatabase();
					System.out.println(dat.insertData());
					PreparedStatement stmt = conn.prepareStatement(dat.updateFK());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.emptyDB());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.insertData());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.insertData1());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.insertData2());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.insertData3());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.insertData4());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.insertData5());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.insertData6());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.insertData7());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.insertData8());
					stmt.executeUpdate();
					stmt = conn.prepareStatement(dat.insertData9());
					stmt.executeUpdate();

					return true;
				}

				catch (SQLException e) {
					e.printStackTrace();

				}
				return null;

			}
		};
	}

}
