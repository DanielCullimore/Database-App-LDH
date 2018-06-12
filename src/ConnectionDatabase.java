/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * @author Daniel Mensche
 * Deze class zet de connetion van de database op een manier zodat je niet twee keer tegelijkertijd kan inloggen
 **/
// Import connection classes
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionDatabase {
	private static String url;
	private static String username;
	private static String password;
	private static Connection conn;

	private static String singleUserLock;

        //Maak verbinding met database met de string uit het bestand connection.txt
	public ConnectionDatabase() throws IOException {
//       String f = ConnectionDatabase.class.getResource("connection.txt").getPath();

		InputStream is = new FileInputStream("src/connection.txt");
		BufferedReader buf = new BufferedReader(new InputStreamReader(is));
		this.url = buf.readLine();
		this.username = buf.readLine();
		this.password = buf.readLine();

	}

      
        
	public Connection getConnection() {
		try {

			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Verbinding gemaakt");

		} catch (SQLException e) {
			System.out.println("Foutmelding:" + e.getMessage());
		}
		return conn;
	}

        //Wanner de connetion wordt gesloten, wordtd ook de tabel LoginInfo verwijderd
	public void closeConnection(Connection mConn) throws SQLException {

		try {
			conn = mConn;
		}

		finally {
			conn.close();
			System.out.println("Verbinding afgesloten");

			java.sql.Statement stat = conn.createStatement();
			String query = "DROP TABLE LoginInfo;";
			stat.execute(query);
		}
	}

        //De check wordt gemaakt door te kijken of de tabel LoginInfo al bestaat, als dat zo is, wordt de connectie hier gestopt.
	public String singleUserConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Verbinding gemaakt Signaal");

			DatabaseMetaData dbm = conn.getMetaData();
			ResultSet tables = dbm.getTables(null, null, "LoginInfo", null);

			if (tables.next()) {
				singleUserLock = "Access Denied"; 
			} else {
				java.sql.Statement stat = conn.createStatement();
				String query = "CREATE TABLE LoginInfo (Status int);";
				stat.execute(query);
				query = "INSERT INTO LoginInfo(Status) VALUES (1);";
				stat.execute(query);
				singleUserLock = "Access Granted";
			}

		} catch (SQLException e) {
			System.out.println("Foutmelding:" + e.getMessage());
		}

		return singleUserLock;
	}
}
