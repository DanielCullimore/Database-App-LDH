/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.coffie.pkg723;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Danny
 */
public class LDHCoffie723 {

    /**
     * @param args the command line arguments
     */
    
    static ConnectionDatabase connDb;
    static Connection conn;
    public static void main(String[] args) {
    connDb = new ConnectionDatabase();        
   
    conn = connDb.getConnection();
   
}
}