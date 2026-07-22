package co.kozao.meetreserve.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import co.kozao.meetreserve.dao.UserSqlQueries;
import co.kozao.meetreserve.model.User;
import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.util.DatabaseConnection;

public class UserDao {

	
	public User useByEmailAndPassword(String email, String password) {
		try(Connection conn = DatabaseConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(UserSqlQueries.SQL_FIND_BY_EMAIL_AND_PASSWORD)){
			
			ps.setString(1, email);
			ps.setString(2, password);
			
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
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
			System.out.println("Error while retrieving the user :" + e.getMessage());
		}
		return null;
		}

	public User useByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static final String SQL_FIND_BY_EMAIL_AND_PASSWORD = 
			"SELECT id_user, name, surname, email, password, role " + 
			"FROM user" +
			"WHERE email = ? and password = ?";
	
	}


