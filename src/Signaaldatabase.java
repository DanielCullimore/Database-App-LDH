
/**
 *
 * @author dc_19
 */
public class Signaaldatabase {
    
    public void updateFK(){
    String update = "Update A"
    + "SET A.Impact = I.Nr, A.Status = S.Nr"
    + "FROM Afwijking A JOIN Impact I ON A.Ba_account = I.Ba_account" 
    + "JOIN Status S ON S.Ba_account = A.Ba_account";
    }
    
    public void insertData(){
     String query1 = 
            "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding)"    
            + "SELECT username_pre2000, 'AD Account, onbekend in Profit'"
            + "FROM [AuditBlackBox].[dbo].[AD-Export]"
            + "WHERE username_pre2000"
            + "not in" 
            + "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export])";
    
     String query2 = 
            "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account)"    
            + "SELECT 0, ‘?’, username_pre2000"
            + "FROM [AuditBlackBox].[dbo].[AD-Export]"
            + "WHERE username_pre2000"
            + "not in" 
            + "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export])";
     
    String query3 = "INSERT INTO Status( Datum_opgelost, Ba_account)"    
            + "SELECT NULL, A.Username_Pre2000"
            + "FROM [AuditBlackBox].[dbo].[AD-Export]"
            + "WHERE username_pre2000"
            + "not in" 
            + "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export])"; 
    }
    
    
    
    public void insertData1(){
    String query1 = 
            "INSERT INTO Afwijking (Ba_account, tekstmelding)"
            + "SELECT PR.EmployeeUsername, 'Medewerker uit dienst in Profit, account is in AD actief'" 
            + "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR " 
            + "WHERE PR.ContractEndDate < GETDATE() AND PR.EmployeeUsername "  
            + "IN (SELECT A.Username_Pre2000 " 
            + "FROM [AuditBlackBox].[dbo].[AD-Export] A)";
    
    
    String query2 = 
            "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_Account)"
            + "SELECT 0, '?', PR.EmployeeUsername" 
            + "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR " 
            + "WHERE PR.ContractEndDate < GETDATE() AND PR.EmployeeUsername "  
            + "IN (SELECT A.Username_Pre2000 " 
            + "FROM [AuditBlackBox].[dbo].[AD-Export] A)";
    
    String query3 = 
            "INSERT INTO Status(Datum_opgelost, Ba_account)"
            + "SELECT NULL, PR.EmployeeUsername" 
            + "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR " 
            + "WHERE PR.ContractEndDate < GETDATE() AND PR.EmployeeUsername "  
            + "IN (SELECT A.Username_Pre2000 " 
            + "FROM [AuditBlackBox].[dbo].[AD-Export] A)";
    }
    
    
    public void insertData2(){
    String query1 = 
                "INSERT INTO Afwijking (Ba_account, tekstmelding)"
                + "SELECT PR.EmployeeUsername, 'RDS User naam in Profit bestaat niet in de AD'" 
    		+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR " 
    		+ "WHERE EmployeeUsername not in " 
    		+ "(select Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export])"; 
    
    String query2 = 
                "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account)"
                + "SELECT 1, '?', PR.EmployeeUsername" 
    		+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR " 
    		+ "WHERE EmployeeUsername not in " 
    		+ "(select Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export])"; 
    
    String query3 = 
                "INSERT INTO Status( Datum_opgelost, Ba_account)"
                + "SELECT NULL, PR.EmployeeUsername" 
    		+ "FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR " 
    		+ "WHERE EmployeeUsername not in " 
    		+ "(select Username_Pre2000 FROM [AuditBlackBox].[dbo].[AD-Export])";
 
    }
    
    public void insertData3(){
    String query1 = 
                "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding)"
                + "SELECT DISTINCT P.ID, PC.Code, o.Naam "  
    		+ "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID "  
    		+ "JOIN Medewerker M ON P.id = M.PersoonID " 
    		+ "JOIN teamlid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN team t on t.ID = tl.TeamID "  
    		+ "JOIN OrganisatieEenheid o on  o.OrganisatieID = t.OrganisatieEenheidID " 
    		+ "WHERE PC.code = 'Andere Code'";
    
     String query2 = 
                "INSERT INTO [SignaalDatabase].[dbo].Impact (Prioriteit, Tekstmelding, Ba_account)"
                + "SELECT 0, '?', PC.Code"  
    		+ "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID "  
    		+ "JOIN Medewerker M ON P.id = M.PersoonID " 
    		+ "JOIN teamlid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN team t on t.ID = tl.TeamID "  
    		+ "JOIN OrganisatieEenheid o on  o.OrganisatieID = t.OrganisatieEenheidID " 
    		+ "WHERE PC.code = 'Andere Code'";
    
    String query3 = 
                "INSERT INTO Status(Datum_opgelost, Ba_account)"
                + "SELECT NULL, PC.Code" 
    		+ "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID " 
    		+"JOIN Medewerker M ON P.id = M.PersoonID " 
    		+ "JOIN teamlid tl on tl.PersoonID = pc.PersoonID "  
    		+ "JOIN team t on t.ID = tl.TeamID "  
    		+ "JOIN OrganisatieEenheid o on  o.OrganisatieID = t.OrganisatieEenheidID "  
    		+ "WHERE PC.code = 'Andere Code'";
    
    }
    
