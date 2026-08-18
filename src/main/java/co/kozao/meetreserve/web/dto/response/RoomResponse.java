package co.kozao.meetreserve.web.dto.response;

public class RoomResponse {

    private Long id;
    private String roomName;
    private Long capacity;
    private String location;
    private String description;
    private Boolean available;
    private Boolean isDeleted;
    private String imageUrl;

    // Constructeur privé utilisé par le Builder
    private RoomResponse(Builder builder) {
        this.id = builder.id;
        this.roomName = builder.roomName;
        this.capacity = builder.capacity;
        this.location = builder.location;
        this.description = builder.description;
        this.available = builder.available;
        this.isDeleted = builder.isDeleted;
        this.imageUrl = builder.imageUrl;
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

    public void setRoomName(String roomName) {
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

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // ==== Builder ====
    public static class Builder {
        private Long id;
        private String roomName;
        private Long capacity;
        private String location;
        private String description;
        private Boolean available;
        private Boolean isDeleted;
        private String imageUrl;

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

        public Builder isDeleted(boolean isDeleted) {
            this.isDeleted = isDeleted;
            return this;
        }
        
        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public RoomResponse build() {
            return new RoomResponse(this);
        }
    }
}
