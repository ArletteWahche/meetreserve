package co.kozao.meetreserve.model;


public class Room {

    private Long id;
    private String roomName;
    private Long capacity;
    private String location;
    private String description;
    private Boolean available;
    private Boolean deleted;

    public Room() {
    }

    private Room(Builder builder) {
        this.id = builder.id;
        this.roomName = builder.roomName;
        this.capacity = builder.capacity;
        this.location = builder.location;
        this.description = builder.description;
        this.available = builder.available;
        this.deleted = builder.deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setroomName(String roomName) {
        this.roomName = roomName;
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

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Boolean getIsDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public static class Builder {
        private Long id;
        private String roomName;
        private Long capacity;
        private String location;
        private String description;
        private Boolean available = true;
        private Boolean deleted = false;

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder roomName(String roomName) {
            this.roomName = roomName;
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

        public Builder available(Boolean available) {
            this.available = available;
            return this;
        }

        public Builder deleted(Boolean deleted) {
            this.deleted = deleted;
            return this;
        }

        public Room build() {
            return new Room(this);
        }
    }
}
