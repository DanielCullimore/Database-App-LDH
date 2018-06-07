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
    private String userID;
    private String aantalOvertredingen;
    
    
    public Gebruiker(String username, String userID, String aantalOvertredingen) {
		super();
		this.username = username;
		this.userID = userID;
		this.aantalOvertredingen = aantalOvertredingen;
	}


	public String getUsername() {
		return username;
	}


	public String getUserID() {
		return userID;
	}


	public String getAantalOvertredingen() {
		return aantalOvertredingen;
	}


}
