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

    //AD Account, onbekend in Profit
    String query1 = "SELECT username_pre2000 "
            + "FROM [AuditBlackBox].[dbo].[AD-Export] "
            + "WHERE username_pre2000 "
            + "not in "
            + "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export]) ";

    //Medewerker uit dienst in Profit, account is in AD actief
    String query2 = "SELECT PR.EmployeeUsername "
            + "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR "
            + "WHERE PR.ContractEndDate < GETDATE() AND PR.EmployeeUsername "
            + "IN (SELECT A.Username_Pre2000 "
            + "FROM [AuditBlackBox].[dbo].[AD-Export] A)";

    //RDS User naam in Profit bestaat niet in de AD
    String query3 = "SELECT A.EmployeeUsername "
            + "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] A "
            + "WHERE EmployeeUsername not in "
            + "(select Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export]) ";

    //RDS naam in Clevernew is niet ingevuld
    String query4 = "SELECT PC.Code "
            + "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID "
            + "JOIN Medewerker M ON P.id = M.PersoonID "
            + "WHERE PC.code = 'Andere Code' ";

    //RDS naam in CleverNew bestaat niet in AD
    String query5 = "SELECT PC.Code "
            + "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID "
            + "JOIN Medewerker M ON P.id = M.PersoonID "
            + "WHERE PC.code NOT IN (SELECT A.Username_Pre2000 "
            + "FROM [AD-Export] A) ";
    
    //Medewerker uit dienst in CleverNew, account in AD actief
    String query6 = "SELECT PC.Code "
            + "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID "
            + "JOIN Medewerker M ON P.id = M.PersoonID "
            + "WHERE PC.CodesoortenID NOT LIKE 981 AND PC.Code IN(SELECT A.Username_Pre2000 "
            + "FROM [AD-Export]A) ";

    //AD Account, onbekend in Clever
    String query7 = "SELECT A.Username_Pre2000 "
            + "FROM [AD-Export]A "
            + "WHERE A.Username_Pre2000 NOT IN( "
            + "SELECT PC.Code "
            + "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID "
            + "JOIN Medewerker M ON P.id = M.PersoonID) ";

    //RDS User naam in Profit bestaat niet in Clever
    String query8 = "SELECT PR.EmployeeUsername "
            + "FROM [AfasProfit-Export] PR JOIN PersoonCodes PC ON PC.Code = PR.EmployeeUsername "
            + "JOIN Persoon P ON PC.PersoonID = P.ID JOIN Medewerker M ON M.PersoonID = P.ID "
            + "WHERE PR.EmployeeUsername NOT IN (SELECT PC.code "
            + "FROM Persooncodes) ";

    //Medewerker uit dienst in Profit, account is in Clever actief
    String query9 = "SELECT PR.EmployeeUsername "
            + "FROM [AfasProfit-Export] PR JOIN PersoonCodes PC ON PC.Code = PR.EmployeeUsername "
            + "JOIN Persoon P ON PC.PersoonID = P.ID JOIN Medewerker M ON M.PersoonID = P.ID "
            + "WHERE Pr.ContractEndDate < GETDATE() AND PC.CodesoortenID = 981 "
            + "AND PR.EmployeeUsername IN (SELECT PC.code "
            + "FROM Persooncodes) ";

    //RDS User naam in Clever bestaat niet in Afas Profit
    String query10 = "SELECT PC.Code "
            + "FROM PersoonCodes PC JOIN Persoon P ON PC.PersoonID = P.ID "
            + "JOIN Medewerker M ON M.PersoonID = P.ID "
            + "WHERE PC.Code NOT IN (SELECT PR.EmployeeUsername "
            + "FROM [AfasProfit-Export] PR) ";

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

}
