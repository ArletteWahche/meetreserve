package co.kozao.meetreserve.service;

import co.kozao.meetreserve.dao.impl.NotificationDaoImpl;
import co.kozao.meetreserve.mapper.NotificationMapper;
import co.kozao.meetreserve.model.Notification;
import co.kozao.meetreserve.model.ReservationStatus;
import co.kozao.meetreserve.web.dto.response.NotificationResponse;
import co.kozao.meetreserve.web.dto.response.ReservationResponse;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class NotificationService {

    private static final int MAX_MESSAGE_LENGTH = 50;

    private final NotificationDaoImpl notificationDao;
    private final NotificationMapper mapper;

    public NotificationService() {
        this.notificationDao = new NotificationDaoImpl();
        this.mapper = new NotificationMapper();
    }

    public void notifyReservationStatusChange(ReservationResponse reservation, String roomName) {
        if (reservation == null || reservation.getUserId() == null) {
            return;
        }

        String verb = reservation.getStatus() == ReservationStatus.CONFIRMED ? "confirmed" : "cancelled";
        String room = (roomName == null || roomName.isBlank()) ? "Room" : roomName;

        String message = truncate(room + " reservation " + verb, MAX_MESSAGE_LENGTH);

        Notification notification = new Notification.Builder()
                .message(message)
                .status(reservation.getStatus().name())
                .reservationId(reservation.getId())
                .userId(reservation.getUserId())
                .read(false)
                .createdAt(new Date(System.currentTimeMillis()))
                .build();

        notificationDao.insert(notification);
    }

    public List<NotificationResponse> getNotification(Long userId) {
        return notificationDao.findByUserId(userId).stream()
                .map(mapper::toResponse)
                .collect(Collectors.toList());
    }

    public Long countUnread(Long userId) {
        return notificationDao.countUnread(userId);
    }

    public Boolean markAsRead(Long notificationId, Long userId) {
        return notificationDao.markAsRead(notificationId, userId);
    }

    public Boolean markAllAsRead(Long userId) {
        return notificationDao.markAllAsRead(userId);
    }

    private String truncate(String text, int maxLength) {
        if (text == null) return null;
        return text.length() <= maxLength ? text : text.substring(0, maxLength);
    }
}