    public void insertData4(){
    String query1 = 
                "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding)"
                + "SELECT PC.Code, 'RDS naam in CleverNew bestaat niet in AD'"  
    		+ "FROM Persoon P " 
    		+ "JOIN PersoonCodes PC ON P.id = PC.PersoonID " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID " 
    		+ "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN Team t on t.ID = tl.ID " 
    		+ "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "  
    		+ "WHERE PC.code NOT IN (SELECT A.Username_Pre2000 FROM [AD-Export] A)";
    
    String query2 = 
                "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account))"
                + "SELECT 0,'?', PC.Code"  
    		+ "FROM Persoon P " 
    		+ "JOIN PersoonCodes PC ON P.id = PC.PersoonID " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID " 
    		+ "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN Team t on t.ID = tl.ID " 
    		+ "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "  
    		+ "WHERE PC.code NOT IN (SELECT A.Username_Pre2000 FROM [AD-Export] A)";
    
    String query3 = 
                  "INSERT INTO Status(Datum_opgelost, Ba_account)"
                + "SELECT getDate(), NULL, PC.Code"  
    		+ "FROM Persoon P " 
    		+ "JOIN PersoonCodes PC ON P.id = PC.PersoonID " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID " 
    		+ "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN Team t on t.ID = tl.ID " 
    		+ "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "  
    		+ "WHERE PC.code NOT IN (SELECT A.Username_Pre2000 FROM [AD-Export] A)";
    }
    
   public void insertData5(){
   String query1 = 
                "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding)"
                + "SELECT PC.Code, 'Medewerker uit dienst in CleverNew, account in AD actief'"
    		+ "FROM Persoon P " 
    		+ "JOIN PersoonCodes PC ON P.id = PC.PersoonID  " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID " 
    		+ "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN Team t on t.ID = tl.ID " 
    		+ "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "  
    		+ "WHERE PC.CodesoortenID NOT LIKE 981 AND PC.Code IN(SELECT A.Username_Pre2000"  
    		+ "FROM [AD-Export]A)";
   
   String query2 = 
                "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account)"
                + "SELECT 0, '?', PC.Code"
    		+ "FROM Persoon P " 
    		+ "JOIN PersoonCodes PC ON P.id = PC.PersoonID  " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID " 
    		+ "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN Team t on t.ID = tl.ID " 
    		+ "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "  
    		+ "WHERE PC.CodesoortenID NOT LIKE 981 AND PC.Code IN(SELECT A.Username_Pre2000"  
    		+ "FROM [AD-Export]A)";
   
   String query3 = 
                "INSERT INTO Status(Datum_opgelost, Ba_account)"
                + "SELECT NULL, PC.code"
    		+ "FROM Persoon P " 
    		+ "JOIN PersoonCodes PC ON P.id = PC.PersoonID  " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID " 
    		+ "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN Team t on t.ID = tl.ID " 
    		+ "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "  
    		+ "WHERE PC.CodesoortenID NOT LIKE 981 AND PC.Code IN(SELECT A.Username_Pre2000"  
    		+ "FROM [AD-Export]A)";
   }         
            
