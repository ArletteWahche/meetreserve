package co.kozao.meetreserve.model;

import java.util.Date;
import java.sql.Time;

public class Reservation {

	private Long id;
	private Long userId;
	private Long roomId;
	private Date reservationDate;
	private Time startTime;
	private Time endTime;
	private String subject;
	private ReservationStatus status;
	private Date createdAt;
	private boolean deleted;

	// Constructeur privé — utilisé uniquement par le Builder
	private Reservation(Builder builder) {
		this.id = builder.id;
		this.userId = builder.userId;
		this.roomId = builder.roomId;
		this.reservationDate = builder.reservationDate;
		this.startTime = builder.startTime;
		this.endTime = builder.endTime;
		this.subject = builder.subject;
		this.status = builder.status;
		this.createdAt = builder.createdAt;
		this.deleted = builder.deleted;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public ReservationStatus getStatus() {
		return status;
	}

	public void setStatus(ReservationStatus status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Long getRoomId() {
		return roomId;
	}
	public void setRoomId(Long roomId) {
		this.roomId = roomId;
	}

	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public static class Builder {
		private Long id;
		private Long userId;
		private Long roomId;
		private Date reservationDate;
		private Time startTime;
		private Time endTime;
		private String subject;
		private ReservationStatus status;
		private Date createdAt;
		private boolean deleted;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

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

		public Builder status(ReservationStatus status) {
			this.status = status;
			return this;
		}

		public Builder createdAt(Date createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder deleted(boolean deleted) {
			this.deleted = deleted;
			return this;
		}

		public Reservation build() {
			return new Reservation(this);
		}
	}
}