package co.kozao.meetreserve.dao.query;

public class NotificationSqlQueries {

    public NotificationSqlQueries() {
    }

    public static final String SQL_INSERT_NOTIFICATION =
            "INSERT INTO notification (message, status, id_reservation, id_user, is_read, created_at) " +
            "VALUES (?, ?, ?, ?, ?, ?)";

    public static final String SQL_FIND_BY_USER_ID =
            "SELECT id_notification, message, status, id_reservation, id_user, is_read, created_at " +
            "FROM notification " +
            "WHERE id_user = ? " +
            "ORDER BY created_at DESC, id_notification DESC";

    public static final String SQL_COUNT_UNREAD =
            "SELECT COUNT(*) FROM notification WHERE id_user = ? AND is_read = false";

    public static final String SQL_MARK_AS_READ =
            "UPDATE notification SET is_read = true WHERE id_notification = ? AND id_user = ?";

    public static final String SQL_MARK_ALL_AS_READ =
            "UPDATE notification SET is_read = true WHERE id_user = ? AND is_read = false";
}