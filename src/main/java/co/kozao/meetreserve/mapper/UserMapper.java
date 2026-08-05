package co.kozao.meetreserve.mapper;

import co.kozao.meetreserve.model.User;
import co.kozao.meetreserve.web.dto.response.UserResponse;
import co.kozao.meetreserve.web.dto.resquest.UserRequest;

public class UserMapper {

    public User mapToEntity(UserRequest userRequest) {
        return new User.Builder()
                    .name(userRequest.getName())
                    .surname(userRequest.getSurname())
                    .email(userRequest.getEmail())
                    .password(userRequest.getPassword())
                    .build();
    }

    public UserResponse toResponse(User user){
        return new UserResponse.Builder()
                    .id(user.getId())
                    .name(user.getName())
                    .surname(user.getSurname())
                    .email(user.getEmail())
                    .role(user.getRole().name())
                    .build();
    }

    public User updateUser(UserRequest request, User user) {
        if (request.getName() != null) {
            user.setName(request.getName());
        }
        if (request.getSurname() != null) {
            user.setSurname(request.getSurname());
        }
        if (request.getEmail() != null) {
            user.setEmail(request.getEmail());
        }
        return user;

    }
}
