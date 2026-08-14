package co.kozao.meetreserve.web.webservice.dashboard;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

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

@WebServlet("/dashboard/employee")
public class EmployeeDashboardServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(EmployeeDashboardServlet.class.getName());

   
    private static final LocalTime DAY_START = LocalTime.of(8, 0);
    private static final LocalTime DAY_END = LocalTime.of(18, 0);
    private static final long DAY_MINUTES = ChronoUnit.MINUTES.between(DAY_START, DAY_END);

    private RoomService roomService;
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        this.roomService = new RoomService();
        this.reservationService = new ReservationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userConnected") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        UserResponse user = (UserResponse) session.getAttribute("userConnected");

        List<RoomResponse> allRooms = roomService.getAllRooms();
        List<ReservationResponse> allReservations = reservationService.getReservations();
        List<ReservationResponse> myReservations = reservationService.getReservationByUserId(user.getId());

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        // ---- Réservations d'aujourd'hui (toutes salles confondues), hors annulées ----
        List<ReservationResponse> todayReservations = allReservations.stream()
                .filter(r -> !"CANCELLED".equalsIgnoreCase(String.valueOf(r.getStatus())))
                .filter(r -> toLocalDate(r.getReservationDate()).isEqual(today))
                .toList();

        // ---- Salles occupées exactement maintenant ----
        Set<Long> occupiedNowRoomIds = new HashSet<>();
        for (ReservationResponse r : todayReservations) {
            LocalDateTime start = LocalDateTime.of(today, r.getStartTime().toLocalTime());
            LocalDateTime end = LocalDateTime.of(today, r.getEndTime().toLocalTime());
            if (!now.isBefore(start) && now.isBefore(end)) {
                occupiedNowRoomIds.add(r.getRoomId());
            }
        }
        int totalRooms = allRooms.size();
        int occupiedNow = occupiedNowRoomIds.size();
        int availableNow = totalRooms - occupiedNow;

        // ---- Mes réservations cette semaine (hors annulées) ----
        LocalDate weekEnd = today.plusDays(6);
        long reservationsThisWeek = myReservations.stream()
                .filter(r -> !"CANCELLED".equalsIgnoreCase(String.valueOf(r.getStatus())))
                .filter(r -> {
                    LocalDate d = toLocalDate(r.getReservationDate());
                    return !d.isBefore(today) && !d.isAfter(weekEnd);
                })
                .count();

        // ---- Ma prochaine réservation à venir ----
        ReservationResponse nextReservation = myReservations.stream()
                .filter(r -> !"CANCELLED".equalsIgnoreCase(String.valueOf(r.getStatus())))
                .filter(r -> {
                    LocalDateTime start = LocalDateTime.of(toLocalDate(r.getReservationDate()), r.getStartTime().toLocalTime());
                    return !start.isBefore(now);
                })
                .sorted(Comparator.comparing((ReservationResponse r) -> toLocalDate(r.getReservationDate()))
                        .thenComparing(r -> r.getStartTime().toLocalTime()))
                .findFirst()
                .orElse(null);

        // ---- Taux d'occupation moyen sur la plage 8h-18h ----
        long bookedMinutes = todayReservations.stream()
                .mapToLong(r -> ChronoUnit.MINUTES.between(
                        clamp(r.getStartTime().toLocalTime()),
                        clamp(r.getEndTime().toLocalTime())))
                .filter(m -> m > 0)
                .sum();
        int occupancyRate = totalRooms == 0 ? 0
                : (int) Math.round((bookedMinutes * 100.0) / (totalRooms * (double) DAY_MINUTES));

        // ---- Frise d'occupation par salle ----
        List<RoomTimeline> timelines = new ArrayList<>();
        for (RoomResponse room : allRooms) {
            List<TimelineSlot> slots = new ArrayList<>();
            for (ReservationResponse r : todayReservations) {
                if (!r.getRoomId().equals(room.getId())) {
                    continue;
                }
                double leftPct = percentOfDay(r.getStartTime().toLocalTime());
                double widthPct = percentOfDay(r.getEndTime().toLocalTime()) - leftPct;
                if (widthPct <= 0) {
                    continue;
                }
                boolean mine = r.getUserId().equals(user.getId());
                slots.add(new TimelineSlot(leftPct, widthPct, mine ? "mine" : "busy"));
            }
            timelines.add(new RoomTimeline(room.getRoomName(), room.getCapacity(), slots));
        }

        request.setAttribute("user", user);
        request.setAttribute("rooms", allRooms);
        request.setAttribute("reservations", myReservations);

        request.setAttribute("totalRooms", totalRooms);
        request.setAttribute("availableNow", availableNow);
        request.setAttribute("occupiedNow", occupiedNow);
        request.setAttribute("reservationsThisWeek", reservationsThisWeek);
        request.setAttribute("nextReservation", nextReservation);
        request.setAttribute("occupancyRate", occupancyRate);
        request.setAttribute("timelines", timelines);

        request.getRequestDispatcher("/employee/dashboard.jsp").forward(request, response);
    }

   
    private LocalDate toLocalDate(java.util.Date date) {
        return new Date(date.getTime()).toLocalDate();
    }

  
    private double percentOfDay(LocalTime time) {
        LocalTime clamped = clamp(time);
        long minutesFromStart = ChronoUnit.MINUTES.between(DAY_START, clamped);
        return Math.max(0, Math.min(100, (minutesFromStart * 100.0) / DAY_MINUTES));
    }

    private LocalTime clamp(LocalTime time) {
        if (time.isBefore(DAY_START)) return DAY_START;
        if (time.isAfter(DAY_END)) return DAY_END;
        return time;
    }

    
    public static class RoomTimeline {
        private final String roomName;
        private final Long capacity;
        private final List<TimelineSlot> slots;

        public RoomTimeline(String roomName, Long capacity, List<TimelineSlot> slots) {
            this.roomName = roomName;
            this.capacity = capacity;
            this.slots = slots;
        }

        public String getRoomName() { return roomName; }
        public Long getCapacity() { return capacity; }
        public List<TimelineSlot> getSlots() { return slots; }
    }

    public static class TimelineSlot {
        private final double left;
        private final double width;
        private final String cssClass; 

        public TimelineSlot(double left, double width, String cssClass) {
            this.left = left;
            this.width = width;
            this.cssClass = cssClass;
        }

        public double getLeft() { return left; }
        public double getWidth() { return width; }
        public String getCssClass() { return cssClass; }
    }
}
