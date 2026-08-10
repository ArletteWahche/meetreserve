package co.kozao.meetreserve.web.webservice.dashboard;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@WebServlet("/dashbord/employee")
public class EmployeeDashboardServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(EmployeeDashboardServlet.class.getName());

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
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        UserResponse user = (UserResponse) session.getAttribute("userConnected");

        List<RoomResponse> rooms = roomService.getAllRooms();
        List<ReservationResponse> reservations = reservationService.getReservations();
        List<ReservationResponse> userReservations = reservationService.getReservationByUserId(user.getId());

        List<RoomResponse> roomResponses  = new ArrayList<>();
        if(!rooms.isEmpty() && !reservations.isEmpty()) {
            List<Long> roomReserved = reservations.stream()
                    .map(ReservationResponse::getRoomId)
                    .toList();
            roomResponses = rooms.stream()
                    .filter(room -> !roomReserved.contains(room.getId()))
                    .toList();
        }

        request.setAttribute("user", user);
        request.setAttribute("rooms", roomResponses);
        request.setAttribute("reservations", userReservations.isEmpty() ? Collections.emptyList() : userReservations);

        request.getRequestDispatcher("/employee/dashbord.jsp").forward(request, response);
    }
}