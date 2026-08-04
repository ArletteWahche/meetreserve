package co.kozao.meetreserve.service;

import co.kozao.meetreserve.dao.UserDao;
import co.kozao.meetreserve.model.User;

public class AuthService {

    private final UserDao userDao = new UserDao();

    public User login(String email, String password) {
        return userDao.login(email, password);
    }
}