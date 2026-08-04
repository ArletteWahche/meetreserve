package co.kozao.meetreserve.servlet.dashboard;

import java.io.IOException;
import java.util.List;

import co.kozao.meetreserve.dao.ReservationDao;
import co.kozao.meetreserve.dao.RoomDao;
import co.kozao.meetreserve.model.Reservation;
import co.kozao.meetreserve.model.Room;
import co.kozao.meetreserve.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard/employee")
public class EmployeeDashboardServlet extends HttpServlet {

    private final RoomDao roomDao = new RoomDao();
    private final ReservationDao reservationDao = new ReservationDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        List<Room> rooms = roomDao.findAll();
        List<Reservation> reservations = reservationDao.findByUserId(user.getId());

        request.setAttribute("user", user);
        request.setAttribute("rooms", rooms);
        request.setAttribute("reservations", reservations);

        request.getRequestDispatcher("/employee/dashboard.jsp").forward(request, response);
    }
}