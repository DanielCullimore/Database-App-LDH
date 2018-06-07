/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Rowan
 */
public class Gebruiker {
    private String username;
    private int userID;
    private int aantalOvertredingen;
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public void setUserID(int userID)
    {
        this.userID = userID;
    }
    
    public void setAantalOvertredingen(int aantalOvertedingen)
    {
        this.aantalOvertredingen = aantalOvertedingen;
    }
}
