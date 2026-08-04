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

		// Constructeur privé utilisé par le Builder
		private Room(Builder builder) {
			this.id = builder.id;
			this.nameRoom = builder.nameRoom;
			this.capacity = builder.capacity;
			this.location = builder.location;
			this.statut = builder.statut;
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

		// ==== Builder ====
		public static class Builder {
			private Long id;
			private String nameRoom;
			private Long capacity;
			private String location;
			private String statut;

			public Builder id(Long id) {
				this.id = id;
				return this;
			}

			public Builder nameRoom(String nameRoom) {
				this.nameRoom = nameRoom;
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

			public Builder statut(String statut) {
				this.statut = statut;
				return this;
			}

			public Room build() {
				if (nameRoom == null || nameRoom.isBlank()) {
					throw new IllegalStateException("The room name is required.");
				}
				return new Room(this);
			}
		}
}
