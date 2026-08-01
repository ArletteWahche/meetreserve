package co.kozao.meetreserve.model;

import java.sql.Time;
import java.sql.Date;

public class Reservation{
	private Long id;
	private Long UserId;
	private Date reservationDate;
	private Time startTime;
	private Time endTime;
	private String subject;
	private ReservationStatus status;
	private Date createdAt;
	
	public Reservation(Long id, Long UserId, Date reservationDate,Time startTime, Time endTime, String subject,ReservationStatus status,  Date createdAt) {
		this.id=id;
		this.UserId=UserId;
		this.reservationDate=reservationDate;
		this.startTime=startTime;
		this.endTime=endTime;
		this.subject=subject;
		this.status=status;
		this.createdAt=createdAt;
	}
	
	public Reservation() {
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return UserId;
	}

	public void setUserId(Long UserId) {
		this.UserId = UserId;
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

}