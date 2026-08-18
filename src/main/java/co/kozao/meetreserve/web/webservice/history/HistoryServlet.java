package co.kozao.meetreserve.web.webservice.history;

import co.kozao.meetreserve.model.ReservationStatus;
import co.kozao.meetreserve.service.ReservationService;
import co.kozao.meetreserve.web.dto.response.ReservationResponse;
import co.kozao.meetreserve.web.dto.response.UserResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {

    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        this.reservationService = new ReservationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userConnected") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        UserResponse user = (UserResponse) session.getAttribute("userConnected");

        LocalDate today = LocalDate.now();

        List<ReservationResponse> all = reservationService.getReservationByUserId(user.getId());

        List<ReservationResponse> history = all.stream()
                .filter(r -> isPast(r, today) || r.getStatus() == ReservationStatus.CANCELLED)
                .sorted(Comparator.comparing(ReservationResponse::getReservationDate).reversed())
                .toList();

        request.setAttribute("user", user);
        request.setAttribute("reservations", history);
        request.getRequestDispatcher("/history.jsp").forward(request, response);
    }

    private boolean isPast(ReservationResponse r, LocalDate today) {
        if (r.getReservationDate() == null) return false;
        LocalDate date = ((java.sql.Date) r.getReservationDate()).toLocalDate();
        return date.isBefore(today);
    }
}