package co.kozao.meetreserve.web.dto.resquest;

import co.kozao.meetreserve.model.Reservation;

import java.sql.Time;
import java.util.Date;

public class ReservationRequest {

    private Long userId;
    private Long roomId;
    private Date reservationDate;
    private Time startTime;
    private Time endTime;
    private String subject;

    private ReservationRequest(Builder builder) {
        this.userId = builder.userId;
        this.roomId = builder.roomId;
        this.reservationDate = builder.reservationDate;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.subject = builder.subject;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getRoomId() {
        return roomId;
    }
    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public class Builder {
        private Long userId;
        private Long roomId;
        private Date reservationDate;
        private Time startTime;
        private Time endTime;
        private String subject;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder roomId(Long roomId) {
            this.roomId = roomId;
            return this;
        }

        public Builder reservationDate(Date reservationDate) {
            this.reservationDate = reservationDate;
            return this;
        }

        public Builder startTime(Time startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder endTime(Time endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder subject(String subject) {
            this.subject = subject;
            return this;
        }

        public ReservationRequest build() {
            return new ReservationRequest(this);
        }
    }
}
