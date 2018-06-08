import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.NoSuchElementException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

public class action {

	static Connection conn;

	public static void writeRules(TextArea a, Window window, Stage primaryStage) {

		conn = main.conn;

		a.setText("");

		Task<?> ruler1 = createRuler1(conn, a);
		Thread rule1Thread = new Thread(ruler1);

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
		Label doneL = new Label("Alle Business rules zijn geladen.");
		done.getChildren().add(doneL);

		ruler1.addEventFilter(WorkerStateEvent.WORKER_STATE_RUNNING, new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent t) {

				dialog.showAndWait();
			}
		});

		ruler1.addEventFilter(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent t) {
				dialog.getDialogPane().setContent(done);
				dialog.getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
			}
		});

		rule1Thread.start();
		Layout.impact.setDisable(false);
		// try {
		// rule1Thread.join();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}
	
	public static void changeString(Dialog<String> dialog) throws IOException {

		Optional<String> s = dialog.showAndWait();
		PrintWriter pw = null;

		try {
			pw = new PrintWriter("src/connection.txt");
			BufferedWriter bw = new BufferedWriter(pw);

			try {
				bw.write(s.get());
				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Connection String aangepast");
				alert.setHeaderText(null);
				alert.setContentText("De connection String is aangepast. \n Start het programma opniew op.");
				alert.initOwner(Layout.window);
				
				alert.showAndWait();


			} catch (NoSuchElementException e) {

			} finally {
				bw.flush();
				bw.close();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
			pw.close();

		} 
	}

	public static void write(Window window, Stage primaryStage) {

		conn = main.conn;

		cvsWriter.write(primaryStage);

	}

	public static void setImpactTable(Stage primaryStage) {
		conn = main.conn;
		ObservableList<Gebruiker> g = FXCollections.observableArrayList();

		Statement stat;
		try {
			stat = conn.createStatement();
                        String q = "";
                        
                        
                        if (main.q.werkeenheidProfit != ""){
                            System.out.println(main.q.werkeenheidProfit + " Wat dit i");
                        
			q = "SELECT PC.Code, PC.PersoonID, count(A.GewijzigdDoor) as aantal_Activiteit "
					+ "  FROM [AuditBlackBox].[dbo].[Activiteit] A "
					+ "  join Persoon P on P.MedewerkerID = A.GewijzigdDoor "
					+ "  join PersoonCodes PC on PC.PersoonID = P.ID "
					+ "  join [AfasProfit-Export] AP on AP.EmployeeUsername = PC.Code "
					+ "  where AP.ContractEndDate < GETDATE() AND AP.EmployerName = '" + main.q.werkeenheidProfit + "'"
					+ " group by PC.Code, PC.PersoonID";

                        } else{
                            q = "SELECT PC.Code, PC.PersoonID, count(A.GewijzigdDoor) as aantal_Activiteit "
					+ "  FROM [AuditBlackBox].[dbo].[Activiteit] A "
					+ "  join Persoon P on P.MedewerkerID = A.GewijzigdDoor "
					+ "  join PersoonCodes PC on PC.PersoonID = P.ID "
					+ "  join [AfasProfit-Export] AP on AP.EmployeeUsername = PC.Code "
					+ "  where AP.ContractEndDate < GETDATE() " 
					+ " group by PC.Code, PC.PersoonID";

                        }
                        
                        
			ResultSet res = stat.executeQuery(q);

			while (res.next()) {
				Gebruiker newGebruiker = new Gebruiker(res.getString("Code"), res.getString("PersoonID"),
						res.getString("aantal_Activiteit"));
				g.add(newGebruiker);
				System.out.println("test");
			}

			Layout.impactTable.setItems(g);
			Layout.toCvs.setDisable(false);
			Layout.sDatabase.setDisable(false);
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Impact geladen");
			alert.setHeaderText(null);
			alert.setContentText("De Impact is geladen en in de tabel neergezet.");
			alert.initOwner(primaryStage);
			alert.showAndWait();
			
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void setEenheden(Query q) {
		q.setEenheid();
	}

	public static void writeSDatabase(Stage ps) {
		Signaaldatabase.start(ps);
	}

	public static void closeProgram() {
		try {
			java.sql.Statement stat = main.conn.createStatement();
			String query = "DROP TABLE LoginInfo;";
			stat.execute(query);
			main.conn.close();

			Layout.window.close();

			System.out.println("Programma afgesloten, en connectie uit");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	public static void writeRule1(Connection con, TextArea a, Query q) {
		// Business Rule 1:
		// AD account onbekend in Profit
		try {

			Statement stat = conn.createStatement();
			String query = q.getQuery1();
			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	AD Account " + res.getString("username_pre2000") + " is onbekend in profit \n";
				count++;
			}
			a.appendText("PROFIT <> AD - AD Account onbepkend in profit : " + count + "\n");
			a.appendText(found);

			Layout.bRuleText1.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 1 \n");
			a.appendText(e.getMessage() + "\n \n");

		}

	}

	public static void writeRule2(Connection con, TextArea a, Query q) {
		// Business Rule 2: Medewerler uitdienst in profit, maar account is nog actief
		// in clever
		// AD account onbekend in Profit
		try {

			Statement stat = conn.createStatement();
			String query = q.getQuery2();
			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	Medewerker " + res.getString("EmployeeUsername")
						+ " is uit diesnt, maar nog actief in Clever\n";
				count++;
			}
			a.appendText("PROFIT <> AD - Medewerker is uit diesnst, maar nog actief in Clever : " + count + "\n");
			a.appendText(found);

			Layout.bRuleText2.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 2 \n");
			a.appendText(e.getMessage() + "\n \n");

		}

	}

	public static void writeRule3(Connection con, TextArea a, Query q) {
		// Business Rule 3:
		// Citrix naam in PROFIT bestaat niet in de AD
		try {

			Statement stat = conn.createStatement();
			String query = q.getQuery3();
			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	Citrix naam " + res.getString("EmployeeUsername") + " is niet in gevuld in AD \n";
				count++;
			}
			a.appendText("PROFIT <> AD - Citrix naam in PROFIT bestaat niet in de AD : " + count + "\n");
			a.appendText(found);

			Layout.bRuleText3.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 3 \n");
			a.appendText(e.getMessage() + "\n \n");

		}

	}

	public static void writeRule4(Connection con, TextArea a, Query q) {
		// Business Rule 4:
		// RDS naam in Clevernew is niet ingevuld
		try {

			Statement stat = conn.createStatement();
			String query = q.getQuery4();

			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	RDS naam " + res.getString("Code") + " is niet ingevuld \n";
				count++;
			}
			a.appendText("Clever <> AD - : RDS naam in Clever is niet ingevuld" + count + "\n");
			a.appendText(found);

			Layout.bRuleText4.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 4 \n");
			a.appendText(e.getMessage() + "\n \n");

		}
	}

	public static void writeRule5(Connection con, TextArea a, Query q) {
		// Business Rule 5:
		// RDS naam in Clever bestaat niet in AD
		try {

			Statement stat = conn.createStatement();
			String query = q.getQuery5();

			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	De RDS naam van " + res.getString("Code") + " bestaat niet in AD \n";
				count++;
			}
			a.appendText("CleverNew <> AD RDS naam in Clever bestaat niet in AD:  " + count + "\n");
			a.appendText(found);

			Layout.bRuleText5.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 5 \n");
			a.appendText(e.getMessage() + "\n \n");

		}
	}

	public static void writeRule6(Connection con, TextArea a, Query q) {
		// Business Rule 6:
		// Medewerker uit dienst in Clever, actief in AD
		try {

			Statement stat = conn.createStatement();
			String query = q.getQuery6();

			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	Medewerker " + res.getString("Code") + " uit dienst in Clever, actief in AD \n";
				count++;
			}
			a.appendText("CleverNew <> Medewerker uit diesnt in Clever, actief in AD:  " + count + "\n");
			a.appendText(found);

			Layout.bRuleText6.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 6 \n");
			a.appendText(e.getMessage() + "\n \n");

		}

	}

	public static void writeRule7(Connection con, TextArea a, Query q) {
		// Business Rule 7:
		// AD Account, onbekend in Clever
		try {

			Statement stat = conn.createStatement();
			String query = q.getQuery7();

			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	AD Account " + res.getString("Username_Pre2000") + " onbekend in Clever \n";
				count++;
			}
			a.appendText("CleverNew <> AD Account onbekend in Clever:  " + count + "\n");
			a.appendText(found);

			Layout.bRuleText7.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 7 \n");
			a.appendText(e.getMessage() + "\n \n");

		}

	}

	public static void writeRule8(Connection con, TextArea a, Query q) {
		// Business Rule 8:
		// RDS username in profit bestaat niet in clever
		try {

			Statement stat = conn.createStatement();
			String query = q.getQuery8();

			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	De RDS naam van " + res.getString("EmployeeUsername")
						+ " bestaat niet in Clever \n";
				count++;
			}
			a.appendText("CleverNew <> Profit RDS naam in Clever bestaat niet in Clever:  " + count + "\n");
			a.appendText(found);

			Layout.bRuleText8.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 8 \n");
			a.appendText(e.getMessage() + "\n \n");

		}

	}

	public static void writeRule9(Connection con, TextArea a, Query q) {
		// Business Rule 9:
		// Medewerker uitdiesnst in profit, account is actief in clever
		try {

			Statement stat = conn.createStatement();
			String query = q.getQuery9();

			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	Medewerker " + res.getString("EmployeeUsername")
						+ " uit dienst in Profit, is actief in Clever \n";
				count++;
			}
			a.appendText("CleverNew <> Proofit Medewerker uitdienst in profit, is actief in Clever:  " + count + "\n");
			a.appendText(found);

			Layout.bRuleText9.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 9 \n");
			a.appendText(e.getMessage() + "\n \n");

		}

	}

	public static void writeRule10(Connection con, TextArea a, Query q) {
		// Business Rule 10:
		// Username in Clever bestaat niet in Afas Profit
		try {

			Statement stat = conn.createStatement();
			String query = q.getQuery10();

			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	De RDS naam van " + res.getString("Code") + " bestaat niet in Afas Profit \n";
				count++;
			}
			a.appendText("CleverNew <> Profit: username om Clever bestaat niet in Afas profit  " + count + "\n");
			a.appendText(found);

			Layout.bRuleText10.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 10 \n");
			a.appendText(e.getMessage() + "\n \n");

		}

	}

	public static Task<?> createRuler1(Connection conn, TextArea a) {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {

				try {
					writeRule1(conn, a, main.q);
					writeRule2(conn, a, main.q);
					writeRule3(conn, a, main.q);
					writeRule4(conn, a, main.q);
					writeRule5(conn, a, main.q);
					writeRule6(conn, a, main.q);
					writeRule7(conn, a, main.q);
					writeRule8(conn, a, main.q);
					writeRule9(conn, a, main.q);
					writeRule10(conn, a, main.q);

					return true;
				} catch (NullPointerException e) {
				}
				return null;

			}
		};
	}

	public static void errorString(String[] args) {
		ErrorString.launch(ErrorString.class, args);		
	}
}
