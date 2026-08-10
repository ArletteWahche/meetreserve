package co.kozao.meetreserve.web.webservice.auth;

import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.service.UserService;
import co.kozao.meetreserve.web.dto.response.UserResponse;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(LoginServlet.class.getName());

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

        boolean isNotOk = email == null || email.isBlank() || password == null || password.isBlank();

        if (isNotOk) {
            String errorMessage = "Login or password is Empty";
            LOG.info(String.format("Login: %s or password: %s is Empty", email, password));
            response.sendRedirect(request.getContextPath() + "/login.jsp?error=" + errorMessage);
            return; // ✅ on arrête ici
        }

        try {
            UserResponse user = userService.login(email, password);

            if (user == null) {
                String errorMessage = "Invalid credential";
                LOG.info(String.format("Invalid credential. Login: %s password: %s", email, password));
                response.sendRedirect(request.getContextPath() + "/login.jsp?error=" + errorMessage);
                return; // ✅ LE RETURN QUI MANQUAIT — c'était la cause du bug
            }

            HttpSession session = request.getSession(true);
            session.setAttribute("userConnected", user);

            Role role = Role.valueOf(user.getRole());

            switch (role) {
                case EMPLOYEE:
                    response.sendRedirect(request.getContextPath() + "/dashbord/employee");
                    break;
                case MANAGER:
                    response.sendRedirect(request.getContextPath() + "/dashbord/manager");
                    break;
                case ADMINISTRATOR:
                    response.sendRedirect(request.getContextPath() + "/dashbord/administrator");
                    break;
                default:
                    String errorMessage = "User don't have a right role";
                    response.sendRedirect(request.getContextPath() + "/login.jsp?error=" + errorMessage);
                    break;
            }
            // pas besoin de return ici, c'est la fin naturelle du try

        } catch (Exception e) {
            LOG.warning(String.format("error: %s", e.getMessage()));

            // Si la réponse a déjà été envoyée (un sendRedirect a eu lieu avant
            // l'exception), impossible de faire un forward — on ne peut plus
            // rien envoyer au client, donc on se contente de logguer.
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