import java.sql.*;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Pair;

public class action {

	static Connection conn;

	public static void writeRules(TextArea a, Window window, Stage primaryStage) {

		conn = main.conn;

		a.setText("");

		Layout.load.setDisable(true);
		Layout.ProgBar.setProgress(0);

		Task<?> ruler1 = createRuler1(conn, a);
		Thread rule1Thread = new Thread(ruler1);
		
//		Dialog<?> dialog = new Dialog<>();
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
		
		

		ruler1.addEventFilter(WorkerStateEvent.WORKER_STATE_RUNNING, new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent t) {



				 dialog.showAndWait();
			}
		});

		ruler1.addEventFilter(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler<WorkerStateEvent>() {

			@Override
			public void handle(WorkerStateEvent t) {
				dialog.close();
				Layout.load.setDisable(false);
			}
		});

		rule1Thread.start();
		// try {
		// rule1Thread.join();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }

	}

	public static void writeRule1(Connection con, TextArea a) {

	}

	public static void writeRule2(Connection con, TextArea a) {

	}

	public static void writeRule3(Connection con, TextArea a) {
		// Business Rule 3:
		// Citrix naam in PROFIT bestaat niet in de AD
		try {

			// Layout.r.setWidth(20);

			Statement stat = conn.createStatement();
			String query = "SELECT username_pre2000 " + "FROM [AuditBlackBox].[dbo].[AD-Export] "
					+ "WHERE username_pre2000 not in "
					+ "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export])";

			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	Citrix naam " + res.getString("username_pre2000") + " is niet in gevuld in AD \n";
				System.out.println(res.getString("username_pre2000"));
				count++;
			}
			a.appendText("PROFIT <> AD - Citrix naam in PROFIT bestaat niet in de AD : " + count + "\n");
			a.appendText(found);
			System.out.println(count);

			Layout.bRuleText3.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 3 \n");
			a.appendText(e.getMessage() + "\n \n");

		}

	}

	public static void writeRule4(Connection con, TextArea a) {
		// Business Rule 4:
		// Medewerker uit dienst in profit, account is in AD actief
		try {

			// Layout.r.setWidth(500);

			Statement stat = conn.createStatement();
			String query = "SELECT * " + "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR "
					+ "WHERE PR.ContractEndDate < GETDATE() AND PR.EmployeeUsername " + "IN (SELECT A.Username_Pre2000 "
					+ "FROM [AD-Export] A)";

			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	Medewerker " + res.getString("EmployeeUsername")
						+ " is uit diesnst maar actief in AD\n";
				System.out.println(res.getString("EmployeeUsername"));
				count++;
			}
			a.appendText("PROFIT <> AD - : Medewerker uit dienst in profit, account is AD actief" + count + "\n");
			a.appendText(found);
			System.out.println(count);

			Layout.bRuleText4.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 4 \n");
			a.appendText(e.getMessage() + "\n \n");

		}
	}

	public static void writeRule5(Connection con, TextArea a) {
		// Business Rule 5:
		// CleverNew <> AD Citrix naam in CleverNew niet ingevuld
		try {

			Statement stat = conn.createStatement();
			String query = "SELECT PC.Code " + "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID "
					+ "JOIN Medewerker M ON P.id = M.PersoonID " + "WHERE PC.code = 'Andere Code'";

			ResultSet res = stat.executeQuery(query);

			int count = 0;

			String found = "";
			while (res.next()) {
				found = found + "	De citrix naam van " + res.getString("Code") + "is niet in gevuld in Clever \n";
				System.out.println(res.getString("Code"));
				count++;
			}
			a.appendText("CleverNew <> AD Citrix naam in CleverNew niet ingevuld:  " + count + "\n");
			a.appendText(found);
			System.out.println(count);

			Layout.bRuleText5.setText(Integer.toString(count));

		} catch (SQLException e) {
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 3 \n");
			a.appendText(e.getMessage() + "\n \n");

		}
	}

	public static void writeRule6(Connection con, TextArea a) {

	}

	public static void writeRule7(Connection con, TextArea a) {

	}

	public static void writeRule8(Connection con, TextArea a) {

	}

	public static void writeRule9(Connection con, TextArea a) {

	}

	public static void writeRule10(Connection con, TextArea a) {

	}

	public static Task<?> createRuler1(Connection conn, TextArea a) {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {

			try {
				
				
//				writeRule1(conn, a);
//				writeRule2(conn, a);
				writeRule3(conn, a);
				 writeRule4(conn, a);
				 writeRule5(conn, a);
				// writeRule6(conn, a);
				// writeRule7(conn, a);
				// writeRule8(conn, a);
				// writeRule9(conn, a);
				// writeRule10(conn, a);

				return true;
			}
			catch(NullPointerException e) {
				System.out.println("Fout");
			}
			return null;
				
			}
		};
	}

	public static Task<?> createRuler2(Connection conn, TextArea a) {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {

				writeRule4(conn, a);
				writeRule5(conn, a);
				writeRule6(conn, a);

				return true;
			}
		};
	}

	public static Task<?> createRuler3(Connection conn, TextArea a) {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {

				writeRule7(conn, a);
				writeRule8(conn, a);
				writeRule9(conn, a);
				writeRule10(conn, a);

				return true;
			}
		};
	}

}
