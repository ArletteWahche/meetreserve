package co.kozao.meetreserve.dao.service;

import co.kozao.meetreserve.model.Reservation;
import co.kozao.meetreserve.model.ReservationStatus;

import java.util.List;

public interface ReservationDao {
    Reservation insert(Reservation reservation);
    Reservation update(long reservationId, Reservation reservation);
    boolean delete(long reservationId);
    Reservation findById(long id);
    List<Reservation> findAll();
    List<Reservation> findByUserId(long userId);
    Reservation updateStatus(long reservationId, ReservationStatus status);
}
