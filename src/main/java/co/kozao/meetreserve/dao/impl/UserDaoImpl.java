package co.kozao.meetreserve.dao.impl;

import co.kozao.meetreserve.dao.database.DatabaseConnection;
import co.kozao.meetreserve.dao.query.UserSqlQueries;
import co.kozao.meetreserve.dao.service.UserDAO;
import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.model.User;
import co.kozao.meetreserve.web.dto.resquest.UserRequest;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDAO {

	private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

	public boolean existsByEmail(String email) {
		try {
			PreparedStatement ps = DatabaseConnection.getInstance()
					.prepareStatement(UserSqlQueries.SQL_EXISTS_BY_EMAIL);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			return rs.hashCode() > 0;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "An error occurred while verifying the email.", e);
		}
		return false;
	}

	public User findByEmail(String email) {
	    try{
			PreparedStatement ps = DatabaseConnection.getInstance()
					.prepareStatement(UserSqlQueries.SQL_FIND_BY_EMAIL);
	        ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				return new User.Builder()
						.id(rs.getLong("id_user"))
						.name(rs.getString("name"))
						.surname(rs.getString("surname"))
						.email(rs.getString("email"))
						.password(rs.getString("password"))
						.role(Role.valueOf(rs.getString("role")))
						.build();
			}
	    } catch (SQLException e) {
	        logger.log(Level.SEVERE, "An error occurred while retrieving the user.", e);
	    }
	    return null;
	}

	public boolean insert(User user) {
		try{
			PreparedStatement ps = DatabaseConnection.getInstance()
					.prepareStatement(UserSqlQueries.SQL_INSERT_USER);

			ps.setString(1, user.getName());
			ps.setString(2, user.getSurname());
			ps.setString(3, user.getEmail());
			ps.setString(4, user.getPassword());
			ps.setString(5, user.getRole().name());

			return ps.executeUpdate() > 0;
		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Error while inserting the user.", e);
		}
		return false;
	}


	@Override
	public User updateUser(UserRequest request) {
		return null;
	}

	@Override
	public boolean deleteUser(String email) {
		return false;
	}
}