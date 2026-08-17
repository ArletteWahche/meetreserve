package co.kozao.meetreserve.dao.service;

import co.kozao.meetreserve.model.Notification;
import java.util.List;

public interface NotificationDAO {
    Notification insert(Notification notification);
    List<Notification> findByUserId(Long userId);
    Long countUnread(Long userId);
    Boolean markAsRead(Long notificationId, Long userId);
    Boolean markAllAsRead(Long userId);
}