package co.kozao.meetreserve.service;

import org.mindrot.jbcrypt.BCrypt;

import co.kozao.meetreserve.dao.UserDao;
import co.kozao.meetreserve.model.User;

public class UserService {

    private final UserDao userDao;

    public UserService() {
        this.userDao = new UserDao();
    }

    public User login(String email, String rawPassword) {
        User user = userDao.findByEmail(email);

        if (user != null && BCrypt.checkpw(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }

    public boolean emailExists(String email) {
        return userDao.existsByEmail(email);
    }

    public boolean register(User user) {
    	if (user.getRole() == null) {
            throw new IllegalArgumentException("Le rôle est obligatoire");
        }
    	
    	if (emailExists(user.getEmail())) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        return userDao.insert(user);
    }
    
}