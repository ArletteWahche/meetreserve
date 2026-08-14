package co.kozao.meetreserve.web.webservice.notification;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import co.kozao.meetreserve.service.ReservationService;
import co.kozao.meetreserve.web.dto.response.ReservationResponse;
import co.kozao.meetreserve.web.dto.response.UserResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/notifications")
public class NotificationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userConnected") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        UserResponse user = (UserResponse) session.getAttribute("userConnected");

        ReservationService reservationService = new ReservationService();
        List<ReservationResponse> myReservations = reservationService.getReservationByUserId(user.getId());

        List<NotificationItem> items = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();

        for (ReservationResponse r : myReservations) {
            String status = String.valueOf(r.getStatus());
            LocalDateTime start = LocalDateTime.of(
                    new java.sql.Date(r.getReservationDate().getTime()).toLocalDate(),
                    r.getStartTime().toLocalTime());

            if ("CONFIRMED".equalsIgnoreCase(status)) {
                items.add(new NotificationItem(
                        "confirm",
                        "Réservation confirmée — " + r.getRoomName(),
                        "Votre créneau du " + formatDate(start) + ", " + r.getStartTime() + "–" + r.getEndTime() + " est confirmé.",
                        start, isRecent(start, now)));
            } else if ("CANCELLED".equalsIgnoreCase(status)) {
                items.add(new NotificationItem(
                        "cancel",
                        "Réservation annulée — " + r.getRoomName(),
                        "Le créneau du " + formatDate(start) + ", " + r.getStartTime() + "–" + r.getEndTime() + " a été annulé.",
                        start, false));
            }

            // Rappel si la réservation démarre dans moins de 2h (et n'est pas annulée)
            if (!"CANCELLED".equalsIgnoreCase(status)) {
                Duration until = Duration.between(now, start);
                if (!until.isNegative() && until.toMinutes() <= 120) {
                    items.add(new NotificationItem(
                            "reminder",
                            "Rappel — " + r.getSubject(),
                            "Votre réunion en salle " + r.getRoomName() + " commence dans " + until.toMinutes() + " minutes.",
                            now, true));
                }
            }
        }

        items.sort(Comparator.comparing(NotificationItem::getTimestamp).reversed());

        request.setAttribute("notifications", items);
        request.getRequestDispatcher("/notifications.jsp").forward(request, response);
    }

    private boolean isRecent(LocalDateTime dateTime, LocalDateTime now) {
        return Duration.between(dateTime, now).toHours() < 24;
    }

    private String formatDate(LocalDateTime dt) {
        return dt.getDayOfMonth() + "/" + dt.getMonthValue();
    }

    public static class NotificationItem {
        private final String type;   // confirm | reminder | cancel
        private final String title;
        private final String text;
        private final LocalDateTime timestamp;
        private final boolean unread;

        public NotificationItem(String type, String title, String text, LocalDateTime timestamp, boolean unread) {
            this.type = type;
            this.title = title;
            this.text = text;
            this.timestamp = timestamp;
            this.unread = unread;
        }

        public String getType() { return type; }
        public String getTitle() { return title; }
        public String getText() { return text; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public boolean isUnread() { return unread; }
    }
}
