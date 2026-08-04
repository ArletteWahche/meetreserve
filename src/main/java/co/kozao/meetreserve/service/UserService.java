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

    public void validateRegistration(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User is required");
        }
        if (user.getName() == null || user.getName().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (user.getSurname() == null || user.getSurname().isBlank()) {
            throw new IllegalArgumentException("Surname is required");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }
        if (user.getRole() == null) {
            throw new IllegalArgumentException("Role is required");
        }
    }

    public boolean register(User user) {
        validateRegistration(user);

        if (emailExists(user.getEmail())) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        return userDao.insert(user);
    }
}