package co.kozao.meetreserve.web.webservice.reservation;

import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.service.ReservationService;
import co.kozao.meetreserve.service.RoomService;
import co.kozao.meetreserve.web.dto.resquest.ReservationRequest;
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
import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@WebServlet({"/reservations", "/reservation/new"})
public class ReservationServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(ReservationServlet.class.getName());

    private ReservationService reservationService;
    private RoomService roomService;

    @Override
    public void init() throws ServletException {
        this.reservationService = new ReservationService();
        this.roomService = new RoomService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserResponse user = getConnectedUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String path = request.getServletPath();

        // ---- Formulaire de nouvelle réservation ----
        if ("/reservation/new".equals(path)) {
            List<RoomResponse> rooms = roomService.getAllRooms();
            request.setAttribute("rooms", rooms);
            request.getRequestDispatcher("/reservation-form.jsp").forward(request, response);
            return;
        }

        // ---- Liste de mes réservations ----
        List<ReservationResponse> reservations = reservationService.getReservationByUserId(user.getId());
        request.setAttribute("user", user);
        request.setAttribute("reservations", reservations);
        request.getRequestDispatcher("/reservations.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserResponse user = getConnectedUser(request);
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String action = request.getParameter("action");
        if (action == null || action.isBlank()) {
            action = "create";
        }

        switch (action) {
            case "create":
                handleCreate(request, response, user);
                break;
            case "cancel":
                handleCancel(request, response, user);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response, UserResponse user)
            throws IOException {
        try {
            ReservationRequest reservationRequest = buildRequestFromParams(request, user.getId());
            ReservationResponse created = reservationService.addReservation(reservationRequest);

            if (created == null) {
                response.sendRedirect(request.getContextPath() + "/reservation/new?error=creation_failed");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/reservations");

        } catch (IllegalStateException e) {
            LOG.warning("Reservation conflict: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/reservation/new?error=conflict");

        } catch (IllegalArgumentException e) {
            LOG.warning("Invalid reservation data: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/reservation/new?error=invalid_data");
        }
    }

    private void handleCancel(HttpServletRequest request, HttpServletResponse response, UserResponse user)
            throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing reservation id.");
            return;
        }

        try {
            long reservationId = Long.parseLong(idParam);
            ReservationResponse reservation = reservationService.getReservationById(reservationId);

            if (reservation == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Reservation not found.");
                return;
            }

            if (!canAccess(user, reservation)) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "You cannot cancel this reservation.");
                return;
            }

            reservationService.cancelReservation(reservationId);
            response.sendRedirect(request.getContextPath() + "/reservations");

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid reservation id.");
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Reservation not found.");
        }
    }

    private ReservationRequest buildRequestFromParams(HttpServletRequest request, Long userId) {
        String roomIdParam = request.getParameter("roomId");
        String dateParam = request.getParameter("reservationDate");
        String startParam = request.getParameter("startTime");
        String endParam = request.getParameter("endTime");
        String subject = request.getParameter("subject");

        boolean missingRequiredFields = roomIdParam == null || dateParam == null || startParam == null
                || endParam == null || subject == null || subject.isBlank();

        if (missingRequiredFields) {
            throw new IllegalArgumentException("All fields are required.");
        }

        Long roomId;
        Date reservationDate;
        Time startTime;
        Time endTime;
        try {
            roomId = Long.parseLong(roomIdParam);
            reservationDate = java.sql.Date.valueOf(dateParam);
            startTime = Time.valueOf(startParam + ":00");
            endTime = Time.valueOf(endParam + ":00");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid room, date or time format.");
        }

        return new ReservationRequest.Builder()
                .userId(userId)
                .roomId(roomId)
                .reservationDate(reservationDate)
                .startTime(startTime)
                .endTime(endTime)
                .subject(subject)
                .build();
    }

    private UserResponse getConnectedUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        Object userObj = session.getAttribute("userConnected");
        return (userObj instanceof UserResponse) ? (UserResponse) userObj : null;
    }

    private boolean canAccess(UserResponse user, ReservationResponse reservation) {
        if (reservation.getUserId() != null && reservation.getUserId().equals(user.getId())) {
            return true;
        }
        Role role = Role.valueOf(user.getRole());
        return role == Role.MANAGER || role == Role.ADMINISTRATOR;
    }
}