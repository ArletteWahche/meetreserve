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

    public ValidationResult validateRegistration(String name, String surname, String email, String password) {

        if (isAnyFieldEmpty(name, surname, email, password)) {
            return ValidationResult.failure("Please fill in the blank space.");
        }

        if (!isValidEmail(email)) {
            return ValidationResult.failure("Please enter a valid email address.");
        }

        if (!isValidPassword(password)) {
            return ValidationResult.failure("Password must be at least 6 characters long.");
        }

        if (emailExists(email)) {
            return ValidationResult.failure("An account with this email already exists.");
        }

        return ValidationResult.success();
    }

    private boolean isAnyFieldEmpty(String name, String surname, String email, String password) {
        return isBlank(name) || isBlank(surname) || isBlank(email) || isBlank(password);
    }

    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$");
    }

    private boolean isValidPassword(String password) {
        return password.length() >= 6;
    }

    public boolean register(User user) {
        if (user.getRole() == null) {
            throw new IllegalArgumentException("The role is required");
        }

        if (emailExists(user.getEmail())) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

        User userToInsert = new User.Builder()
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .password(hashedPassword)
                .role(user.getRole())
                .build();

        return userDao.insert(userToInsert);
    }
}