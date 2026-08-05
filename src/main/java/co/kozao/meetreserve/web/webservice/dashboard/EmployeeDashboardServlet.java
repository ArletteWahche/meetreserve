package co.kozao.meetreserve.web.webservice.dashboard;

import co.kozao.meetreserve.dao.ReservationDao;
import co.kozao.meetreserve.dao.impl.RoomDaoImpl;
import co.kozao.meetreserve.model.Reservation;
import co.kozao.meetreserve.model.Room;
import co.kozao.meetreserve.service.UserService;
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

@WebServlet("/dashboard/employee")
public class EmployeeDashboardServlet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(EmployeeDashboardServlet.class.getName());

    private final RoomDaoImpl roomDaoImpl = new RoomDaoImpl();
    private final ReservationDao reservationDao = new ReservationDao();
    private UserService userService;

    @Override
    public void init() throws ServletException {
        this.userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userConnected") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        UserResponse user = (UserResponse) session.getAttribute("userConnected");

        List<Room> rooms = roomDaoImpl.findAll();
        List<Reservation> reservations = reservationDao.findByUserId(user.getId());

        request.setAttribute("user", user);
        request.setAttribute("rooms", rooms);
        request.setAttribute("reservations", reservations);

        request.getRequestDispatcher("/employee/dashboard.jsp").forward(request, response);
    }
}