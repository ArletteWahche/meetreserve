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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoImpl implements UserDAO {

	private static final Logger logger = Logger.getLogger(UserDaoImpl.class.getName());

	public Boolean insert(User user) {
	    try{
	        PreparedStatement ps = DatabaseConnection.getInstance()
	                .prepareStatement(UserSqlQueries.SQL_INSERT_USER);
	        ps.setString(1, user.getName());
	        ps.setString(2, user.getSurname());
	        ps.setString(3, user.getEmail());
	        ps.setString(4, user.getPassword());
	        ps.setString(5, user.getRole().name());
	        if (user.getManagerId() != null) {
	            ps.setLong(6, user.getManagerId());
	        } else {
	            ps.setNull(6, java.sql.Types.BIGINT);
	        }
	        return ps.executeUpdate() > 0;
	    } catch (SQLException e) {
	        logger.log(Level.SEVERE, "Error while inserting the user.", e);
	    }
	    return false;
	}
	
	public List<User> findByManagerId(Long managerId) {
	    try {
	        PreparedStatement ps = DatabaseConnection.getInstance()
	                .prepareStatement(UserSqlQueries.SQL_FIND_BY_MANAGER_ID);
	        ps.setLong(1, managerId);
	        ResultSet rs = ps.executeQuery();
	        List<User> users = new ArrayList<>();
	        while (rs.next()) {
	            users.add(mapRow(rs));
	        }
	        return users;
	    } catch (SQLException e) {
	        logger.log(Level.SEVERE, "Error while retrieving users by manager.", e);
	    }
	    return Collections.emptyList();
	}
	
	
	public Boolean existsByEmail(String email) {
		try {
			PreparedStatement ps = DatabaseConnection.getInstance()
					.prepareStatement(UserSqlQueries.SQL_EXISTS_BY_EMAIL);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			return rs.next() ;
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

	public List<User> findAll(){
		try {
			PreparedStatement ps = DatabaseConnection.getInstance()
					.prepareStatement(UserSqlQueries.SQL_FIND_ALL);
			ResultSet rs = ps.executeQuery();
			
			List<User> users = new ArrayList<>();
			while(rs.next()) {
				users.add(new User.Builder()
						.id(rs.getLong("id_user"))
						.name(rs.getString("name"))
						.surname(rs.getString("surname"))
						.email(rs.getString("email"))
						.password(rs.getString("password"))
						.role(Role.valueOf(rs.getString("role")))
						.build());
			}
			return users;
		} catch(SQLException e) {
			logger.log(Level.SEVERE, "Error while retrievving users.", e);
		}
		return Collections.emptyList();
	}
	
	private User mapRow(ResultSet rs) throws SQLException {
	    Long managerId = rs.getLong("manager_id");
	    if (rs.wasNull()) managerId = null;
	    return new User.Builder()
	            .id(rs.getLong("id_user"))
	            .name(rs.getString("name"))
	            .surname(rs.getString("surname"))
	            .email(rs.getString("email"))
	            .password(rs.getString("password"))
	            .role(Role.valueOf(rs.getString("role")))
	            .managerId(managerId)
	            .build();
	}

	@Override
	public User updateUser(UserRequest request) {
	    try {
	        PreparedStatement ps = DatabaseConnection.getInstance()
	                .prepareStatement(UserSqlQueries.SQL_UPDATE_USER);
	        ps.setString(1, request.getName());
	        ps.setString(2, request.getSurname());
	        ps.setString(3, request.getRole());
	        if (request.getManagerId() != null) {
	            ps.setLong(4, request.getManagerId());
	        } else {
	            ps.setNull(4, java.sql.Types.BIGINT);
	        }
	        ps.setString(5, request.getEmail());

	        int rows = ps.executeUpdate();
	        return rows > 0 ? findByEmail(request.getEmail()) : null;

	    } catch (SQLException e) {
	        logger.log(Level.SEVERE, "Error while updating the user.", e);
	    }
	    return null;
	}

	@Override
	public Boolean deleteUser(String email) {
	    try {
	        PreparedStatement ps = DatabaseConnection.getInstance()
	                .prepareStatement(UserSqlQueries.SQL_DELETE_USER);
	        ps.setString(1, email);
	        return ps.executeUpdate() > 0;

	    } catch (SQLException e) {
	        logger.log(Level.SEVERE, "Error while deleting the user.", e);
	    }
	    return false;
	}
}