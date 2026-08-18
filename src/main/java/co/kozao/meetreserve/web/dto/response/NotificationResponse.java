package co.kozao.meetreserve.web.dto.response;

import java.sql.Date;

public class NotificationResponse {

    private Long id;
    private String message;
    private String status;
    private Long reservationId;
    private Boolean read;
    private Date createdAt;

    private NotificationResponse(Builder builder) {
        this.id = builder.id;
        this.message = builder.message;
        this.status = builder.status;
        this.reservationId = builder.reservationId;
        this.read = builder.read;
        this.createdAt = builder.createdAt;
    }

    public Long getId() { return id; }
    public String getMessage() { return message; }
    public String getStatus() { return status; }
    public Long getReservationId() { return reservationId; }
    public Boolean isRead() { return read; }
    public Date getCreatedAt() { return createdAt; }

    public static class Builder {
        private Long id;
        private String message;
        private String status;
        private Long reservationId;
        private Boolean read;
        private Date createdAt;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder message(String message) { this.message = message; return this; }
        public Builder status(String status) { this.status = status; return this; }
        public Builder reservationId(Long reservationId) { this.reservationId = reservationId; return this; }
        public Builder read(Boolean read) { this.read = read; return this; }
        public Builder createdAt(Date createdAt) { this.createdAt = createdAt; return this; }

        public NotificationResponse build() {
            return new NotificationResponse(this);
        }
    }
}