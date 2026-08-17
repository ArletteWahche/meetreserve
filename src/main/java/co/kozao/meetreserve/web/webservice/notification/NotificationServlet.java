package co.kozao.meetreserve.web.webservice.notification;

import co.kozao.meetreserve.service.NotificationService;
import co.kozao.meetreserve.web.dto.response.NotificationResponse;
import co.kozao.meetreserve.web.dto.response.UserResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/notification")
public class NotificationServlet extends HttpServlet {

    private NotificationService notificationService;

    @Override
    public void init() throws ServletException {
        this.notificationService = new NotificationService();
    }

    private UserResponse getConnectedUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userConnected") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return null;
        }
        return (UserResponse) session.getAttribute("userConnected");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserResponse user = getConnectedUser(request, response);
        if (user == null) return;

        List<NotificationResponse> notification = notificationService.getNotification(user.getId());

        request.setAttribute("user", user);
        request.setAttribute("notification", notification);
        request.getRequestDispatcher("/notification.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserResponse user = getConnectedUser(request, response);
        if (user == null) return;

        String action = request.getParameter("action");
        if ("markAllRead".equals(action)) {
            notificationService.markAllAsRead(user.getId());
        } else {
            String idParam = request.getParameter("id");
            if (idParam != null && !idParam.isBlank()) {
                try {
                    notificationService.markAsRead(Long.parseLong(idParam), user.getId());
                } catch (NumberFormatException ignored) {
                }
            }
        }
        response.sendRedirect(request.getContextPath() + "/notification");
    }
}