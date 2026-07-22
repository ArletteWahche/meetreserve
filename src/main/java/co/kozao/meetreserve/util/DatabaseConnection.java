package co.kozao.meetreserve.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String URL = "jdbc:postgresql://localhost:5432/meetreserve";
	private static final String USER = "postgres";
	private static final String PASSWORD = "arlette";

	private static Connection connection;

	private DatabaseConnection() {
		// empêche l'instanciation, classe utilitaire (Singleton)
	}

	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			try {
				Class.forName("org.postgresql.Driver");
				connection = DriverManager.getConnection(URL, USER, PASSWORD);
			} catch (ClassNotFoundException e) {
				throw new SQLException("Driver PostgreSQL introuvable", e);
			}
		}
		return connection;
	}
}
