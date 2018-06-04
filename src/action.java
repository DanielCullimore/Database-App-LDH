import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import DataEntries.ProfitEntry;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Window;

public class action {

	static Connection conn;

	public static void LoadProfit() {

		conn = main.conn;

		try {
			Statement stat = conn.createStatement();
			String query = "SELECT medewerker, ;";

			ResultSet res = stat.executeQuery(query);

			while (res.next()) {
				System.out.println(res.getRow() + ": ");
				System.out.println(res.getString("ID") + ": ");
			}

		} catch (SQLException e) {
			System.out.println("Foutmelding:" + e.getMessage());

		}
	}

	public static ArrayList<ProfitEntry> fillProfitTable() {
		conn = main.conn;
		ArrayList<ProfitEntry> profitList = new ArrayList<>();

		try {
			Statement stat = conn.createStatement();
			String query = "SELECT EmployeeUsername, ContractStartDate, ContractEndDate\r\n"
					+ "FROM [AfasProfit-Export]";

			ResultSet res = stat.executeQuery(query);

			while (res.next()) {
				profitList.add(new ProfitEntry(res.getString("EmployeeUsername"), res.getDate("ContractStartDate"),
						res.getDate("ContractEndDate")));
			}

		} catch (SQLException e) {
			System.out.println("Foutmelding:" + e.getMessage());

		}

		return profitList;
	}

	public static void writeRules(TextArea a, Window window) {

		conn = main.conn;

		Layout.load.setDisable(true);
		Layout.ProgBar.setProgress(0);
		
		Task<?> ruler1 = createRuler1(conn, a);
		Thread rule1Thread = new Thread(ruler1);
		
		ruler1.addEventFilter(WorkerStateEvent.WORKER_STATE_RUNNING,  new EventHandler<WorkerStateEvent>() {
			 
            @Override
            public void handle(WorkerStateEvent t) {
        		Layout.ProgBar.setProgress(-1);
            }
        });
		
		
		ruler1.addEventFilter(WorkerStateEvent.WORKER_STATE_SUCCEEDED,  new EventHandler<WorkerStateEvent>() {
			 
            @Override
            public void handle(WorkerStateEvent t) {
        		Layout.ProgBar.setProgress(1);
        		Layout.load.setDisable(false);
            }
        });
		
		rule1Thread.start();
		
		
		
		
//		Layout.ProgBar.setProgress(-1);
//
//		ArrayList<Thread> t = new ArrayList<>();
//
//
//		
//		Task<?> progressTask = createProgressT();
//		Thread progressTaskThread = new Thread(progressTask);
//		t.add(progressTaskThread);
//		progressTaskThread.start();
//
//		Task<?> ruler1 = createRuler1(conn, a);
//		Thread rule1Thread = new Thread(ruler1);
//		t.add(rule1Thread);
//		rule1Thread.start();
//
//		Task<?> ruler2 = createRuler2(conn, a);
//		Thread rule2Thread = new Thread(ruler2);
//		t.add(rule2Thread);
//		rule2Thread.start();
//
//		Task<?> ruler3 = createRuler3(conn, a);
//		Thread rule3Thread = new Thread(ruler3);
//		t.add(rule3Thread);
//		rule3Thread.start();
//
//		for (int i = 0; i < t.size(); i++) {
//			try {
//				t.get(i).join();
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//
//		Layout.ProgBar.setProgress(1);


	}

	private static Task<?> createProgressT() {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {

				AlertBox.display("sdfsdf", "sdfsdf");

				return true;
			}
		};
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

	public static Task<?> createWorker() {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {
				for (int i = 0; i < 10; i++) {
					Thread.sleep(2000);
					updateMessage("2000 milliseconds");
					updateProgress(i + 1, 10);
				}
				return true;
			}
		};
	}

	public static Task<?> createRuler1(Connection conn, TextArea a) {
		return new Task<Object>() {
			@Override
			protected Object call() throws Exception {

				writeRule1(conn, a);
				writeRule2(conn, a);
				writeRule3(conn, a);
//				writeRule4(conn, a);
				writeRule5(conn, a);
//				writeRule6(conn, a);
//				writeRule7(conn, a);
//				writeRule8(conn, a);
//				writeRule9(conn, a);
//				writeRule10(conn, a);

				return true;
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
