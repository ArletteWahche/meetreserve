package co.kozao.meetreserve.mapper;

import co.kozao.meetreserve.model.Reservation;
import co.kozao.meetreserve.web.dto.response.ReservationResponse;
import co.kozao.meetreserve.web.dto.resquest.ReservationRequest;

public class ReservationMapper {

    public Reservation toReservation(ReservationRequest request) {
        return new Reservation.Builder()
                .userId(request.getUserId())
                .roomId(request.getRoomId())
                .reservationDate(request.getReservationDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .subject(request.getSubject())
                .build();
    }

    public ReservationResponse toReservationResponse(Reservation reservation) {
        return new ReservationResponse.Builder()
                .id(reservation.getId())
                .userId(reservation.getUserId())
                .roomId(reservation.getRoomId())
                .reservationDate(reservation.getReservationDate())
                .startTime(reservation.getStartTime())
                .endTime(reservation.getEndTime())
                .subject(reservation.getSubject())
                .status(reservation.getStatus())
                .createdAt(reservation.getCreatedAt())
                .deleted(reservation.isDeleted())
                .build();
    }
}
