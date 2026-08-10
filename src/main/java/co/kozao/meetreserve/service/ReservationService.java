package co.kozao.meetreserve.service;

import co.kozao.meetreserve.dao.impl.ReservationDaoImpl;

import co.kozao.meetreserve.mapper.ReservationMapper;
import co.kozao.meetreserve.model.Reservation;
import co.kozao.meetreserve.model.ReservationStatus;
import co.kozao.meetreserve.web.dto.response.ReservationResponse;
import co.kozao.meetreserve.web.dto.resquest.ReservationRequest;

import java.sql.Date;
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
        
    	if(request == null) {
    		throw new IllegalArgumentException("Reservation request is required");
    	}
    	Reservation reservation = reservationMapper.toReservation(request);
    	
    	reservation.setStatus(ReservationStatus.PENDING);
    	
    	reservation.setCreatedAt(new Date(System.currentTimeMillis()));
    	
    	Reservation created = reservationDao.insert(reservation);
    	if(created == null) {
    		return null; // Placeholder for actual implementation
    	}
    	
    	return reservationMapper.toReservationResponse(created);
    }
    
    public ReservationResponse getReservationById(Long id) {
    	Reservation reservation = reservationDao.findById(id);
    	return reservation != null ? reservationMapper.toReservationResponse(reservation) : null;
    }
    
    public ReservationResponse cancelReservation(Long id) {
    	Reservation cancelled = reservationDao.updateStatus(id, ReservationStatus.CANCELLED);
    	return cancelled != null ? reservationMapper.toReservationResponse(cancelled) : null;
    }

    public List<ReservationResponse> getReservationByUserId(Long userId) {
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