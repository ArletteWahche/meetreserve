package co.kozao.meetreserve.dao;

public class ReservationSqlQueries {

	public ReservationSqlQueries() {
	}

	public static final String SQL_INSERT_RESERVATION = 
			"INSERT INTO reservations (user_id, reservation_date, start_time, end_time, subject, status, created_at) "
			+ "VALUES (?,?,?,?,?,?,?)";

	public static final String SQL_FIND_BY_ID = 
			"SELECT id_reservation, user_id, room_id, reservation_date, start_time, end_time, status "
			+ "FROM reservations "
			+ "WHERE id_reservation = ?";

	public static final String SQL_FIND_ALL = 
			"SELECT id_reservation, user_id, room_id, reservation_date, start_time, end_time, status "
			+ "FROM reservations";

	public static final String SQL_FIND_BY_USER_ID = 
			"SELECT id_reservation, user_id, room_id, reservation_date, start_time, end_time, status "
			+ "FROM reservations WHERE user_id = ?";

	public static final String SQL_UPDATE_STATUS = 
			"UPDATE reservations SET status = ? WHERE id_reservation = ?";

	public static final String SQL_DELETE_RESERVATION = 
			"DELETE FROM reservations WHERE id_reservation = ?";

}