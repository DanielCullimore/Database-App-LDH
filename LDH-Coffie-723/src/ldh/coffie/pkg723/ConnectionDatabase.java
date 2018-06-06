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
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ConnectionDatabase {
    private static String url = "jdbc:sqlserver://localhost:1433;databaseName=AuditBlackBox";    
    private static String username = "TestDB";   
    private static String password = "1234567";
    private static Connection conn;
    
    private static String signalUrl = "jdbc:sqlserver://localhost:1433;databaseName=TestConnectionDatabase";    
    private static String signalUsername = "TestDB";   
    private static String signalPassword = "1234567";
    private static Connection signalConn;
    
    private static String singleUserLock;
        
    public ConnectionDatabase(){
    }
   
    public static Connection getConnection() {
    try{
        
      conn = DriverManager.getConnection(url,username,password);
      System.out.println("Verbinding gemaakt");
       
       }
      catch(SQLException e){
           System.out.println("Foutmelding:" + e.getMessage());
      }
    return conn;
    }
    
    public void closeConnection(Connection mConn) throws SQLException {  
        
        try{ 
            conn = mConn;
            }

       finally{
            conn.close();
            System.out.println( "Verbinding afgesloten");
            
            java.sql.Statement stat = signalConn.createStatement();
            String query = "DROP TABLE LoginInfo;";
            stat.execute(query);
               }
  }
    
  public String singleUserConnection(){
    try{
        signalConn = DriverManager.getConnection(signalUrl,signalUsername,signalPassword);
        System.out.println("Verbinding gemaakt Signaal");
         
        DatabaseMetaData dbm = signalConn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "LoginInfo", null);    
        
        if (tables.next()) {
            singleUserLock = "Access Denied"; // Table exists
        }
        else {
            java.sql.Statement stat = signalConn.createStatement();
           // String query = "CREATE TABLE LoginInfo (Status varchar(20));";
            String query = "CREATE TABLE LoginInfo (Status int);";
            stat.execute(query);
            query = "INSERT INTO LoginInfo(Status) VALUES (1);";
            stat.execute(query);
            singleUserLock = "Access Granted";
        }
       
            
    } catch(SQLException e){
           System.out.println("Foutmelding:" + e.getMessage());
      }
    
    return singleUserLock;
}  
}
