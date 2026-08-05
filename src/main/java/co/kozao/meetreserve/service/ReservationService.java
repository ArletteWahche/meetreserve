package co.kozao.meetreserve.service;

import co.kozao.meetreserve.dao.impl.ReservationDaoImpl;
import co.kozao.meetreserve.mapper.ReservationMapper;
import co.kozao.meetreserve.model.Reservation;
import co.kozao.meetreserve.web.dto.response.ReservationResponse;
import co.kozao.meetreserve.web.dto.resquest.ReservationRequest;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ReservationService {

    private static Logger logger = Logger.getLogger(ReservationService.class.getName());

    private ReservationDaoImpl reservationDao;
    private ReservationMapper  reservationMapper;

    public ReservationService() {
        this.reservationDao = new ReservationDaoImpl();
        this.reservationMapper = new ReservationMapper();
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        // Implement the logic to add a reservation using the reservationDao
        // Convert ReservationRequest to Reservation model, call reservationDao.insert(), and return a ReservationResponse
        return null; // Placeholder for actual implementation

    }

    public List<ReservationResponse> getReservationByUserId(long userId) {
        List<Reservation> userReservation = reservationDao.findByUserId(userId);
        return userReservation.stream()
                .map(reservationMapper::toReservationResponse)
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        return reservations.stream()
                .map(reservationMapper::toReservationResponse)
                .collect(Collectors.toList());
    }

}