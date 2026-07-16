package co.kozao.meetreserve.model;

public class Room {
	
	private Long id;
	private String nameRoom;
	private int capacity;
	private String localisation;
	private String statut;
	
	
	public Room(Long id, String nameRoom, int capacity, String localisation, String statut) {
		this.id = id;
		this.nameRoom = nameRoom;
		this.capacity = capacity;
		this.localisation = localisation;
		this.statut = statut;
	}
}
