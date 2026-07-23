package co.kozao.meetreserve.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import co.kozao.meetreserve.model.User;
import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.util.DatabaseConnection;

public class UserDao {

	private static final Logger logger = Logger.getLogger(UserDao.class.getName());

	public User findByEmail(String email) {
		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(UserSqlQueries.SQL_FIND_BY_EMAIL)) {

			ps.setString(1, email);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					User u = new User();
					u.setId(rs.getLong("id_user"));
					u.setName(rs.getString("name"));
					u.setSurname(rs.getString("surname"));
					u.setEmail(rs.getString("email"));
					u.setPassword(rs.getString("password"));
					u.setRole(Role.valueOf(rs.getString("role")));
					return u;
				}
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Erreur lors de la récupération de l'utilisateur", e);
		}
		return null;
	}

	public boolean existsByEmail(String email) {
		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(UserSqlQueries.SQL_EXISTS_BY_EMAIL)) {

			ps.setString(1, email);

			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Erreur lors de la vérification de l'email", e);
		}
		return false;
	}

	public boolean insert(User user) {
		try (Connection conn = DatabaseConnection.getConnection();
			 PreparedStatement ps = conn.prepareStatement(UserSqlQueries.SQL_INSERT_USER)) {

			ps.setString(1, user.getName());
			ps.setString(2, user.getSurname());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getRole().name());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Erreur lors de l'insertion de l'utilisateur", e);
		}
		return false;
	}
	
}