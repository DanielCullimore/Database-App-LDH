
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.application.Application;

/**
 * @author Daniel Mensche
 */
public class main {

	/**
	 * @param args
	 *            the command line arguments
	 */

	static ConnectionDatabase connDb;
	static Connection conn;
	static MultiAccessRestriction access;
	static String statusProgram;
	static String singleUserStatus;

	public static void main(String[] args) throws SQLException, IOException {

		connDb = new ConnectionDatabase();
		access = new MultiAccessRestriction();
		statusProgram = "Running";
		singleUserStatus = connDb.singleUserConnection();

		if (statusProgram.equals("Running")) {

			if (singleUserStatus.equals("Access Granted")) {

				conn = connDb.getConnection();
				launchProgram(args);

			} else if (singleUserStatus.equals("Access Denied")) {
				System.out.println("Only one user can access the program at the time");
			}
		} else if (statusProgram.equals("Deny Instance")) {
			System.out.println("Only 1 instance can run");
		} else if (statusProgram.equals("Access Deny")) {
			System.out.println("Could not start program, try again later");
		}

	}

	public static void launchProgram(String[] args) {
		Application.launch(Layout.class, args);
	}
}