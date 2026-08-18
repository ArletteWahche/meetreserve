package co.kozao.meetreserve.web.webservice.dashboard;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.service.UserService;
import co.kozao.meetreserve.web.dto.resquest.UserRequest;
import co.kozao.meetreserve.web.dto.response.UserResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/users")
public class UserManagementServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    private UserResponse requireAdmin(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userConnected") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return null;
        }
        UserResponse user = (UserResponse) session.getAttribute("userConnected");
        if (!Role.ADMINISTRATOR.name().equals(user.getRole())) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Administrator access only.");
            return null;
        }
        return user;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserResponse currentUser = requireAdmin(request, response);
        if (currentUser == null) {
            return;
        }

        List<String> roles = Arrays.stream(Role.values()).map(Role::name).toList();
        List<UserResponse> users = userService.getAllUsers();

        request.setAttribute("roles", roles);
        request.setAttribute("users", users);
        request.setAttribute("currentUser", currentUser);
        request.getRequestDispatcher("/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserResponse currentUser = requireAdmin(request, response);
        if (currentUser == null) {
            return;
        }

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String roleParam = request.getParameter("role");

        Role role;
        try {
            role = Role.valueOf(roleParam);
        } catch (IllegalArgumentException | NullPointerException e) {
            response.sendRedirect(request.getContextPath() + "/users?error=invalid_role");
            return;
        }

        var validation = userService.validateRegistration(name, surname, email, password);
        if (!validation.isValid()) {
            response.sendRedirect(request.getContextPath() + "/users?error=" + validation.getMessage());
            return;
        }

        UserRequest userRequest = new UserRequest.Builder()
                .name(name)
                .role(roleParam)
                .surname(surname)
                .email(email)
                .password(password)
                .build();

        Boolean created = userService.register(userRequest);

        if (created) {
            response.sendRedirect(request.getContextPath() + "/users?success=user_created");
        } else {
            response.sendRedirect(request.getContextPath() + "/users?error=creation_failed");
        }
    }
}
