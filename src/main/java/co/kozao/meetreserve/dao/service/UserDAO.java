package co.kozao.meetreserve.dao.service;

import co.kozao.meetreserve.model.User;
import co.kozao.meetreserve.web.dto.resquest.UserRequest;

public interface UserDAO {
    boolean existsByEmail(String email);
    User findByEmail(String email);
    boolean insert(User user);
    User updateUser(UserRequest request);
    boolean deleteUser(String email);
}
