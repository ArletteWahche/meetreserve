package co.kozao.meetreserve.service;

import java.util.List;
import co.kozao.meetreserve.dao.ReservationDao;
import co.kozao.meetreserve.model.Reservation;

public class ReservationService {

    private final ReservationDao reservationDao = new ReservationDao();

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationDao.findByUserId(userId);
    }
}