package co.kozao.meetreserve.web.dto.response;

public class RoomResponse {

    private Long id;
    private String nameRoom;
    private Long capacity;
    private String location;
    private String description;
    private boolean isDeleted;

    // Constructeur privé utilisé par le Builder
    private RoomResponse(Builder builder) {
        this.id = builder.id;
        this.nameRoom = builder.nameRoom;
        this.capacity = builder.capacity;
        this.location = builder.location;
        this.description = builder.description;
        this.isDeleted = builder.isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNameRoom() {
        return nameRoom;
    }

    public void setNameRoom(String nameRoom) {
        this.nameRoom = nameRoom;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    // ==== Builder ====
    public static class Builder {
        private Long id;
        private String nameRoom;
        private Long capacity;
        private String location;
        private String description;
        private boolean isDeleted;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nameRoom(String nameRoom) {
            this.nameRoom = nameRoom;
            return this;
        }

        public Builder capacity(Long capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder location(String location) {
            this.location = location;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }

        public RoomResponse build() {
            return new RoomResponse(this);
        }
    }
}
