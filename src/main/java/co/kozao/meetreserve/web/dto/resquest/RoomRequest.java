package co.kozao.meetreserve.web.dto.resquest;

public class RoomRequest {

    private String nameRoom;
    private Long capacity;
    private String location;
    private String description;

    // Constructeur privé utilisé par le Builder
    public RoomRequest(Builder builder) {
        this.nameRoom = builder.nameRoom;
        this.capacity = builder.capacity;
        this.location = builder.location;
        this.description = builder.description;
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

    // ==== Builder ====
    public static class Builder {
        private String nameRoom;
        private Long capacity;
        private String location;
        private String description;

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

        public RoomRequest build() {
            return new RoomRequest(this);
        }
    }
}
