import java.sql.*;
import java.util.ArrayList;

import DataEntries.ProfitEntry;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;

public class action {
	
	static Connection conn;
	
	
	public static void LoadProfit() {
		
		conn = main.conn;
		
	      try{
	          Statement stat = conn.createStatement();
	          String query = "SELECT medewerker, ;";
	          
	          
	          
	          
	          	 
	          ResultSet res = stat.executeQuery(query);
	          
	          while(res.next()){
	            System.out.println(res.getRow() + ": ");
	            System.out.println(res.getString("ID") + ": ");
	          }
	      
		
	}
	      catch(SQLException e){
	            System.out.println("Foutmelding:" + e.getMessage());
	      
	      }
}

	public static ArrayList<ProfitEntry> fillProfitTable() {
		conn = main.conn;
		ArrayList<ProfitEntry> profitList = new ArrayList<>();
		
		
	      try{
	          Statement stat = conn.createStatement();
	          String query = "SELECT EmployeeUsername, ContractStartDate, ContractEndDate\r\n" + 
	          		"FROM [AfasProfit-Export]";
	          
	          
	          
	          
	          	 
	          ResultSet res = stat.executeQuery(query);
	          
	          while(res.next()){     	  
	        	  profitList.add(new ProfitEntry(res.getString("EmployeeUsername"),  res.getDate("ContractStartDate"), res.getDate("ContractEndDate")));
	          }
	      
		
	}
	      catch(SQLException e){
	            System.out.println("Foutmelding:" + e.getMessage());
	      
	      }
	
          return profitList;
	}

	public static void writeRules(TextArea a) {
		
		conn = main.conn;
		
		
		
		
		
		
		
		
		
		//Business Rule 3:
		//Citrix naam in PROFIT bestaat niet in de AD
		try {
			
			//Layout.r.setWidth(20);
			
			Statement stat = conn.createStatement();
			String query = "SELECT username_pre2000 "
					+ "FROM [AuditBlackBox].[dbo].[AD-Export] "
					+ "WHERE username_pre2000 not in "
					+ "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export])"; 
			
			ResultSet res = stat.executeQuery(query);
			
			int count = 0;
			
			

			String found = "";
			while(res.next()) {
				found = found + "	Citrix naam " + res.getString("username_pre2000") + " is niet in gevuld in AD \n";
				//a.appendText("	Citrix naam " + res.getString("username_pre2000") + " is niet in gevuld in AD \n" );
				System.out.println(res.getString("username_pre2000"));
				count++;
			}
			a.appendText("PROFIT <> AD - Citrix naam in PROFIT bestaat niet in de AD : " + count + "\n");
			a.appendText(found);
			System.out.println(count);
			
			Layout.bRule3.setText(Layout.bRule3.getText() + ": " + count);
			
			
			
		}
		catch(SQLException e){
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 3 \n");
			a.appendText(e.getMessage() + "\n \n");
			
		}
		
		
		//Business Rule 4:
		//Medewerker uit dienst in profit, account is in AD actief
		try {
			
			//Layout.r.setWidth(500);
			
			Statement stat = conn.createStatement();
			String query = "SELECT * " + 
					"FROM [AuditBlackBox].[dbo].[AfasProfit-Export] PR " + 
					"WHERE PR.ContractEndDate < GETDATE() AND PR.EmployeeUsername " + 
					"IN (SELECT A.Username_Pre2000 " + 
					"FROM [AD-Export] A)";  
					
			
			ResultSet res = stat.executeQuery(query);
			
			int count = 0;
			
			

			String found = "";
			while(res.next()) {
				found = found + "	Medewerker " + res.getString("EmployeeUsername") + " is uit diesnst maar actief in AD\n";
				System.out.println(res.getString("EmployeeUsername"));
				count++;
			}
			a.appendText("PROFIT <> AD - : Medewerker uit dienst in profit, account is AD actief" + count + "\n");
			a.appendText(found);
			System.out.println(count);
			
			
			Layout.bRule4.setText(Layout.bRule4.getText() + ": " + count);
			//Layout.bRule4.setTextFill(Color.RED);
			
			
			
		}
		catch(SQLException e){
			a.appendText("Er is een error gevonden is het berekenen van Business Rule 4 \n");
			a.appendText(e.getMessage() + "\n \n");
			
		}
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
}
