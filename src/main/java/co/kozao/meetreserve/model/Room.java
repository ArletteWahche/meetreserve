package co.kozao.meetreserve.model;

public class Room {
	
	private Long id;
	private String nameRoom;
	private Long capacity;
	private String location;
	private String statut;
	
	
	public Room() {
		
	}
	
	public Room(Long id, String nameRoom, Long capacity, String location, String statut) {
		this.id = id;
		this.nameRoom = nameRoom;
		this.capacity = capacity;
		this.location = location;
		this.statut = statut;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public String getStatut() {
		return statut;
	}


	public void setStatut(String statut) {
		this.statut = statut;
	}

}
