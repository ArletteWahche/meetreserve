package co.kozao.meetreserve.dao.query;

public class RoomSqlQueries {

	public RoomSqlQueries() {
	}

	public static final String SQL_INSERT_ROOM =
			"INSERT INTO rooms (room_name, capacity, location, description, available, image_url) "
			+ "VALUES (?,?,?,?,?,?)";

	public static final String SQL_UPDATE_ROOM =
			"UPDATE rooms "
			+ "SET room_name = ?, capacity = ?, location = ?, description = ?, available = ?, image_url = ? "
			+ "WHERE id_room = ?";

	public static final String SQL_DELETE_ROOM =
			"UPDATE rooms "
			+ "SET deleted = true "
			+ "WHERE id_room = ?";

	public static final String SQL_FIND_BY_ID =
			"SELECT id_room, room_name, capacity, location, description, available, deleted, image_url "
			+ "FROM rooms "
			+ "WHERE id_room = ?";

	public static final String SQL_FIND_ALL =
			"SELECT id_room, room_name, capacity, location, description, available, deleted, image_url "
			+ "FROM rooms "
			+ "WHERE deleted = false";

	public static final String SQL_FIND_AVAILABLE =
			"SELECT id_room, room_name, capacity, location, description, available, deleted, image_url "
			+ "FROM rooms "
			+ "WHERE deleted = false AND available = true "
			+ "ORDER BY room_name";

	public static final String SQL_UPDATE_AVAILABLE =
			"UPDATE rooms "
			+ "SET available = ? "
			+ "WHERE id_room = ?";
}