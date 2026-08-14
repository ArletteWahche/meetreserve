package co.kozao.meetreserve.dao.service;

import java.util.List;

import co.kozao.meetreserve.model.User;
import co.kozao.meetreserve.web.dto.resquest.UserRequest;

public interface UserDAO {
    Boolean existsByEmail(String email);
    User findByEmail(String email);
    Boolean insert(User user);
    User updateUser(UserRequest request);
    Boolean deleteUser(String email);
    List<User> findAll();
}
