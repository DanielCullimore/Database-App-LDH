import java.sql.*;

public class action {
	
	
	public static void Load() {
		
		Connection conn = main.conn;
		
	      try{
	          Statement stat = conn.createStatement();
	          String query = "SELECT ID FROM Persoon;";
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
}
