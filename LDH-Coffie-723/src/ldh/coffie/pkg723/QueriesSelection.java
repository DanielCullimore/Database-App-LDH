/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ldh.coffie.pkg723;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Daniel Mensche
 */
public class QueriesSelection {
      private Connection conn;    
    
      public QueriesSelection(){
      }
    
    public void getIDPersoon(Connection mConn) throws SQLException{
            conn = mConn;
            java.sql.Statement stat = conn.createStatement();
            String query = "SELECT ID FROM Persoon;";
            ResultSet res = stat.executeQuery(query);
          
            while(res.next()){
            System.out.println(res.getRow() + ": ");
            System.out.println(res.getString("ID") + ": ");
          }
    }
}
