package co.kozao.meetreserve.dao.impl;

import co.kozao.meetreserve.dao.database.DatabaseConnection;


import co.kozao.meetreserve.dao.query.ReservationSqlQueries;
import co.kozao.meetreserve.dao.service.ReservationDao;
import co.kozao.meetreserve.model.Reservation;
import co.kozao.meetreserve.model.ReservationStatus;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationDaoImpl implements ReservationDao {

    private static final Logger logger = Logger.getLogger(ReservationDaoImpl.class.getName());

    public Reservation insert(Reservation reservation) {
        try{
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(ReservationSqlQueries.SQL_INSERT_RESERVATION);

            ps.setLong(1, reservation.getUserId());
            ps.setLong(2, reservation.getRoomId());
            ps.setDate(3, (Date) reservation.getReservationDate());
            ps.setTime(4, reservation.getStartTime());
            ps.setTime(5, reservation.getEndTime());
            ps.setString(6, reservation.getSubject());
            ps.setString(7, reservation.getStatus().name());
            ps.setDate(8, (Date) reservation.getCreatedAt());

            int rows = ps.executeUpdate();
            if (rows == 0) {
                return null;
            }

            // On récupère le vrai id généré par PostgreSQL, pas le nombre de lignes affectées
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setId(generatedKeys.getLong(1));
                }
            }


            return reservation;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while inserting the reservation.", e);
            return null;
        }
    }

    public Reservation update(Long reservationId, Reservation reservation) {
        try{
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(ReservationSqlQueries.SQL_UPDATE_RESERVATION);

            ps.setLong(1, reservation.getUserId());
            ps.setLong(2, reservation.getRoomId());
            ps.setDate(3, (Date) reservation.getReservationDate());
            ps.setTime(4, reservation.getStartTime());
            ps.setTime(5, reservation.getEndTime());
            ps.setString(6, reservation.getSubject());
            ps.setString(7, reservation.getStatus().name());
            ps.setLong(8, reservationId);

            return ps.executeUpdate() > 0 ? reservation : null;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while inserting the reservation.", e);
            return null;
        }
    }

    public Boolean delete(Long reservationId) {
        try{
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(ReservationSqlQueries.SQL_DELETE_RESERVATION);

            ps.setLong(1, reservationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while deleting the reservation.", e);
        }
        return false;
    }

    public Reservation findById(Long id) {
        try{
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(ReservationSqlQueries.SQL_FIND_BY_ID);

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToReservation(rs);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while retrieving reservation by id.", e);
        }
        return null;
    }

    public List<Reservation> findAll() {

        try{
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(ReservationSqlQueries.SQL_FIND_ALL);
            ResultSet rs = ps.executeQuery();

            List<Reservation> reservations = new ArrayList<>();
            while (rs.next()) {
                reservations.add(mapRowToReservation(rs));
            }
            return reservations;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while retrieving all reservations.", e);
        }

        return Collections.emptyList();
    }

    //  méthode pour chercher par userId  change le nom pour éviter les conflits avec findById
    public List<Reservation> findByUserId(Long userId) {

        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(ReservationSqlQueries.SQL_FIND_BY_USER_ID);
            ps.setLong(1, userId);

            List<Reservation> reservations = new ArrayList<>();
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reservations.add(mapRowToReservation(rs));
            }
            return reservations;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while retrieving reservations by user id.", e);
        }

        return Collections.emptyList();
    }

    @Override
    public Reservation updateStatus(Long reservationId, ReservationStatus status) {
        try{
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(ReservationSqlQueries.SQL_UPDATE_STATUS);

            ps.setString(1, status.name());
            ps.setLong(2, reservationId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapRowToReservation(rs);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while updating reservation status.", e);
        }
        return null;
    }
    
    @Override
    public Boolean hasConflict(Long roomId, Date reservationDate, Time startTime, Time endTime) {
    	try {
    		PreparedStatement ps = DatabaseConnection.getInstance()
    				.prepareStatement(ReservationSqlQueries.SQL_CHECK_CONFLICT);
    		
    		ps.setLong(1, roomId);
    		ps.setDate(2, new Date(reservationDate.getTime()));
    		ps.setTime(3, endTime);
    		ps.setTime(4, startTime);
    		
    		ResultSet rs = ps.executeQuery();
    		if(rs.next()) {
    			return rs.getInt(1) > 0;
    		}
    	}catch (SQLException e) {
    		logger.log(Level.SEVERE, "Error while checking reservation conflict.", e);
    	}
    	return false;
    }

    private Reservation mapRowToReservation(ResultSet rs) throws SQLException {
        return new Reservation.Builder()
                .id(rs.getLong("id_reservation"))
                .userId(rs.getLong("id_user"))
                .roomId(rs.getLong("id_room"))
                .reservationDate(rs.getDate("reservation_date"))
                .startTime(rs.getTime("start_time"))
                .endTime(rs.getTime("end_time"))
                .subject(rs.getString("subject"))
                .status(ReservationStatus.valueOf(rs.getString("status")))
                .createdAt(rs.getDate("created_at"))
                .build();
    }

	
}