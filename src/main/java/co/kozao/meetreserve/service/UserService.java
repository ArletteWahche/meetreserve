package co.kozao.meetreserve.service;

import co.kozao.meetreserve.dao.impl.UserDaoImpl;

import co.kozao.meetreserve.mapper.UserMapper;
import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.model.User;
import co.kozao.meetreserve.web.dto.response.UserResponse;
import co.kozao.meetreserve.web.dto.resquest.UserRequest;

import java.util.List;
import java.util.stream.Collectors;

import org.mindrot.jbcrypt.BCrypt;

public class UserService {
    private final UserDaoImpl userDao;
    private final UserMapper mapper;

    public UserService() {
        this.userDao = new UserDaoImpl();
        this.mapper = new UserMapper();
    }

    public UserResponse login(String email, String rawPassword) {
        User user = userDao.findByEmail(email);
        boolean isCorrectPassword = user != null && BCrypt.checkpw(rawPassword, user.getPassword());
        if (isCorrectPassword) {
            return mapper.toResponse(user);
        }
        return null;
    }
    
    public List<UserResponse> getTeamMembers(Long managerId) {
        return userDao.findByManagerId(managerId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public Boolean emailExists(String email) {
        return userDao.existsByEmail(email);
    }

    public Boolean register(UserRequest user) {
        if (Boolean.TRUE.equals(emailExists(user.getEmail()))) {
            return false;
        }

        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        User userToInsert = mapper.mapToEntity(user);
        userToInsert.setPassword(hashedPassword);
        userToInsert.setRole(user.getRole().isEmpty() ? Role.EMPLOYEE : Role.valueOf(user.getRole()));

        return userDao.insert(userToInsert);
    }
    
    public List<UserResponse> getAllUsers(){
    	return userDao.findAll().stream()
    			.map(mapper::toResponse)
    			.collect(Collectors.toList());
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
}