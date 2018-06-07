/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.coffie.pkg723;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Daniel Mensche
 */
public class LDHCoffie723 {

    /**
     * @param args the command line arguments
     */
    
    static ConnectionDatabase connDb;
    static Connection conn;
    static QueriesSelection qStatments;
    static MultiAccessRestriction access;
    static String statusProgram;
    static String singleUserStatus;
    static InetAddress ip;
    
    
    static String serverNaam;
    static String connectionString;
    static String databaseNaam;
    static String userConnectie;
    static Date date;
    static long time;
    static String timestamp;
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
    
        
    
    
    connDb = new ConnectionDatabase();        
    qStatments = new QueriesSelection();
    access = new MultiAccessRestriction();
    statusProgram = "Running";// access.restrictionControl();
    singleUserStatus = connDb.singleUserConnection();
    
    
    if(statusProgram.equals("Running")){
        
        if (singleUserStatus.equals("Access Granted")){

            conn = connDb.getConnection();
            connectionString();
            connDb.closeConnection(conn);
        } else if (singleUserStatus.equals("Access Denied")){
            System.out.println("Only one user can access the program at the time");
        }
    }
    else if (statusProgram.equals("Deny Instance")){
        System.out.println("Only 1 instance can run");
    }
    else if (statusProgram.equals("Access Deny")){
        System.out.println("Could not start program, try again later");
    }
    
    
}
    
public static void connectionString() throws SQLException, UnknownHostException{
         date= new Date();
         time = date.getTime();
         Timestamp ts = new Timestamp(time);
         timestamp = ts.toString();
 
    serverNaam="";
    databaseNaam ="";
    userConnectie = "";
    connectionString ="";
    
    DatabaseMetaData dbm =conn.getMetaData();
    String url = dbm.getURL();
    String dbFindName = "databaseName=";
   
    for (int i= url.indexOf(dbFindName)+13; i < url.indexOf(";", url.indexOf(dbFindName)); i++){
         databaseNaam = databaseNaam + url.charAt(i);
         }
            
        ip = InetAddress.getLocalHost();
        serverNaam = ip.getHostName();
                 
    String usrNm = dbm.getUserName();
     
    for (int i= usrNm.lastIndexOf("\\") + 1; i < usrNm.length(); i++){
         userConnectie = userConnectie + usrNm.charAt(i);
         }
                        
        connectionString = serverNaam + "/" + databaseNaam +", "+ userConnectie +", "+ timestamp;
        System.out.println(connectionString);
            

}

}