package co.kozao.meetreserve.web.webservice.dashboard;

import co.kozao.meetreserve.model.ReservationStatus;
import co.kozao.meetreserve.service.ReservationService;
import co.kozao.meetreserve.service.RoomService;
import co.kozao.meetreserve.web.dto.response.ReservationResponse;
import co.kozao.meetreserve.web.dto.response.RoomResponse;
import co.kozao.meetreserve.web.dto.response.UserResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.sql.Time;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

@WebServlet("/dashboard/employee")
public class EmployeeDashboardServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(EmployeeDashboardServlet.class.getName());

    private static final int WINDOW_START_MIN = 8 * 60;
    private static final int WINDOW_END_MIN = 18 * 60;
    private static final int WINDOW_SPAN_MIN = WINDOW_END_MIN - WINDOW_START_MIN;

    private RoomService roomService;
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        this.roomService = new RoomService();
        this.reservationService = new ReservationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, java.io.IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userConnected") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        UserResponse user = (UserResponse) session.getAttribute("userConnected");

        List<RoomResponse> allRooms = roomService.getAllRooms();
        List<ReservationResponse> allReservations = reservationService.getReservations();
        List<ReservationResponse> myReservations = reservationService.getReservationByUserId(user.getId());

        LocalDate today = LocalDate.now();

        // ---- Salles disponibles (pour "Nouvelle réservation" / liste courante) ----
        List<RoomResponse> availableRooms = new ArrayList<>();
        if (!allRooms.isEmpty()) {
            List<Long> roomsBusyNow = allReservations.stream()
                    .filter(r -> r.getStatus() != ReservationStatus.CONFIRMED)
                    .filter(r -> isToday(r, today) && coversNow(r))
                    .map(ReservationResponse::getRoomId)
                    .toList();
            availableRooms = allRooms.stream()
                    .filter(RoomResponse::getAvailable)
                    .filter(room -> !roomsBusyNow.contains(room.getId()))
                    .toList();
        }

        // ---- Frise d'occupation du jour, par salle ----
        List<Map<String, Object>> occupancyRows = new ArrayList<>();
        for (RoomResponse room : allRooms) {
            List<Map<String, Object>> slots = new ArrayList<>();
            for (ReservationResponse r : allReservations) {
                if (!room.getId().equals(r.getRoomId())) continue;
                if (r.getStatus() == ReservationStatus.CANCELLED) continue;
                if (!isToday(r, today)) continue;

                int startMin = toMinutes(r.getStartTime());
                int endMin = toMinutes(r.getEndTime());

                double leftPct = clampPct((startMin - WINDOW_START_MIN) * 100.0 / WINDOW_SPAN_MIN);
                double widthPct = clampPct((endMin - startMin) * 100.0 / WINDOW_SPAN_MIN);
                widthPct = Math.min(widthPct, 100 - leftPct);

                boolean mine = r.getUserId() != null && r.getUserId().equals(user.getId());

                Map<String, Object> slot = new LinkedHashMap<>();
                slot.put("left", leftPct);
                slot.put("width", widthPct);
                slot.put("cssClass", mine ? "mine" : "busy");
                slots.add(slot);
            }

            Map<String, Object> row = new LinkedHashMap<>();
            row.put("name", room.getRoomName());
            row.put("capacity", room.getCapacity());
            row.put("slots", slots);
            occupancyRows.add(row);
        }

        // ---- Stat : réservations de l'utilisateur cette semaine ----
        WeekFields wf = WeekFields.of(Locale.FRANCE);
        int currentWeek = today.get(wf.weekOfWeekBasedYear());
        Long reservationsThisWeek = myReservations.stream()
                .filter(r -> r.getStatus() != ReservationStatus.CANCELLED)
                .filter(r -> {
                    LocalDate d = ((java.sql.Date) r.getReservationDate()).toLocalDate();
                    return d.get(wf.weekOfWeekBasedYear()) == currentWeek && d.getYear() == today.getYear();
                })
                .count();

        // ---- Stat : salles disponibles maintenant / total ----
        long roomsAvailableNow = availableRooms.size();
        long totalRooms = allRooms.size();

        request.setAttribute("user", user);
        request.setAttribute("rooms", availableRooms);
        request.setAttribute("reservations", myReservations.isEmpty() ? Collections.emptyList() : myReservations);
        request.setAttribute("occupancyRows", occupancyRows);
        request.setAttribute("roomsAvailableNow", roomsAvailableNow);
        request.setAttribute("totalRooms", totalRooms);
        request.setAttribute("reservationsThisWeek", reservationsThisWeek);

        request.getRequestDispatcher("/employee/dashboard.jsp").forward(request, response);
    }

    private boolean isToday(ReservationResponse r, LocalDate today) {
        if (r.getReservationDate() == null) return false;
        return ((java.sql.Date) r.getReservationDate()).toLocalDate().isEqual(today);
    }

    private boolean coversNow(ReservationResponse r) {
        int nowMin = toMinutes(new Time(System.currentTimeMillis()));
        return toMinutes(r.getStartTime()) <= nowMin && nowMin < toMinutes(r.getEndTime());
    }

    private int toMinutes(Time t) {
        if (t == null) return 0;
        return t.getHours() * 60 + t.getMinutes();
    }

    private double clampPct(double value) {
        return Math.max(0, Math.min(100, value));
    }
}
