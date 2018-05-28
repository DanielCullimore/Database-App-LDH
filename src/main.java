import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import com.microsoft.sqlserver.jdbc.*;

public class main {

	public static void main(String[] args) throws ClassNotFoundException {
		
		try{
			 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		      String connectionString = "jdbc:sqlserver://localhost:1433;DatabaseName=AuditBlackBox";
		      Connection conn = DriverManager.getConnection(connectionString,"testAccount1","Welkom01!");
		      System.out.println("Verbinding gemaakt");
		       
		      try{
		          Statement stat = conn.createStatement();
		          String query = "SELECT ID FROM Persoon;";
		          ResultSet res = stat.executeQuery(query);
		          
		          while(res.next()){
		            System.out.println(res.getRow() + ": ");
		            System.out.println(res.getString("ID") + ": ");
		          }
		      }
		      
		       finally{
		            conn.close();
		            System.out.println("Verbinding afgesloten");
		               
		               }   
		      }
		      
		      catch(SQLException e){
		            System.out.println("Foutmelding:" + e.getMessage());
		      
		      }
	}
}
