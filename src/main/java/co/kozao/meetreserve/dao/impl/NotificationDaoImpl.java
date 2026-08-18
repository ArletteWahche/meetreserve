package co.kozao.meetreserve.dao.impl;

import co.kozao.meetreserve.dao.database.DatabaseConnection;
import co.kozao.meetreserve.dao.query.NotificationSqlQueries;
import co.kozao.meetreserve.dao.service.NotificationDAO;
import co.kozao.meetreserve.model.Notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotificationDaoImpl implements NotificationDAO {

    private static final Logger logger = Logger.getLogger(NotificationDaoImpl.class.getName());

    @Override
    public Notification insert(Notification notification) {
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(NotificationSqlQueries.SQL_INSERT_NOTIFICATION);

            ps.setString(1, notification.getMessage());
            ps.setString(2, notification.getStatus());
            ps.setLong(3, notification.getReservationId());
            ps.setLong(4, notification.getUserId());
            ps.setBoolean(5, notification.isRead());
            ps.setDate(6, notification.getCreatedAt());

            int rows = ps.executeUpdate();
            return rows > 0 ? notification : null;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while inserting the notification.", e);
            return null;
        }
    }

    @Override
    public List<Notification> findByUserId(Long userId) {
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(NotificationSqlQueries.SQL_FIND_BY_USER_ID);
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();

            List<Notification> notifications = new ArrayList<>();
            while (rs.next()) {
                notifications.add(mapRow(rs));
            }
            return notifications;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while retrieving notifications.", e);
        }
        return Collections.emptyList();
    }

    @Override
    public Long countUnread(Long userId) {
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(NotificationSqlQueries.SQL_COUNT_UNREAD);
            ps.setLong(1, userId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while counting unread notifications.", e);
        }
        return 0L;
    }

    @Override
    public Boolean markAsRead(Long notificationId, Long userId) {
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(NotificationSqlQueries.SQL_MARK_AS_READ);
            ps.setLong(1, notificationId);
            ps.setLong(2, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while marking notification as read.", e);
        }
        return false;
    }

    @Override
    public Boolean markAllAsRead(Long userId) {
        try {
            PreparedStatement ps = DatabaseConnection.getInstance()
                    .prepareStatement(NotificationSqlQueries.SQL_MARK_ALL_AS_READ);
            ps.setLong(1, userId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error while marking all notifications as read.", e);
        }
        return false;
    }

    private Notification mapRow(ResultSet rs) throws SQLException {
        return new Notification.Builder()
                .id(rs.getLong("id_notification"))
                .message(rs.getString("message"))
                .status(rs.getString("status"))
                .reservationId(rs.getLong("id_reservation"))
                .userId(rs.getLong("id_user"))
                .read(rs.getBoolean("is_read"))
                .createdAt(rs.getDate("created_at"))
                .build();
    }
}
