package co.kozao.meetreserve.web.webservice.dashboard;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.model.ReservationStatus;
import co.kozao.meetreserve.service.NotificationService;
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

@WebServlet("/dashboard/manager")
public class ManagerDashboardServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ManagerDashboardServlet.class.getName());

    private ReservationService reservationService;
    private RoomService roomService;
    private NotificationService notificationService;

    @Override
    public void init() throws ServletException {
        this.reservationService = new ReservationService();
        this.roomService = new RoomService();
        this.notificationService = new NotificationService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserResponse currentUser = getConnectedUser(request);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (!canManage(currentUser)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Reserved to managers.");
            return;
        }

        List<ReservationResponse> reservations = reservationService.getReservations();
        List<RoomResponse> rooms = roomService.getAllRooms();

        Map<Long, String> roomNames = rooms.stream()
                .collect(Collectors.toMap(RoomResponse::getId, RoomResponse::getRoomName, (a, b) -> a));

        long pendingCount = reservations.stream()
                .filter(r -> ReservationStatus.PENDING.equals(r.getStatus()))
                .count();

        request.setAttribute("currentUser", currentUser);
        request.setAttribute("reservations", reservations);
        request.setAttribute("rooms", rooms);
        request.setAttribute("roomNames", roomNames);
        request.setAttribute("pendingCount", pendingCount);

        request.getRequestDispatcher("/manager/dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserResponse currentUser = getConnectedUser(request);
        if (currentUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        if (!canManage(currentUser)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Reserved to managers.");
            return;
        }

        String idParam = request.getParameter("id");
        String action = request.getParameter("action");

        if (idParam == null || idParam.isBlank() || action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing id or action.");
            return;
        }

        try {
            long reservationId = Long.parseLong(idParam);

            switch (action) {
                case "confirm":
                    reservationService.updateReservationStatus(reservationId, ReservationStatus.CONFIRMED);
                    break;
                case "cancel":
                    reservationService.updateReservationStatus(reservationId, ReservationStatus.CANCELLED);
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
                    return;
            }

            response.sendRedirect(request.getContextPath() + "/dashboard/manager");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid reservation id.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            LOG.warning("Could not update reservation status: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/dashboard/manager?error=" + e.getMessage());
        }
        
        Long reservationId = Long.parseLong(idParam);
        ReservationResponse updated;

        switch (action) {
            case "confirm":
                updated = reservationService.updateReservationStatus(reservationId, ReservationStatus.CONFIRMED);
                break;
            case "cancel":
                updated = reservationService.updateReservationStatus(reservationId, ReservationStatus.CANCELLED);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
                return;
        }

        if (updated != null) {
            String roomName = roomService.getRoomById(updated.getRoomId()) != null
                    ? roomService.getRoomById(updated.getRoomId()).getRoomName() : null;
            notificationService.notifyReservationStatusChange(updated, roomName);
        }

        response.sendRedirect(request.getContextPath() + "/dashboard/manager");
        
    }
    
    

    private UserResponse getConnectedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        Object userObj = session.getAttribute("userConnected");
        return (userObj instanceof UserResponse user) ? user : null;
    }

    private boolean canManage(UserResponse user) {
        return user != null && (
                Role.MANAGER.name().equals(user.getRole()) ||
                Role.ADMINISTRATOR.name().equals(user.getRole())
        );
    }
}