   public void insertData6(){
    String query1 =
                "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding)"
                + "SELECT A.Username_Pre2000, ‘AD Account, onbekend in Clever’" 
    		+ "FROM [AD-Export]A " 
    		+ "WHERE A.Username_Pre2000 NOT IN(" 
    		+ "SELECT PC.Code " 
    		+ "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID)";
   
    String query2 = 
                "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account)"
                + "SELECT 0, '?', A.Username_Pre2000" 
    		+ "FROM [AD-Export]A " 
    		+ "WHERE A.Username_Pre2000 NOT IN(" 
    		+ "SELECT PC.Code " 
    		+ "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID)";
   
    String query3 = 
                 "INSERT INTO Status( Datum_opgelost, Ba_account)"
                + "SELECT NULL, A.Username_Pre2000" 
    		+ "FROM [AD-Export]A " 
    		+ "WHERE A.Username_Pre2000 NOT IN(" 
    		+ "SELECT PC.Code " 
    		+ "FROM Persoon P JOIN PersoonCodes PC ON P.id = PC.PersoonID " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID)";
   }         
            
   
   public void insertData7(){
   String query1 = 
                "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding)"
                + "SELECT PR.EmployeeUsername, 'RDS User naam in Profit bestaat niet in Clever'" 
    		+ "FROM [AfasProfit-Export] PR JOIN PersoonCodes PC ON PC.Code = PR.EmployeeUsername " 
    		+ "JOIN Persoon P ON PC.PersoonID = P.ID JOIN Medewerker M ON M.PersoonID = P.ID "  
    		+ "WHERE PR.EmployeeUsername NOT IN (SELECT PC.code " 
    		+ "FROM Persooncodes) ";
   
            
   String query2 = 
                "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account)"
                + "SELECT 0, '?', PR.EmployeeUsername" 
    		+ "FROM [AfasProfit-Export] PR JOIN PersoonCodes PC ON PC.Code = PR.EmployeeUsername " 
    		+ "JOIN Persoon P ON PC.PersoonID = P.ID JOIN Medewerker M ON M.PersoonID = P.ID "  
    		+ "WHERE PR.EmployeeUsername NOT IN (SELECT PC.code " 
    		+ "FROM Persooncodes) ";
            
    String query3 =   
                "INSERT INTO Status(Datum_opgelost, Ba_account)"
                + "SELECT NULL, PR.EmployeeUsername" 
    		+ "FROM [AfasProfit-Export] PR JOIN PersoonCodes PC ON PC.Code = PR.EmployeeUsername " 
    		+ "JOIN Persoon P ON PC.PersoonID = P.ID JOIN Medewerker M ON M.PersoonID = P.ID "  
    		+ "WHERE PR.EmployeeUsername NOT IN (SELECT PC.code " 
    		+ "FROM Persooncodes) ";
}        
   
    public void insertData8(){
    String query1 = 
                "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding)"
                + "SELECT PR.EmployeeUsername, 'Medewerker uit dienst in Profit, account is in Clever actief'" 
    		+ "FROM [AfasProfit-Export] PR JOIN PersoonCodes PC ON PC.Code = PR.EmployeeUsername "  
    		+ "JOIN Persoon P ON PC.PersoonID = P.ID JOIN Medewerker M ON M.PersoonID = P.ID "  
    		+ "WHERE Pr.ContractEndDate < GETDATE() AND PC.CodesoortenID = 981 "  
    		+ "AND PR.EmployeeUsername IN (SELECT PC.code " 
    		+ "FROM Persooncodes)";
                
    String query2 = 
                "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account)"
                + "SELECT 0, ‘?’, PR.EmployeeUsername'" 
    		+ "FROM [AfasProfit-Export] PR JOIN PersoonCodes PC ON PC.Code = PR.EmployeeUsername "  
    		+ "JOIN Persoon P ON PC.PersoonID = P.ID JOIN Medewerker M ON M.PersoonID = P.ID "  
    		+ "WHERE Pr.ContractEndDate < GETDATE() AND PC.CodesoortenID = 981 "  
    		+ "AND PR.EmployeeUsername IN (SELECT PC.code " 
    		+ "FROM Persooncodes)";
    
    String query3 = 
                "INSERT INTO Status(Datum_opgelost, Ba_account)"
                + "SELECT NULL, PR.EmployeeUsername" 
    		+ "FROM [AfasProfit-Export] PR JOIN PersoonCodes PC ON PC.Code = PR.EmployeeUsername "  
    		+ "JOIN Persoon P ON PC.PersoonID = P.ID JOIN Medewerker M ON M.PersoonID = P.ID "  
    		+ "WHERE Pr.ContractEndDate < GETDATE() AND PC.CodesoortenID = 981 "  
    		+ "AND PR.EmployeeUsername IN (SELECT PC.code " 
    		+ "FROM Persooncodes)";
    
    }        
            
     public void insertData9(){
     String query1 = 
                "INSERT INTO [SignaalDatabase].[dbo].Afwijking (Ba_account, tekstmelding)"
                + "SELECT PC.Code, 'RDS User naam in Clever bestaat niet in Afas Profit'" 
    		+ "FROM Persoon P "  
    		+ "JOIN PersoonCodes PC ON P.id = PC.PersoonID " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID "  
    		+ "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN Team t on t.ID = tl.ID " 
    		+ "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "  
    		+ "WHERE PC.Code NOT IN (SELECT PR.EmployeeUsername "  
    		+ "FROM [AfasProfit-Export] PR)";

     String query2 = 
                "INSERT INTO Impact(Prioriteit, tekstmelding, Ba_account)"
                + "SELECT 0, '?', PC.Code" 
    		+ "FROM Persoon P "  
    		+ "JOIN PersoonCodes PC ON P.id = PC.PersoonID " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID "  
    		+ "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN Team t on t.ID = tl.ID " 
    		+ "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "  
    		+ "WHERE PC.Code NOT IN (SELECT PR.EmployeeUsername "  
    		+ "FROM [AfasProfit-Export] PR)";
             
     String query3 = 
                "INSERT INTO Status(Datum_opgelost, Ba_account)"
                + "SELECT NULL, PC.Code" 
    		+ "FROM Persoon P "  
    		+ "JOIN PersoonCodes PC ON P.id = PC.PersoonID " 
    		+ "JOIN Medewerker M ON P.id = M.PersoonID "  
    		+ "JOIN TeamLid tl on tl.PersoonID = pc.PersoonID " 
    		+ "JOIN Team t on t.ID = tl.ID " 
    		+ "JOIN OrganisatieEenheid o on o.OrganisatieID = t.OrganisatieEenheidID "  
    		+ "WHERE PC.Code NOT IN (SELECT PR.EmployeeUsername "  
    		+ "FROM [AfasProfit-Export] PR)"; 
     
     
     
     
     
     
     }       
            
            
            
            
            
            
            
            
    
}
