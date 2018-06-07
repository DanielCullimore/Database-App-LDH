/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Rowan
 */
public class ImpactGebruiker {
    private static ArrayList<Gebruiker> signaal = new ArrayList<>();
    
    public static void laadImpactSignalen(Connection con)
    {
        try
        {
			Statement stat = con.createStatement();
            ResultSet rs = stat.executeQuery("SELECT AD.username_pre2000, PC.PersoonID  \n" +
                                                       "FROM [AuditBlackBox].[dbo].[AD-Export] AD join [AuditBlackBox].[dbo].[PersoonCodes] PC "
                                                                + " ON AD.Username_pre2000 = PC.Code \n" +
                                                       "WHERE AD.username_pre2000 not in \n" +
                                                       "(select EmployeeUsername FROM [AuditBlackBox].[dbo].[AfasProfit-Export] \n)");
            while(rs.next())
            {   Gebruiker gebruiker = new Gebruiker();
                gebruiker.setUsername(rs.getString("AD.username_pre2000"));
                gebruiker.setUserID(rs.getInt("PC.PersoonID"));
                signaal.add(gebruiker);
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
    
    public static ArrayList<Gebruiker> getSignaal()
    {
        return signaal;
    }
}
