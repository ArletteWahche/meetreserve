package co.kozao.meetreserve.web.webservice.auth;

import co.kozao.meetreserve.service.UserService;
import co.kozao.meetreserve.service.ValidationResult;
import co.kozao.meetreserve.web.dto.resquest.UserRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        ValidationResult result = userService.validateRegistration(name, surname, email, password);
        if (!result.isValid()) {
            request.setAttribute("message", result.getMessage());
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        UserRequest user = new UserRequest.Builder()
                .name(name)
                .surname(surname)
                .email(email)
                .password(password)
                .build();

        boolean created = userService.register(user);
        if (created) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
        } else {
            request.setAttribute("message", "An account with this email already exists, or an error occurred.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
}