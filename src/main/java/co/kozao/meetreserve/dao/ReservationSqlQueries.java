package co.kozao.meetreserve.dao;

public class ReservationSqlQueries {

	public ReservationSqlQueries() {
		
	}
		
		public static final String SQL_INSERT_RESERVATION = "INSERT INTO reservation (id_reservation, User_id, reservation_date, start_time, end_time, subject, status, createdAt)"
				+ "									  VALUES (?,?,?,?,?,?,?,?)";
		
		public static final String SQL_FIND_BY_ID = "SELECT id_reservation, User_id, room_id, reservation_date, start_time, end_time, status "
				+ "									 FROM reservation "
				+ "									 WHERE id_resrvation = ?";
		public static final String SQL_FIND_ALL = "SELECT id_reservation, User_id, room_id, reservation_date, start_time, end_time, status "
				+ "									 FROM reservation ";
		
		public static final String SQL_FIND_BY_USER_ID = "SELECT id_reservation, user_id, room_id, start_time, end_time, status "
	            + "										FROM reservation WHERE user_id = ?";

	    public static final String SQL_UPDATE_STATUS = "UPDATE reservation SET status = ? WHERE id_reservation = ?";

	    public static final String SQL_DELETE_RESERVATION = "DELETE FROM reservation WHERE id_reservation = ?";


}
