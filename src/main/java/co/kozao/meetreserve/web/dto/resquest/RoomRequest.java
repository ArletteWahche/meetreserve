package co.kozao.meetreserve.web.dto.resquest;

public class RoomRequest {

    private String roomName;
    private Long capacity;
    private String location;
    private String description;
    private boolean available = true;
    private String imageUrl;

    // Constructeur privé utilisé par le Builder
    public RoomRequest(Builder builder) {
        this.roomName = builder.roomName;
        this.capacity = builder.capacity;
        this.location = builder.location;
        this.description = builder.description;
        this.available = builder.available;
        this.imageUrl = builder.imageUrl;
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
    
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



    // ==== Builder ====
    public static class Builder {
        private String roomName;
        private Long capacity;
        private String location;
        private String description;
        private Boolean available;
        private String imageUrl;

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
        
        public Builder imageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public RoomRequest build() {
            return new RoomRequest(this);
        }
    }
}
