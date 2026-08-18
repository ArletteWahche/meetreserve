package co.kozao.meetreserve.web.webservice.room;

import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.service.RoomService;
import co.kozao.meetreserve.web.dto.response.RoomResponse;
import co.kozao.meetreserve.web.dto.response.UserResponse;
import co.kozao.meetreserve.web.dto.resquest.RoomRequest;
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
    private static final String ERROR_KEY = "error";
    private static final String ROOM_PATH = "/rooms";

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
                request.setAttribute(ERROR_KEY, "Error during the process of creating room. Please try again");
                response.sendRedirect(request.getContextPath() + ROOM_PATH);
                return;
            }

            response.sendRedirect(request.getContextPath() + ROOM_PATH);

        } catch (IllegalArgumentException e) {
            LOG.warning("Invalid room data: " + e.getMessage());
            request.setAttribute(ERROR_KEY, e.getMessage());
            response.sendRedirect(request.getContextPath() + ROOM_PATH);
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
            RoomResponse update = roomService.updateRoom(roomId, roomRequest);

            response.sendRedirect(request.getContextPath() + "/rooms?id=" + roomId);

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid room id.");
        } catch (IllegalArgumentException e) {
            LOG.warning("Invalid room data: " + e.getMessage());
            request.setAttribute(ERROR_KEY, e.getMessage());
            response.sendRedirect(request.getContextPath() + ROOM_PATH);
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
            boolean deleted = roomService.deletedRoom(roomId);
            request.setAttribute(!deleted ? ERROR_KEY : "Success", deleted ? "failed to delete room" : "Deleted Successfully");
            response.sendRedirect(request.getContextPath() + ROOM_PATH);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid room id.");
        } catch (IllegalArgumentException e) {
            LOG.warning("Room not found for deletion: " + e.getMessage());
            request.setAttribute(ERROR_KEY, "Room not found");
            response.sendRedirect(request.getContextPath() + ROOM_PATH);
        }
    }

    private RoomRequest buildRequestFromParams(HttpServletRequest request) {
        String roomName = request.getParameter("roomName");
        String capacityParam = request.getParameter("capacity");
        String location = request.getParameter("location");
        String description = request.getParameter("description");
        String availableParam = request.getParameter("available");
        String imageUrl = request.getParameter("imageUrl");

        if (roomName == null || roomName.isBlank()) {
            throw new IllegalArgumentException("Room name is required.");
        }

        if (capacityParam == null || capacityParam.isBlank()) {
            throw new IllegalArgumentException("Capacity is required.");
        }

        Long capacity;
        try {
            capacity = Long.parseLong(capacityParam);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Capacity must be a valid number.");
        }

        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero.");
        }

        if (location == null || location.isBlank()) {
            throw new IllegalArgumentException("Location is required.");
        }

        
        
        return new RoomRequest.Builder()
                .roomName(roomName.trim())
                .capacity(capacity)
                .location(location.trim())
                .description(description == null ? null : description.trim())
                .available(availableParam == null || Boolean.parseBoolean(availableParam))
                .imageUrl(imageUrl == null || imageUrl.isBlank() ? null : imageUrl.trim())
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