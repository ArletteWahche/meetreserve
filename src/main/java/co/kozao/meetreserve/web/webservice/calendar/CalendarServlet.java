package co.kozao.meetreserve.web.webservice.calendar;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import co.kozao.meetreserve.service.ReservationService;
import co.kozao.meetreserve.web.dto.response.ReservationResponse;
import co.kozao.meetreserve.web.dto.response.UserResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


@WebServlet("/calendar")
public class CalendarServlet extends HttpServlet {

    
    private static final int[] ROW_HOURS = {9, 11, 14, 16};

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userConnected") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        UserResponse currentUser = (UserResponse) session.getAttribute("userConnected");

        ReservationService reservationService = new ReservationService();
        List<ReservationResponse> allReservations = reservationService.getReservations();

        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        LocalDate friday = monday.plusDays(4);

        List<LocalDate> weekDays = new ArrayList<>();
        List<String> weekDayLabels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            LocalDate day = monday.plusDays(i);
            weekDays.add(day);
            String dayName = day.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
            weekDayLabels.add(capitalize(dayName) + " " + day.getDayOfMonth());
        }

        
        List<CalendarRow> rows = new ArrayList<>();
        for (int hour : ROW_HOURS) {
            List<ReservationResponse> cells = new ArrayList<>();
            for (LocalDate day : weekDays) {
                cells.add(findClosestReservation(allReservations, day, hour));
            }
            rows.add(new CalendarRow(hour + "h", cells));
        }

        request.setAttribute("weekLabel", formatWeekLabel(monday, friday));
        request.setAttribute("weekDayLabels", weekDayLabels);
        request.setAttribute("rows", rows);
        request.setAttribute("currentUserId", currentUser.getId());

        request.getRequestDispatcher("/calendar.jsp").forward(request, response);
    }

   
    private ReservationResponse findClosestReservation(List<ReservationResponse> reservations, LocalDate day, int anchorHour) {
        ReservationResponse best = null;
        int bestDiff = Integer.MAX_VALUE;

        for (ReservationResponse r : reservations) {
            if ("CANCELLED".equalsIgnoreCase(String.valueOf(r.getStatus()))) continue;
            if (!toLocalDate(r.getReservationDate()).isEqual(day)) continue;

            LocalTime start = r.getStartTime().toLocalTime();
            int diff = Math.abs(start.getHour() - anchorHour);
            if (diff < bestDiff && diff <= 1) { 
                bestDiff = diff;
                best = r;
            }
        }
        return best;
    }

    private java.time.LocalDate toLocalDate(java.util.Date date) {
        return new java.sql.Date(date.getTime()).toLocalDate();
    }

    private String capitalize(String s) {
        return s.isEmpty() ? s : Character.toUpperCase(s.charAt(0)) + s.substring(1);
    }

    private String formatWeekLabel(LocalDate monday, LocalDate friday) {
        String moisFin = friday.getMonth().getDisplayName(TextStyle.FULL, Locale.FRENCH);
        return "Semaine du " + monday.getDayOfMonth() + " au " + friday.getDayOfMonth() + " " + moisFin + " " + friday.getYear();
    }

    public static class CalendarRow {
        private final String label;
        private final List<ReservationResponse> cells;

        public CalendarRow(String label, List<ReservationResponse> cells) {
            this.label = label;
            this.cells = cells;
        }

        public String getLabel() { return label; }
        public List<ReservationResponse> getCells() { return cells; }
    }
}
