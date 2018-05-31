import java.sql.*;
import java.util.ArrayList;

import DataEntries.ProfitEntry;

public class action {
	
	
	public static void LoadProfit() {
		
		Connection conn = main.conn;
		
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
		Connection conn = main.conn;
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
	
	
	
	
	
}
