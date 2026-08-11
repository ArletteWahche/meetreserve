package co.kozao.meetreserve.web.webservice.room;

import co.kozao.meetreserve.model.Role;

import co.kozao.meetreserve.service.RoomService;
import co.kozao.meetreserve.web.dto.resquest.RoomRequest;
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

@WebServlet("/rooms")
public class RoomServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(RoomServlet.class.getName());

    private RoomService roomService;

    @Override
    public void init() throws ServletException {
        this.roomService = new RoomService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");

        // ---- Détail d'une salle ----
        if (idParam != null && !idParam.isBlank()) {
            try {
                long roomId = Long.parseLong(idParam);
                RoomResponse room = roomService.getRoomById(roomId);

                if (room == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Room not found.");
                    return;
                }

                request.setAttribute("room", room);
                request.getRequestDispatcher("/room-detail.jsp").forward(request, response);
                return;

            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid room id.");
                return;
            }
        }

        // ---- Liste (toutes ou filtrées par disponibilité) ----
        String availableParam = request.getParameter("available");
        List<RoomResponse> rooms = "true".equalsIgnoreCase(availableParam)
                ? roomService.getAvailableRooms()
                : roomService.getAllRooms();

        request.setAttribute("rooms", rooms);
        request.getRequestDispatcher("/rooms.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Seul un administrateur peut créer/modifier/supprimer une salle
        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Only administrators can manage rooms.");
            return;
        }

        String action = request.getParameter("action");
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing action parameter.");
            return;
        }

        switch (action) {
            case "create":
                handleCreate(request, response);
                break;
            case "update":
                handleUpdate(request, response);
                break;
            case "delete":
                handleDelete(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
        }
    }

    private void handleCreate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            RoomRequest roomRequest = buildRequestFromParams(request);
            RoomResponse created = roomService.addRoom(roomRequest);

            if (created == null) {
                response.sendRedirect(request.getContextPath() + "/rooms?error=creation_failed");
                return;
            }

            response.sendRedirect(request.getContextPath() + "/rooms");

        } catch (IllegalArgumentException e) {
            LOG.warning("Invalid room data: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/rooms?error=" + e.getMessage());
        }
    }

    private void handleUpdate(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing room id.");
            return;
        }

        try {
            long roomId = Long.parseLong(idParam);
            RoomRequest roomRequest = buildRequestFromParams(request);
            RoomResponse updated = roomService.updateRoom(roomId, roomRequest);

            response.sendRedirect(request.getContextPath() + "/rooms?id=" + roomId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid room id.");
        } catch (IllegalArgumentException e) {
            LOG.warning("Invalid room data: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/rooms?error=" + e.getMessage());
        }
    }

    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing room id.");
            return;
        }

        try {
            long roomId = Long.parseLong(idParam);
            boolean deleted = roomService.deleteRoom(roomId);
            response.sendRedirect(request.getContextPath() + "/rooms" + (deleted ? "" : "?error=delete_failed"));

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid room id.");
        } catch (IllegalArgumentException e) {
            LOG.warning("Room not found for deletion: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/rooms?error=not_found");
        }
    }

    private RoomRequest buildRequestFromParams(HttpServletRequest request) {
        String capacityParam = request.getParameter("capacity");
        String availableParam = request.getParameter("available");

        Long capacity;
        try {
            capacity = Long.parseLong(capacityParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Capacity must be a valid number.");
        }

        return new RoomRequest.Builder()
                .nameRoom(request.getParameter("nameRoom"))
                .capacity(capacity)
                .location(request.getParameter("location"))
                .description(request.getParameter("description"))
                .available(availableParam == null || Boolean.parseBoolean(availableParam))
                .build();
    }

    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return false;
        }
        Object userObj = session.getAttribute("userConnected");
        if (!(userObj instanceof UserResponse user)) {
            return false;
        }
        return Role.ADMINISTRATOR.name().equals(user.getRole());
    }
}