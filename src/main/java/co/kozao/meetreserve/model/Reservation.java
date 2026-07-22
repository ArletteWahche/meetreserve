package co.kozao.meetreserve.model;

import java.util.Date;

public class Reservation{
	private Long id;
	private Date startDate;
	private Date endDate;
	private String motif;
	private Room room;
	private User user;
	
	public Reservation(Long id, Date startDate, Date endDate, String motif, Room room, User user) {
		this.id=id;
		this.startDate=startDate;
		this.endDate=endDate;
		this.motif=motif;
		this.room=room;
		this.user=user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}