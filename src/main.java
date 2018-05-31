import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.sun.corba.se.pept.transport.EventHandler;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class main {

	public static Connection conn;
	public static String[] launch;
	
	public static void main(String[] args) throws ClassNotFoundException {
		
		try{
			
			launch = args;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		      String connectionString = "jdbc:sqlserver://localhost:1433;DatabaseName=AuditBlackBox";
		      conn = DriverManager.getConnection(connectionString,"testAccount1","Welkom01!");
		      System.out.println("Verbinding gemaakt");
		      launchProgram(launch);
		}
		  		      
		      catch(SQLException e){
		            System.out.println("Foutmelding:" + e.getMessage());
		      
		      }
		
	}
		
		public static void launchProgram(String[] args){
			Application.launch(Layout.class, args);
		}

		
	}
	


	
	

