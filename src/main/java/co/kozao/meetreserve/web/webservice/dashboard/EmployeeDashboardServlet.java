package co.kozao.meetreserve.web.webservice.dashboard;

import co.kozao.meetreserve.mapper.RoomMapper;
import co.kozao.meetreserve.model.Reservation;
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
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@WebServlet("/dashbord/employee")
public class EmployeeDashboardServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(EmployeeDashboardServlet.class.getName());

    private RoomService roomService;
    private RoomMapper roomMapper;
    private ReservationService reservationService;

    @Override
    public void init() throws ServletException {
        this.roomService = new RoomService();
        this.roomMapper = new RoomMapper();
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
        List<Long> roomReserved = reservations.stream()
                .map(ReservationResponse::getRoomId)
                .toList();

        List<RoomResponse> roomResponses = rooms.stream()
                .filter(room -> !roomReserved.contains(room.getId()))
                .toList();
        List<ReservationResponse> userReservations = reservationService.getReservationByUserId(user.getId());

        request.setAttribute("user", user);
        request.setAttribute("rooms", roomResponses);
        request.setAttribute("reservations", userReservations);

        request.getRequestDispatcher("/employee/dashboard.jsp").forward(request, response);
    }
}