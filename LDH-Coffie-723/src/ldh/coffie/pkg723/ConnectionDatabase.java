/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.coffie.pkg723;

/**
 * @author Daniel Mensche
 **/
// Import connection classes
import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConnectionDatabase {
    private static String url = "jdbc:sqlserver://localhost:1433;databaseName=AuditBlackBox";    
    private static String username = "TestDB";   
    private static String password = "1234567";
    private static Connection conn;
    
    public ConnectionDatabase(){
    }
   
    public static Connection getConnection() {
    try{
      
      conn = DriverManager.getConnection(url,username,password);
      System.out.println("Verbinding gemaakt");
       
       try{
          java.sql.Statement stat = conn.createStatement();
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
    return conn;
    }
}
