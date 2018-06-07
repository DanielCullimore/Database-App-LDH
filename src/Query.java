import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author megas
 */
public class Query {

	// AD Account, onbekend in Profit
	String query1 = "SELECT username_pre2000 " + "FROM [AuditBlackBox].[dbo].[AD-Export] " + "WHERE username_pre2000 "
			+ "not in " + "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export])";

	// Medewerker uit dienst in Profit, account is in AD actief
	String query2 = "SELECT PR.EmployeeUsername " + "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR "
			+ "WHERE PR.ContractEndDate < GETDATE() AND PR.EmployeeUsername " + "IN (SELECT A.Username_Pre2000 "
			+ "FROM [AuditBlackBox].[dbo].[AD-Export] A) ";

	// RDS User naam in Profit bestaat niet in de AD
	String query3 = "SELECT PR.EmployeeUsername " + "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR "
			+ "WHERE EmployeeUsername not in " + "(select Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export])";

	// RDS naam in Clevernew is niet ingevuld
	String query4 = "SELECT DISTINCT P.ID, PC.Code, o.Naam "
			+ "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID " + "JOIN Medewerker M ON P.id = M.PersoonID "
			+ "JOIN teamlid tl on tl.PersoonID = pc.PersoonID " + "JOIN team t on t.ID = tl.TeamID "
			+ "JOIN OrganisatieEenheid o on  o.OrganisatieID = t.OrganisatieEenheidID "
			+ "WHERE PC.code = 'Andere Code'";

	// RDS naam in CleverNew bestaat niet in AD
	String query5 = "SELECT DISTINCT P.ID, pc.Code  " + "FROM Persoon P "
			+ "JOIN PersoonCodes PC ON P.id = PC.PersoonID " + "JOIN Medewerker M ON P.id = M.PersoonID "
			+ "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " + "JOIN Team t on t.ID = tl.ID "
			+ "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "
			+ "WHERE PC.code NOT IN (SELECT A.Username_Pre2000 FROM [AD-Export] A)";

	// Medewerker uit dienst in CleverNew, account in AD actief
	String query6 = "SELECT DISTINCT P.ID, PC.Code " + "	FROM Persoon P "
			+ "	JOIN PersoonCodes PC ON P.id = PC.PersoonID  " + "	JOIN Medewerker M ON P.id = M.PersoonID "
			+ "	JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " + "	JOIN Team t on t.ID = tl.ID "
			+ "	JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "
			+ "WHERE PC.CodesoortenID NOT LIKE 981 AND PC.Code IN(SELECT A.Username_Pre2000 " + "FROM [AD-Export]A)";

	// AD Account, onbekend in Clever
	String query7 = "SELECT A.Username_Pre2000 " + "FROM [AD-Export]A " + "WHERE A.Username_Pre2000 NOT IN("
			+ "SELECT PC.Code " + "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID "
			+ "JOIN Medewerker M ON P.id = M.PersoonID) ";

	// RDS User naam in Profit bestaat niet in Clever
	String query8 = "SELECT PR.EmployeeUsername "
			+ "FROM [AfasProfit-Export] PR JOIN PersoonCodes PC ON PC.Code = PR.EmployeeUsername "
			+ "JOIN Persoon P ON PC.PersoonID = P.ID JOIN Medewerker M ON M.PersoonID = P.ID "
			+ "WHERE PR.EmployeeUsername NOT IN (SELECT PC.code " + "FROM Persooncodes) ";

	// Medewerker uit dienst in Profit, account is in Clever actief
	String query9 = "SELECT PR.EmployeeUsername "
			+ "FROM [AfasProfit-Export] PR JOIN PersoonCodes PC ON PC.Code = PR.EmployeeUsername "
			+ "JOIN Persoon P ON PC.PersoonID = P.ID JOIN Medewerker M ON M.PersoonID = P.ID "
			+ "WHERE Pr.ContractEndDate < GETDATE() AND PC.CodesoortenID = 981 "
			+ "AND PR.EmployeeUsername IN (SELECT PC.code " + "FROM Persooncodes)";

	// RDS User naam in Clever bestaat niet in Afas Profit
	String query10 = "SELECT PC.Code " + "FROM Persoon P " + "JOIN PersoonCodes PC ON P.id = PC.PersoonID "
			+ "JOIN Medewerker M ON P.id = M.PersoonID " + "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID "
			+ "JOIN Team t on t.ID = tl.ID " + "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "
			+ "WHERE PC.Code NOT IN (SELECT PR.EmployeeUsername " + "FROM [AfasProfit-Export] PR)";

