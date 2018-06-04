import java.sql.*;
import java.util.ArrayList;

import DataEntries.ProfitEntry;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

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

	public static void writeRules(TextArea a) {

		conn = main.conn;
		
		

		writeRule1(conn, a);
		writeRule2(conn, a);
		writeRule3(conn, a);
		writeRule4(conn, a);
		writeRule5(conn, a);
		writeRule6(conn, a);
		writeRule7(conn, a);
		writeRule8(conn, a);
		writeRule9(conn, a);
		writeRule10(conn, a);

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

}
