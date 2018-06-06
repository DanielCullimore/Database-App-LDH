
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.stage.FileChooser;

/**
 *
 * @author megas
 */
public class cvsWriter {

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void write() {		
		String filePath = ".\\Desktop\\export.txt";
		File export = new File(filePath);

		try {
			String connectionString = "jdbc:sqlserver://localhost:1433;databaseName=AuditBlackBox";
			Connection conn = DriverManager.getConnection(connectionString, "testAccount1", "Welkom01!");
			System.out.println("verbinding gemaakt...");

			writeQ1ToCsv(conn, export, filePath);
			writeQ2ToCsv(conn, export, filePath);
			writeQ3ToCsv(conn, export, filePath);
			writeQ4ToCsv(conn, export, filePath);
			writeQ5ToCsv(conn, export, filePath);
			writeQ6ToCsv(conn, export, filePath);
			writeQ7ToCsv(conn, export, filePath);
			writeQ8ToCsv(conn, export, filePath);
			writeQ9ToCsv(conn, export, filePath);
			writeQ10ToCsv(conn, export, filePath);

		} catch (SQLException E) {
			System.out.println("Foutmelding: " + E.getMessage());
		}
	}

	public static void writeQ1ToCsv(Connection conn, File export, String filePath) {

		String found;
		Query query = new Query();
		String useQuery = query.getQuery1();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(filePath, false);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("AD Account onbekend in Profit,");
				while (res.next()) {
					found = res.getString("username_pre2000") + ",\n";
					pw.println(found);
					System.out.println(res.getString("username_pre2000"));
				}

				pw.flush();
				pw.close();
//				JOptionPane.showMessageDialog(null, "Record saved");

			} catch (Exception E) {
//				JOptionPane.showMessageDialog(null, "Record not saved" + E);
			}

		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Er is een error gevonden\n" + e.getMessage());
		}
	}

	public static void writeQ2ToCsv(Connection conn, File export, String filePath) {

		String found;
		Query query = new Query();
		String useQuery = query.getQuery2();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(filePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("Medewerker uit dienst in Profit account is in AD actief,");
				while (res.next()) {
					found = res.getString("EmployeeUsername") + ",\n";
					pw.println(found);
					System.out.println(res.getString("EmployeeUsername"));
				}

				pw.flush();
				pw.close();
//				JOptionPane.showMessageDialog(null, "Record saved");

			} catch (Exception E) {
//				JOptionPane.showMessageDialog(null, "Record not saved" + E);
			}

		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Er is een error gevonden\n" + e.getMessage());
		}
	}

	public static void writeQ3ToCsv(Connection conn, File export, String filePath) {

		String found;
		Query query = new Query();
		String useQuery = query.getQuery3();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(filePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("RDS User naam in Profit bestaat niet in de AD,");
				while (res.next()) {
					found = res.getString("EmployeeUsername") + ",\n";
					pw.println(found);
					System.out.println(res.getString("EmployeeUsername"));
				}

				pw.flush();
				pw.close();
//				JOptionPane.showMessageDialog(null, "Record saved");

			} catch (Exception E) {
//				JOptionPane.showMessageDialog(null, "Record not saved" + E);
			}

		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Er is een error gevonden\n" + e.getMessage());
		}
	}

	public static void writeQ4ToCsv(Connection conn, File export, String filePath) {

		String found;
		Query query = new Query();
		String useQuery = query.getQuery4();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(filePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("RDS naam in Clevernew is niet ingevuld,");
				while (res.next()) {
					found = res.getString("Code") + ",\n";
					pw.println(found);
					System.out.println(res.getString("Code"));
				}

				pw.flush();
				pw.close();
//				JOptionPane.showMessageDialog(null, "Record saved");

			} catch (Exception E) {
//				JOptionPane.showMessageDialog(null, "Record not saved" + E);
			}

		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Er is een error gevonden\n" + e.getMessage());
		}
	}

	public static void writeQ5ToCsv(Connection conn, File export, String filePath) {

		String found;
		Query query = new Query();
		String useQuery = query.getQuery5();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(filePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("RDS naam in CleverNew bestaat niet in AD,");
				while (res.next()) {
					found = res.getString("Code") + ",\n";
					pw.println(found);
					System.out.println(res.getString("Code"));
				}

				pw.flush();
				pw.close();
//				JOptionPane.showMessageDialog(null, "Record saved");

			} catch (Exception E) {
//				JOptionPane.showMessageDialog(null, "Record not saved" + E);
			}

		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Er is een error gevonden\n" + e.getMessage());
		}
	}

	public static void writeQ6ToCsv(Connection conn, File export, String filePath) {

		String found;
		Query query = new Query();
		String useQuery = query.getQuery6();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(filePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("Medewerker uit dienst in CleverNew account in AD actief,");
				while (res.next()) {
					found = res.getString("Code") + ",\n";
					pw.println(found);
					System.out.println(res.getString("Code"));
				}

				pw.flush();
				pw.close();
//				JOptionPane.showMessageDialog(null, "Record saved");

			} catch (Exception E) {
//				JOptionPane.showMessageDialog(null, "Record not saved" + E);
			}

		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Er is een error gevonden\n" + e.getMessage());
		}
	}

	public static void writeQ7ToCsv(Connection conn, File export, String filePath) {

		String found;
		Query query = new Query();
		String useQuery = query.getQuery7();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(filePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("AD Account onbekend in Clever,");
				while (res.next()) {
					found = res.getString("Username_Pre2000") + ",\n";
					pw.println(found);
					System.out.println(res.getString("Username_Pre2000"));
				}

				pw.flush();
				pw.close();
//				JOptionPane.showMessageDialog(null, "Record saved");

			} catch (Exception E) {
//				JOptionPane.showMessageDialog(null, "Record not saved" + E);
			}

		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Er is een error gevonden\n" + e.getMessage());
		}
	}

	public static void writeQ8ToCsv(Connection conn, File export, String filePath) {

		String found;
		Query query = new Query();
		String useQuery = query.getQuery8();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(filePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("AD Account onbekend in Clever,");
				while (res.next()) {
					found = res.getString("EmployeeUsername") + ",\n";
					pw.println(found);
					System.out.println(res.getString("EmployeeUsername"));
				}

				pw.flush();
				pw.close();
//				JOptionPane.showMessageDialog(null, "Record saved");

			} catch (Exception E) {
//				JOptionPane.showMessageDialog(null, "Record not saved" + E);
			}

		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Er is een error gevonden\n" + e.getMessage());
		}
	}

	public static void writeQ9ToCsv(Connection conn, File export, String filePath) {

		String found;
		Query query = new Query();
		String useQuery = query.getQuery9();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(filePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("Medewerker uit dienst in Profit account is in Clever actief,");
				while (res.next()) {
					found = res.getString("EmployeeUsername") + ",\n";
					pw.println(found);
					System.out.println(res.getString("EmployeeUsername"));
				}

				pw.flush();
				pw.close();
//				JOptionPane.showMessageDialog(null, "Record saved");

			} catch (Exception E) {
//				JOptionPane.showMessageDialog(null, "Record not saved" + E);
			}

		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Er is een error gevonden\n" + e.getMessage());
		}
	}

	public static void writeQ10ToCsv(Connection conn, File export, String filePath) {

		String found;
		Query query = new Query();
		String useQuery = query.getQuery10();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(filePath, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("RDS User naam in Clever bestaat niet in Afas Profit,");
				while (res.next()) {
					found = res.getString("Code") + ",\n";
					pw.println(found);
					System.out.println(res.getString("Code"));
				}

				pw.flush();
				pw.close();
//				JOptionPane.showMessageDialog(null, "Record saved");

			} catch (Exception E) {
//				JOptionPane.showMessageDialog(null, "Record not saved" + E);
			}

		} catch (SQLException e) {
//			JOptionPane.showMessageDialog(null, "Er is een error gevonden\
		}
	}

}