	String werkeenheidClever = "";
	String werkeenheidProfit = "";
	String werkeenheidADExport = "";
	
	public String getWerkeenheidClever() {
		return werkeenheidClever;
	}

	public String getWerkeenheidProfit() {
		return werkeenheidProfit;
	}

	public String getWerkeenheidExport() {
		return werkeenheidADExport;
	}

	public String getQuery1() {
		return query1;
	}

	public String getQuery2() {
		return query2;
	}

	public String getQuery3() {
		return query3;
	}

	public String getQuery4() {
		return query4;
	}

	public String getQuery5() {
		return query5;
	}

	public String getQuery6() {
		return query6;
	}

	public String getQuery7() {
		return query7;
	}

	public String getQuery8() {
		return query8;
	}

	public String getQuery9() {
		return query9;
	}

	public String getQuery10() {
		return query10;
	}

	public void setQuery1(String add) {
		query1 = query1 + " and ParentContainer = '" + add + "'";
	}

	public void setQuery2(String add) {
		query2 = query2 + " and EmployerName = '" + add + "'";
	}

	public void setQuery3(String add) {
		query3 = query3 + " and EmployerName = '" + add + "'";
	}

	public void setQuery4(String add) {
		query4 = query4 + " AND o.Naam = '" + add + "'";
	}

	public void setQuery5(String add) {
		query5 = query5 + " AND o.Naam = '" + add + "'";
	}

	public void setQuery6(String add) {
		query6 = query6 + " AND o.Naam = '" + add + "'";
	}

	public void setQuery7(String add) {
		query7 = query7 + " and ParentContainer = '" + add + "'";
	}

	public void setQuery8(String add) {
		query8 = query8 + " and EmployerName = '" + add + "'";
	}

	public void setQuery9(String add) {
		query9 = query9 + " and EmployerName = '" + add + "'";
	}

	public void setQuery10(String add) {
		query10 = query10 + " AND o.Naam = '" + add + "'";
	}

	public void setEenheid() {
		setClever();
		setProfit();
		setExport();

	}

	public void setClever() {

		try {
			Statement stat = main.conn.createStatement();
			String query = "select o.naam " + "from organisatieEenheid o "
					+ "join team t on o.organisatieid = t.organisatieEenheidID "
					+ "join teamlid tl on tl.teamID = t.ID " + "join persooncodes p on p.persoonid = tl.persoonID "
					+ "where OrganisatieEenheidNiveauID = 3 " + "and p.code like '" + System.getProperty("user.name")
					+ "'";
			ResultSet res = stat.executeQuery(query);
			System.out.println(System.getProperty("user.name"));
			
			while (res.next()) {
				werkeenheidClever = res.getString("naam");

			}

			setQuery4(werkeenheidClever);
			setQuery5(werkeenheidClever);
			setQuery6(werkeenheidClever);
			setQuery7(werkeenheidClever);
			setQuery10(werkeenheidClever);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setProfit() {
		try {
			Statement stat = main.conn.createStatement();
			String query = "SELECT [EmployerName] " + "  FROM [AuditBlackBox].[dbo].[AfasProfit-Export] "
					+ "  where EmployeeUsername = '" + System.getProperty("user.name") + "'";
			ResultSet res = stat.executeQuery(query);

			while (res.next()) {
				werkeenheidProfit = res.getString("EmployerName");
				System.out.println(werkeenheidProfit);
			}
			
			
			setQuery2(werkeenheidProfit);
			setQuery3(werkeenheidProfit);
			setQuery8(werkeenheidProfit);
			setQuery9(werkeenheidProfit);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void setExport() {

		try {
			Statement stat = main.conn.createStatement();
			String query = "SELECT ParentContainer " + "  FROM [AuditBlackBox].[dbo].[AD-Export] "
					+ "  where Username_Pre2000 = '" + System.getProperty("user.name") + "'";
			ResultSet res = stat.executeQuery(query);

			while (res.next()) {
				werkeenheidADExport = res.getString("ParentContainer");
			}
		} catch (

		SQLException e) {
			System.out.println("Foutmelding: " + e.getMessage());

		}
		setQuery1(werkeenheidADExport);
	}
}
