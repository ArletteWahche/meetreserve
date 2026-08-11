package co.kozao.meetreserve.web.webservice.dashboard;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import co.kozao.meetreserve.model.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard/admin")
public class AdministratorDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        List<String> roles = Arrays.stream(Role.values()).map(Role::name).toList();

        request.setAttribute("roles", roles);
        request.getRequestDispatcher("/administrator/dashboard.jsp").forward(request, response);
    }
    
    //bon ca va le probleme a ete regler, bon je dois ressortir la vue adminnistrateur et manageur mais j'ai fais un truc au debut qui etaais de mettre 
}
