import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javafx.application.Application;

public class main {

	public static Connection conn;
	public static String[] launch;

	public static void main(String[] args) throws ClassNotFoundException, IOException {

		try {

			InputStream is = new FileInputStream("src/connection.txt");
			BufferedReader buf = new BufferedReader(new InputStreamReader(is));
			String URL = buf.readLine();

			launch = args;
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(URL, "testAccount1", "Welkom01!");
			System.out.println("Verbinding gemaakt");
			launchProgram(launch);
		}

		catch (SQLException e) {
			e.printStackTrace();

		}

	}

	public static void launchProgram(String[] args) {
		Application.launch(Layout.class, args);
	}

}
