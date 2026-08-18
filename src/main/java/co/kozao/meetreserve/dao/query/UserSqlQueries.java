package co.kozao.meetreserve.dao.query;

public class UserSqlQueries {

	public UserSqlQueries() {
		// empêche l'instanciation, classe utilitaire
	}

	public static final String SQL_INSERT_USER =
			"INSERT INTO users (name, surname, email, password, role, manager_id) " +
			"VALUES (?, ?, ?, ?, ?, ?)";
	
	public static final String SQL_UPDATE_USER =
	        "UPDATE users SET name = ?, surname = ?, role = ?, manager_id = ? WHERE email = ?";

	public static final String SQL_DELETE_USER =
	        "DELETE FROM users WHERE email = ?";
	
	public static final String SQL_FIND_BY_EMAIL =
			"SELECT id_user, name, surname, email, password, role, manager_id " +
			"FROM users " +
			"WHERE email = ?";

	public static final String SQL_EXISTS_BY_EMAIL =
			"SELECT 1 FROM users WHERE email = ?";

	public static final String SQL_FIND_ALL =
			"SELECT id_user, name, surname, email, password, role, manager_id " +
			"FROM users";
	
	public static final String SQL_FIND_BY_MANAGER_ID =
	        "SELECT id_user, name, surname, email, password, role, manager_id " +
	        "FROM users " +
	        "WHERE manager_id = ?";
	
}

	

