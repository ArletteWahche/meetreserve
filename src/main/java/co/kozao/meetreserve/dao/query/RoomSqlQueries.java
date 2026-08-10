package co.kozao.meetreserve.dao.query;

public class RoomSqlQueries {
	

	public RoomSqlQueries(){
	}
	
	public static final String SQL_INSERT_ROOM = "INSERT INTO room (name, capacity, location, statut)"
			+ "									  VALUES (?,?,?,?)";
	
	public static final String SQL_UPDATE_ROOM = "UPDATE room "
			+ "									  SET name = ?, capacity = ?, location = ?, description = ?, "
			+ "									  WHERE id_room = ?";
	
	public static final String SQL_DELETE_ROOM = "UPDATE room "
			+ "									  SET isDeleted = ?, "
			+ "									  WHERE id_room = ?";
	
	public static final String SQL_FIND_BY_ID = "SELECT id_room, name, capacity, statut "
			+ "									 FROM room "
			+ "									 WHERE id_room = ?";
	
	public static final String SQL_FIND_ALL = "SELECT id_room, name, capacity, location, statut "
			+ "								   FROM room";
	
	public static final String SQL_FIND_AVAILABLE =
            "SELECT id_room, name_room, capacity, location, description, available, deleted"
            + "FROM rooms "
            + "WHERE deleted = false AND available = true";
	
	public static final String SQL_UPDATE_AVAILABLE =
            "UPDATE available "
            + "SET available = ? "
            + "WHERE deleted = true AND available = false";

}
