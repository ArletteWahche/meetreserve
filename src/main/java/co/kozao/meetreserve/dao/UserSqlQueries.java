package co.kozao.meetreserve.dao;

public class UserSqlQueries {
	
	private UserSqlQueries() {
        // empêche l'instanciation, classe utilitaire
    }
	
	public static final String SQL_FIND_BY_EMAIL_AND_PASSWORD = "SELECT id_user, name, surname, email, password, role " + 
			"FROM user" +
			"WHERE email = ? and password = ?";
	;
	
	public static final String SQL_INSERT_USER =
	        "INSERT INTO user (name, surname, email, password, role) " +
	        "VALUES (?, ?, ?, ?, ?)";
	
	
}
