package co.kozao.meetreserve.dao;

public class RoomSqlQueries {
	
	public RoomSqlQueries(){
	}
	
	public static final String SQL_INSERT_ROOM = "INSERT INTO room (name, capacity, location, statut)"
			+ "									  VALUES (?,?,?,?)";
	
	public static final String SQL_FIND_BY_ID = "SELECT id_room, name, capacity, statut "
			+ "									 FROM room "
			+ "									 WHERE id_room = ?";
	public static final String SQL_FIND_ALL = "SELECT id_room, name, capacity, location, statut "
			+ "								   FROM room";
	public static final String SQL_UPDATE_ROOM = "UPDATE room "
			+ "									  SET name = ?, capacity = ?, location = ?, description = ?, "
			+ "									  WHERE id_room = ?";
	public static final String SQL_DELETE_ROOM = "DELETE FROM room WHERE id_room = ?";

}
