package co.kozao.meetreserve.service;

import java.util.List;
import co.kozao.meetreserve.dao.ReservationDao;
import co.kozao.meetreserve.model.Reservation;
import co.kozao.meetreserve.model.ReservationStatus;
import co.kozao.meetreserve.model.Room;

public class ReservationService {

    private final ReservationDao reservationDao = new ReservationDao();

    public List<Reservation> getReservationsByUserId(Long userId) {
        return reservationDao.findByUserId(userId);
    }
    
    public Reservation getReservationById(Long id) {
    	return reservationDao.findById(id);
    }
    
    public List<Reservation> getAllReservations() {
        return reservationDao.findAll();
    }
    
    public boolean addReservation(Reservation reservation) {
    	if(reservation == null) {
    		throw new IllegalArgumentException("Reservation is required");
    	}
    	return reservationDao.insert(reservation);
    }
    
    public boolean updateStatus(long reservationId, ReservationStatus status) {
    	if(status == null) {
    		throw new IllegalArgumentException("Status is required");
    	}
    	return reservationDao.updateStatus(reservationId, status);
    }
    
    public boolean deleteReservation(Long id) {
    	
    	return reservationDao.delete(id);
    }
}