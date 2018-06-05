/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.coffie.pkg723;

import java.sql.Connection;
import java.sql.SQLException;

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
    
    public static void main(String[] args) throws SQLException {
    
    connDb = new ConnectionDatabase();        
    qStatments = new QueriesSelection();
    access = new MultiAccessRestriction();
    statusProgram = access.restrictionControl();
    
    if(statusProgram.equals("Running")){
    conn = connDb.getConnection();
    qStatments.getIDPersoon(conn);
    connDb.closeConnection(conn);
    }
    else if (statusProgram.equals("Deny Instance")){
        System.out.println("Only 1 instance can run");
    }
    else if (statusProgram.equals("Access Deny")){
        System.out.println("Could not start program, try again later");
    }
    
    
}
}