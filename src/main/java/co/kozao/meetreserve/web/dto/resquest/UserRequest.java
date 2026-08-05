package co.kozao.meetreserve.web.dto.resquest;

import co.kozao.meetreserve.model.User;
import co.kozao.meetreserve.web.dto.response.UserResponse;

public class UserRequest {
    private String name;
    private String surname;
    private String email;
    private String password;

    public UserRequest(Builder builder) {
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.password = builder.password;
    }

    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }

    // Setters (gardés, car ton DAO/Service les utilisent)
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }

    public static class Builder {
        private String name;
        private String surname;
        private String email;
        private String password;

        public UserRequest.Builder name(String name) {
            this.name = name;
            return this;
        }

        public UserRequest.Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public UserRequest.Builder email(String email) {
            this.email = email;
            return this;
        }

        public UserRequest.Builder password(String password) {
            this.password = password;
            return this;
        }

        public UserRequest build() {
            return new UserRequest(this);
        }
    }
}
