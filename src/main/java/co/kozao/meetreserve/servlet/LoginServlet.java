package co.kozao.meetreserve.servlet;

import java.io.IOException;

import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.model.User;
import co.kozao.meetreserve.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.isBlank() || password == null || password.isBlank()) {
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=champsVides");
            return;
        }

        try {
            User user = userService.authentifier(email, password);

            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setAttribute("userConnect", user);

                Role role = user.getRole();

                switch (role) {
                    case EMPLOYEE:
                        response.sendRedirect(request.getContextPath() + "/employee/home");
                        break;
                    case MANAGER:
                        response.sendRedirect(request.getContextPath() + "/manager/home");
                        break;
                    case ADMINISTRATOR:
                        response.sendRedirect(request.getContextPath() + "/administrator/home");
                        break;
                    default:
                        response.sendRedirect(request.getContextPath() + "/login.jsp?error=role");
                        break;
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/login.jsp?error=identifiants");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred. Please try again.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}