package co.kozao.meetreserve.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:postgresql://localhost:5432/meetreserve";
    private static final String USER = "postgres";
    private static final String PASSWORD = "arlette";
    
    
    public static Connection getConnection() {
    	try {
    		return DriverManager.getConnection(URL, USER, PASSWORD);
    	} catch(SQLException e){
    		System.out.println("Error of connection PostgreSQL :" + e.getMessage());
    		throw new RuntimeException("Impossible to connect to the database", e);
    	}
    }
}
