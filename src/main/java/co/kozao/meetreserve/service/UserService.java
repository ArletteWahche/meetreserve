package co.kozao.meetreserve.service;

import org.mindrot.jbcrypt.BCrypt;

import co.kozao.meetreserve.dao.UserDao;
import co.kozao.meetreserve.model.User;

public class UserService{
	
	private final UserDao userDao;
	
	public UserService() {
		this.userDao = new UserDao();
	}
	
	public User authentifier(String email, String password) {
		User user = userDao.useByEmail(email);
		
		if(user != null && BCrypt.checkpw(password, user.getPassword())) {
			return user;
		}
		return null;
	}
}