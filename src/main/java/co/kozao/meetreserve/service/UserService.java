package co.kozao.meetreserve.service;

import co.kozao.meetreserve.dao.UserDao;
import co.kozao.meetreserve.model.User;

public class UserService{
	

	    private final UserDao userDao;

	    public UserService() {
	        this.userDao = new UserDao();
	    }

	    public User authentifier(String email, String password) {
	        return userDao.findByEmailAndPassword(email, password);
	    }

	    public boolean emailExists(String email) {
	        return userDao.existsByEmail(email);
	    }

	    public boolean register(User user) {
	        if (emailExists(user.getEmail())) {
	            return false;
	        }
	        return userDao.insert(user);
	    }
}
