package co.kozao.meetreserve.mapper;

import co.kozao.meetreserve.model.Notification;
import co.kozao.meetreserve.web.dto.response.NotificationResponse;

public class NotificationMapper {

    public NotificationResponse toResponse(Notification notification) {
        return new NotificationResponse.Builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .status(notification.getStatus())
                .reservationId(notification.getReservationId())
                .read(notification.isRead())
                .createdAt(notification.getCreatedAt())
                .build();
    }
}