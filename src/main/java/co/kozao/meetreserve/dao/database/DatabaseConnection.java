package co.kozao.meetreserve.dao.database;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

	private static final String URL =  "jdbc:postgresql://localhost:5434/Kozao_db?ssl=false&connectTimeout=5";
	private static final String USER = "postgres";
	private static final String PASSWORD = "vectordb";

	private static Connection connection;

	private DatabaseConnection() {
		// empêche l'instanciation, classe utilitaire (Singleton)
	}

	private static Connection getConnection() throws SQLException {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			return connection;
		} catch (ClassNotFoundException e) {
			throw new SQLException("Driver PostgreSQL introuvable", e);
		}
	}

	public static Connection getInstance() throws SQLException{
		if(connection == null || connection.isClosed()){
			connection = getConnection();
			return connection;
		}
		return connection;
	}
}
