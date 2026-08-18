package co.kozao.meetreserve.dao.service;

import co.kozao.meetreserve.model.Reservation;


import co.kozao.meetreserve.model.ReservationStatus;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public interface ReservationDao {
    Reservation insert(Reservation reservation);
    Reservation update(Long reservationId, Reservation reservation);
    Boolean delete(Long reservationId);
    Reservation findById(Long id);
    List<Reservation> findAll();
    List<Reservation> findByUserId(Long userId);
    Reservation updateStatus(Long reservationId, ReservationStatus status);
    Boolean hasConflict(Long roomId, Date reservationDate, Time startTime, Time endTime);
	
}
