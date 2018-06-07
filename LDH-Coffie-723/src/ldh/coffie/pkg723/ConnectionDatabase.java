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
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.InetAddress;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class ConnectionDatabase {
    private static String url = "jdbc:sqlserver://localhost:1433;integratedSecurity=true;databasename=AuditBlackBox";   
    private static Connection conn;
    
    private static String signalUrl = "jdbc:sqlserver://localhost:1433;integratedSecurity=true; databaseName=TestConnectionDatabase"; 
    private static Connection signalConn;
    
    private static String singleUserLock;
        
    public ConnectionDatabase(){
    }
   
    public static Connection getConnection() throws ClassNotFoundException, IOException {
    try{
    
       
      Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
      conn = DriverManager.getConnection(url);
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
    
  public String singleUserConnection() throws IOException{
    try{
       locateFileInFolder();
        signalConn = DriverManager.getConnection(signalUrl);
        System.out.println("Verbinding gemaakt Signaal");
         
        DatabaseMetaData dbm = signalConn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "LoginInfo", null);    
        
        if (tables.next()) {
            singleUserLock = "Access Denied"; // Table exists
        }
        else {
            java.sql.Statement stat = signalConn.createStatement();
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
 
 private static void locateFileInFolder(){

   //  System.out.println(arguments);
     
     File f = new File("scr//file//sqljdbc_auth.dll");
     String f2=f.getAbsolutePath();
     System.out.println("Path:"+f2);
     
     System.setProperty("java.library.path", f2);
     
     List<String> arguments;
     RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
     System.out.println(""+runtimeMxBean.getInputArguments());
 }
  
 

}
