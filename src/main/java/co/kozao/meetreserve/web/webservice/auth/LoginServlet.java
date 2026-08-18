package co.kozao.meetreserve.web.webservice.auth;

import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.service.UserService;
import co.kozao.meetreserve.web.dto.response.UserResponse;

import co.kozao.meetreserve.web.dto.resquest.UserRequest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet( "/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(LoginServlet.class.getName());

    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
        boolean exists = this.userService.emailExists("admin@gmail.com");
        if (!exists) {
            UserRequest adminUser = new UserRequest.Builder()
                    .name("Admin")
                    .surname("Admin")
                    .email("admin@gmail.com")
                    .password("admin1")
                    .role("ADMINISTRATOR")
                    .build();
            userService.register(adminUser);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean isNotOk = email == null || email.isBlank() || password == null || password.isBlank();

        if (isNotOk) {
            String errorMessage = "Login or password is Empty";
            request.setAttribute("error", errorMessage);
            request.setAttribute("email", email);
            request.setAttribute("password", password);
            LOG.info(String.format("Login: %s or password: %s is Empty", email, password));
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return; 
        }

        try {
            if(!userService.emailExists(email)){
                request.setAttribute("error", "Invalid email : " + email);
                request.setAttribute("email", email);
                request.setAttribute("password", password);
                LOG.info(String.format("Invalid Email. Login: %s ", email));
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            UserResponse user = userService.login(email, password);
            if (user == null) {
                String errorMessage = String.format("Invalid password : %s", password);
                request.setAttribute("error", errorMessage);
                request.setAttribute("email", email);
                request.setAttribute("password", password);
                LOG.info(errorMessage);
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return; 
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("userConnected", user);
            Role role = Role.valueOf(user.getRole());
            switch (role) {
                case EMPLOYEE:
                    response.sendRedirect(request.getContextPath() + "/dashboard/employee");
                    break;
                case MANAGER:
                    response.sendRedirect(request.getContextPath() + "/dashboard/manager");
                    break;
                case ADMINISTRATOR:
                    response.sendRedirect(request.getContextPath() + "/dashboard/administrator");
                    break;
                default:
                    String errorMessage = "User don't have a right role";
                    response.sendRedirect(request.getContextPath() + "/login.jsp?error=" + errorMessage);
                    break;
            }
            

        } catch (Exception e) {
            LOG.warning(String.format("error: %s", e.getMessage()));
            if (!response.isCommitted()) {
                request.setAttribute("error", "An error occurred. Please try again.");
                request.getRequestDispatcher("/login.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}