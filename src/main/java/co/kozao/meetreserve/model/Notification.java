package co.kozao.meetreserve.model;

import java.sql.Date;

public class Notification {

    private Long id;
    private String message;
    private String status;
    private Long reservationId;
    private Long userId;
    private Boolean read;
    private Date createdAt;

    private Notification(Builder builder) {
        this.id = builder.id;
        this.message = builder.message;
        this.status = builder.status;
        this.reservationId = builder.reservationId;
        this.userId = builder.userId;
        this.read = builder.read;
        this.createdAt = builder.createdAt;
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public Long getReservationId() { return reservationId; }
    public Long getUserId() { return userId; }
    public Boolean isRead() { return read; }
    public Date getCreatedAt() { return createdAt; }

    public void setId(Long id) { this.id = id; }
    public void setMessage(String message) { this.message = message; }
    public void setStatus(String status) { this.status = status; }
    public void setReservationId(Long reservationId) { this.reservationId = reservationId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setRead(Boolean read) { this.read = read; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public static class Builder {
        private Long id;
        private String message;
        private String status;
        private Long reservationId;
        private Long userId;
        private Boolean read;
        private Date createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder reservationId(Long reservationId) { this.reservationId = reservationId; return this; }
        public Builder userId(Long userId) { this.userId = userId; return this; }
        public Builder read(Boolean read) { this.read = read; return this; }
        public Builder createdAt(Date createdAt) { this.createdAt = createdAt; return this; }

        public Notification build() {
            return new Notification(this);
        }
    }
}
