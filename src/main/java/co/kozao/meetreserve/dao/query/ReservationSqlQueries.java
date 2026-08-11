package co.kozao.meetreserve.dao.query;

public class ReservationSqlQueries {

	public ReservationSqlQueries() {
	}

	public static final String SQL_INSERT_RESERVATION =
			"INSERT INTO reservations (id_user, id_room, reservation_date, start_time, end_time, subject, status, created_at) "
			+ "VALUES (?,?,?,?,?,?,?,?)";

	public static final String SQL_UPDATE_RESERVATION =
			"UPDATE reservations "
			+ "SET id_user = ?, id_room = ?, reservation_date = ?, start_time = ?, end_time = ?, subject = ?, status = ? "
			+ "WHERE id_reservation = ?";

	public static final String SQL_DELETE_RESERVATION =
			"UPDATE reservations "
					+ "SET deleted = true "
					+ "WHERE id_reservation = ?";

	public static final String SQL_FIND_BY_ID =
	        "SELECT id_reservation, id_user, id_room, reservation_date, "
	        + "start_time, end_time, subject, status, created_at, deleted "
	        + "FROM reservations "
	        + "WHERE id_reservation = ? AND deleted = false";

	public static final String SQL_FIND_ALL =
	        "SELECT id_reservation, id_user, id_room, reservation_date, "
	        + "start_time, end_time, subject, status, created_at, deleted "
	        + "FROM reservations "
	        + "WHERE deleted = false";

	public static final String SQL_FIND_BY_USER_ID =
	        "SELECT id_reservation, id_user, id_room, reservation_date, "
	        + "start_time, end_time, subject, status, created_at, deleted "
	        + "FROM reservations "
	        + "WHERE id_user = ? AND deleted = false";

	public static final String SQL_UPDATE_STATUS =
	        "UPDATE reservations "
	        + "SET status = ? "
	        + "WHERE id_reservation = ? AND deleted = false "
	        + "RETURNING id_reservation, id_user, id_room, reservation_date, "
	        + "start_time, end_time, subject, status, created_at, deleted";

	// Un conflit existe si une réservation active (non annulée) sur la même salle
	// chevauche l'intervalle demandé : existing.start < new.end AND existing.end > new.start
	public static final String SQL_CHECK_CONFLICT =
			"SELECT COUNT(*) FROM reservations "
			+ "WHERE id_room = ? AND reservation_date = ? AND status <> 'CANCELLED' "
			+ "AND start_time < ? AND end_time > ?";
}
