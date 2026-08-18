package co.kozao.meetreserve.service;

import co.kozao.meetreserve.dao.impl.ReservationDaoImpl;



import co.kozao.meetreserve.mapper.ReservationMapper;
import co.kozao.meetreserve.model.Reservation;
import co.kozao.meetreserve.model.ReservationStatus;
import co.kozao.meetreserve.web.dto.response.ReservationResponse;
import co.kozao.meetreserve.web.dto.response.RoomResponse;
import co.kozao.meetreserve.web.dto.resquest.ReservationRequest;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ReservationService {

    private static Logger logger = Logger.getLogger(ReservationService.class.getName());

    private ReservationDaoImpl reservationDao;
    private ReservationMapper  reservationMapper;
    private RoomService roomService;

    public ReservationService() {
        this.reservationDao = new ReservationDaoImpl();
        this.reservationMapper = new ReservationMapper();
        this.roomService = new RoomService();
    }

    public ReservationResponse addReservation(ReservationRequest request) {
        
    	if (request == null) {
    	    throw new IllegalArgumentException(
    	            "Reservation request is required"
    	    );
    	}
    	
    	Reservation reservation = reservationMapper.toReservation(request);
    	
    	reservation.setStatus(ReservationStatus.PENDING);
    	reservation.setCreatedAt(new Date(System.currentTimeMillis()));
    	
    	Boolean conflict = reservationDao.hasConflict(
    			reservation.getRoomId(),
    			(java.sql.Date)reservation.getReservationDate(),
    			reservation.getStartTime(),
    			reservation.getEndTime());
    	
    	if(conflict) {
    		throw new IllegalStateException("Room already booked for this time slot.");
    	}
    	
    	Reservation created = reservationDao.insert(reservation);
    	if(created == null) {
    		return null; // Placeholder for actual implementation
    	}
    	
    	return reservationMapper.toReservationResponse(created, getRoomName(created.getRoomId()));
    }
    
    public ReservationResponse updateReservationStatus(Long id, ReservationStatus status) {
    	
    	Reservation updated = reservationDao.updateStatus(id, status);
    	
    	return updated != null ? reservationMapper.toReservationResponse(updated, getRoomName(updated.getRoomId())) : null;
    }
    
    public ReservationResponse cancelReservation(Long id) {
    	
    	Reservation existing = reservationDao.findById(id);
    	
    	if(existing == null) {
    		throw new IllegalArgumentException("Reservation not found");
    	}
    	
    	Reservation updated = reservationDao.updateStatus(id, ReservationStatus.CANCELLED);
    	return updated != null ? reservationMapper.toReservationResponse(updated, getRoomName(updated.getRoomId())) : null;
    }
    
    public ReservationResponse getReservationById(Long id) {
    	Reservation reservation = reservationDao.findById(id);
    	if(reservation == null) {
    		return null;
    	}
    	return reservation != null ? reservationMapper.toReservationResponse(reservation, getRoomName(reservation.getRoomId())) : null;
    }
    
    

    public List<ReservationResponse> getReservationByUserId(long userId) {
        List<Reservation> userReservation = reservationDao.findByUserId(userId);
        Map<Long, String> roomNames = buildRoomNameMap();
 
        return userReservation.stream()
                .map(r -> reservationMapper.toReservationResponse(r, roomNames.get(r.getRoomId())))
                .collect(Collectors.toList());
    }

    public List<ReservationResponse> getReservations() {
        List<Reservation> reservations = reservationDao.findAll();
        
        Map<Long, String> roomNames = buildRoomNameMap();
        
        return reservations.stream()
                .map(r -> reservationMapper.toReservationResponse(r, roomNames.get(r.getRoomId())))
                .collect(Collectors.toList());
    }
    
    private Map<Long, String> buildRoomNameMap(){
    	return roomService.getAllRooms().stream()
    			.collect(Collectors.toMap(RoomResponse::getId, RoomResponse::getRoomName));
    }
    
    private String getRoomName(Long roomId) {
    	if(roomId == null) {
    		return null;
    	}
    	
    	RoomResponse room = roomService.getRoomById(roomId);
    	return room != null ? room.getRoomName() : null;
    }

}