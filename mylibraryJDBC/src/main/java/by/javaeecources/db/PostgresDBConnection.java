package by.javaeecources.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class PostgresDBConnection {

	static Properties prop = null;
	static Connection conn = null;
	static String database = null;
	static String driver = null;
	static String url = null;
	static String user = null;
	static String password = null;

	public static Connection getConnection() {


		try {

			if (prop == null) {
				prop = new Properties();
				prop.load(PostgresDBConnection.class.getClassLoader().getResourceAsStream("db.properties"));

				database = prop.getProperty("db.databaseName");
				driver = prop.getProperty("db.driverClassName");
				url = prop.getProperty("db.url") + database;
				user = prop.getProperty("db.username");
				password = prop.getProperty("db.password");

				System.out.println("The URL is : " + url);

			}
			Class.forName(driver);
			conn = DriverManager.getConnection(url, user, password);

		} catch (Exception e) {
			System.err.println("Exception occured : SQLException : " + e.getMessage());
			e.printStackTrace();
		}

		return conn;
	}

	public static void closeConnection(Connection conn) {
		try {
			if (null != conn) {
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public static void closeResultset(ResultSet rs) {
		try {
			if (null != rs) {
				rs.close();
				rs = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public static void closePreparedStatement(PreparedStatement pstmt) {
		try {
			if (null != pstmt) {
				pstmt.close();
				pstmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public static void closeStatement(Statement stmt) {
		try {
			if (null != stmt) {
				stmt.close();
				stmt = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();

		}
	}

	public static String getProperty(String property) {
		return prop.getProperty(property);
	}

	public static void main(String args[]) {
		PostgresDBConnection.getConnection();
	}

}
