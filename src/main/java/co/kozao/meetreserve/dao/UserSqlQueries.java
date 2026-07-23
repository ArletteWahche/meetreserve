package co.kozao.meetreserve.dao;

public class UserSqlQueries {

	public UserSqlQueries() {
		// empêche l'instanciation, classe utilitaire
	}

	public static final String SQL_FIND_BY_EMAIL =
			"SELECT id_user, name, surname, email, password, role " +
			"FROM users " +
			"WHERE email = ?";

	public static final String SQL_EXISTS_BY_EMAIL =
			"SELECT 1 FROM users WHERE email = ?";

	public static final String SQL_INSERT_USER =
			"INSERT INTO users (name, surname, email, password, role) " +
			"VALUES (?, ?, ?, ?, ?)";
}