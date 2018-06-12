
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author megas
 */
public class cvsWriter {

	static Alert dialog;

	/**
	 * In deze methode wordt het bestand gekozen war naaro wordt geschrevne, en waar
	 * die op te slaan, en worden alle losse schrijf methodes aangeschreven
	 */
	public static void write(Stage primaryStage) {
		String filePath = ".\\Desktop\\export.txt";

		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(primaryStage);

		Connection conn = main.conn;
		System.out.println("verbinding gemaakt...");

		Query q = new Query();
		q.setEenheid();

		writeQ1ToCsv(conn, file, filePath, q);
		writeQ2ToCsv(conn, file, filePath, q);
		writeQ3ToCsv(conn, file, filePath, q);
		writeQ4ToCsv(conn, file, filePath, q);
		writeQ5ToCsv(conn, file, filePath, q);
		writeQ6ToCsv(conn, file, filePath, q);
		writeQ7ToCsv(conn, file, filePath, q);
		writeQ8ToCsv(conn, file, filePath, q);
		writeQ9ToCsv(conn, file, filePath, q);
		writeQ10ToCsv(conn, file, filePath, q);

		dialog = new Alert(Alert.AlertType.INFORMATION);
		dialog.setHeaderText(null);
		dialog.setGraphic(null);
		dialog.setTitle("Business Rules overgeschreven");
		dialog.initOwner(primaryStage);
		dialog.initStyle(StageStyle.UTILITY);
		StackPane done = new StackPane();
		Label donel = new Label("De business rules zijn overgeschreven.");
		done.getChildren().add(donel);
		dialog.getDialogPane().setContent(done);
		dialog.showAndWait();

	}

	/*
	 * Hier onder staan 10 methodes voor elke business rules, en hun eigen syntax,
	 * en hoe ze moeten worden overgeschreven naar een los bestand
	 */

	public static void writeQ1ToCsv(Connection conn, File export, String filePath, Query q) {

		String found;
		String useQuery = q.getQuery1();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(export);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("AD Account onbekend in Profit,");
				while (res.next()) {
					found = res.getString("username_pre2000") + ",\n";
					pw.println(found);
				}

				pw.flush();
				pw.close();

			} catch (Exception E) {
				ShowError();

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void writeQ2ToCsv(Connection conn, File export, String filePath, Query q) {

		String found;
		String useQuery = q.getQuery2();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(export);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("Medewerker uit dienst in Profit account is in AD actief,");
				while (res.next()) {
					found = res.getString("EmployeeUsername") + ",\n";
					pw.println(found);
				}

				pw.flush();
				pw.close();

			} catch (Exception E) {
				ShowError();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void writeQ3ToCsv(Connection conn, File export, String filePath, Query q) {

		String found;
		String useQuery = q.getQuery3();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(export);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("RDS User naam in Profit bestaat niet in de AD,");
				while (res.next()) {
					found = res.getString("EmployeeUsername") + ",\n";
					pw.println(found);
				}

				pw.flush();
				pw.close();

			} catch (Exception E) {
				ShowError();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void writeQ4ToCsv(Connection conn, File export, String filePath, Query q) {

		String found;
		String useQuery = q.getQuery4();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(export);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("RDS naam in Clevernew is niet ingevuld,");
				while (res.next()) {
					found = res.getString("Code") + ",\n";
					pw.println(found);
				}

				pw.flush();
				pw.close();

			} catch (Exception E) {
				ShowError();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void writeQ5ToCsv(Connection conn, File export, String filePath, Query q) {

		String found;
		String useQuery = q.getQuery5();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(export);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("RDS naam in CleverNew bestaat niet in AD,");
				while (res.next()) {
					found = res.getString("Code") + ",\n";
					pw.println(found);
				}

				pw.flush();

			} catch (Exception E) {
				ShowError();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void writeQ6ToCsv(Connection conn, File export, String filePath, Query q) {

		String found;
		String useQuery = q.getQuery6();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(export);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("Medewerker uit dienst in CleverNew account in AD actief,");
				while (res.next()) {
					found = res.getString("Code") + ",\n";
					pw.println(found);
				}

				pw.flush();
				pw.close();

			} catch (Exception E) {
				ShowError();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void writeQ7ToCsv(Connection conn, File export, String filePath, Query q) {

		String found;
		String useQuery = q.getQuery7();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(export);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("AD Account onbekend in Clever,");
				while (res.next()) {
					found = res.getString("Username_Pre2000") + ",\n";
					pw.println(found);
				}

				pw.flush();
				pw.close();

			} catch (Exception E) {
				ShowError();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void writeQ8ToCsv(Connection conn, File export, String filePath, Query q) {

		String found;
		String useQuery = q.getQuery8();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(export);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("AD Account onbekend in Clever,");
				while (res.next()) {
					found = res.getString("EmployeeUsername") + ",\n";
					pw.println(found);
				}

				pw.flush();
				pw.close();

			} catch (Exception E) {
				ShowError();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void writeQ9ToCsv(Connection conn, File export, String filePath, Query q) {

		String found;
		String useQuery = q.getQuery9();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(export);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("Medewerker uit dienst in Profit account is in Clever actief,");
				while (res.next()) {
					found = res.getString("EmployeeUsername") + ",\n";
					pw.println(found);
				}

				pw.flush();
				pw.close();

			} catch (Exception E) {
				ShowError();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void writeQ10ToCsv(Connection conn, File export, String filePath, Query q) {

		String found;
		String useQuery = q.getQuery10();

		try {
			Statement stat = conn.createStatement();
			ResultSet res = stat.executeQuery(useQuery);

			try {
				FileWriter fw = new FileWriter(export);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pw = new PrintWriter(bw);

				pw.println("RDS User naam in Clever bestaat niet in Afas Profit,");
				while (res.next()) {
					found = res.getString("Code") + ",\n";
					pw.println(found);
				}

				pw.flush();
				pw.close();

			} catch (Exception E) {
				ShowError();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void ShowError() {
		dialog.setAlertType(AlertType.ERROR);
		dialog.setHeaderText(null);
		dialog.setGraphic(null);
		dialog.setTitle("Error bij overschrijven");
		dialog.initOwner(Layout.window);
		dialog.initStyle(StageStyle.UTILITY);
		StackPane done = new StackPane();
		Label donel = new Label("Er ging is fout bij het overschrijven naar het bestand.");
		done.getChildren().add(donel);
		dialog.getDialogPane().setContent(done);
		dialog.showAndWait();
	}

}
