package co.kozao.meetreserve.web.dto.response;

public class UserResponse {
    private Long id;
    private Long managerId;
    private String name;
    private String surname;
    private String email;
    private String role;

    public UserResponse(Builder builder) {
        this.id = builder.id;
        this.managerId = builder.managerId;
        this.name = builder.name;
        this.surname = builder.surname;
        this.email = builder.email;
        this.role = builder.role;
    }

    // Getters
    public Long getId() { return id; }
    public Long getManagerId() { return managerId; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public String getRole() { return role; }

    // Setters (gardés, car ton DAO/Service les utilisent)
    public void setId(Long id) { this.id = id; }
    public void setManagerId(Long managerId) { this.managerId = managerId; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setEmail(String email) { this.email = email; }
    public void setRole(String role) { this.role = role; }

    public static class Builder {
        private Long id;
        private Long managerId;
        private String name;
        private String surname;
        private String email;
        private String role;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }
        
        public Builder managerId(Long managerId) {
            this.managerId = managerId;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surname(String surname) {
            this.surname = surname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public UserResponse build() {
            return new UserResponse(this);
        }
    }
}
