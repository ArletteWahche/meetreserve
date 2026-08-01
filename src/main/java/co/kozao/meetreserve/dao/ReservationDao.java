package co.kozao.meetreserve.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import co.kozao.meetreserve.model.Reservation;
import co.kozao.meetreserve.model.ReservationStatus;
import co.kozao.meetreserve.dao.DatabaseConnection;

public class ReservationDao {

    private static final Logger logger = Logger.getLogger(ReservationDao.class.getName());

    public boolean insert(Reservation reservation) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(ReservationSqlQueries.SQL_INSERT_RESERVATION)) {

            ps.setLong(1, reservation.getUserId());
            ps.setDate(2, reservation.getReservationDate());
            ps.setTime(3, reservation.getStartTime());
            ps.setTime(4, reservation.getEndTime());
            ps.setString(5, reservation.getSubject());
            ps.setString(6, reservation.getStatus().name());
            ps.setDate(7, reservation.getCreatedAt());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while inserting the reservation.", e);
        }
        return false;
    }

    public Reservation findById(long id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(ReservationSqlQueries.SQL_FIND_BY_ID)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRowToReservation(rs);
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while retrieving reservation by id.", e);
        }
        return null;
    }

    public List<Reservation> findAll() {
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(ReservationSqlQueries.SQL_FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                reservations.add(mapRowToReservation(rs));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while retrieving all reservations.", e);
        }

        return reservations;
    }

    //  méthode pour chercher par userId  change le nom pour éviter les conflits avec findById
    
    public List<Reservation> findByUserId(long userId) {
        List<Reservation> reservations = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(ReservationSqlQueries.SQL_FIND_BY_USER_ID)) {

            ps.setLong(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reservations.add(mapRowToReservation(rs));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while retrieving reservations by user id.", e);
        }

        return reservations;
    }

    public boolean updateStatus(long reservationId, ReservationStatus status) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(ReservationSqlQueries.SQL_UPDATE_STATUS)) {

            ps.setString(1, status.name());
            ps.setLong(2, reservationId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while updating reservation status.", e);
        }
        return false;
    }

    public boolean delete(long id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(ReservationSqlQueries.SQL_DELETE_RESERVATION)) {

            ps.setLong(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while deleting the reservation.", e);
        }
        return false;
    }

    private Reservation mapRowToReservation(ResultSet rs) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setId(rs.getLong("id_reservation"));
        reservation.setUserId(rs.getLong("id_user"));
        reservation.setReservationDate(rs.getDate("reservation_date"));
        reservation.setStartTime(rs.getTime("start_time"));
        reservation.setEndTime(rs.getTime("end_time"));
        reservation.setSubject(rs.getString("subject"));
        reservation.setStatus(ReservationStatus.valueOf(rs.getString("status")));
        reservation.setCreatedAt(rs.getDate("created_at"));
        return reservation;
    }
}