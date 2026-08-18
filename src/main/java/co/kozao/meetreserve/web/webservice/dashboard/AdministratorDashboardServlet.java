package co.kozao.meetreserve.web.webservice.dashboard;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import co.kozao.meetreserve.model.ReservationStatus;
import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.service.ReservationService;
import co.kozao.meetreserve.service.RoomService;
import co.kozao.meetreserve.service.UserService;
import co.kozao.meetreserve.web.dto.response.ReservationResponse;
import co.kozao.meetreserve.web.dto.response.RoomResponse;
import co.kozao.meetreserve.web.dto.response.UserResponse;
import co.kozao.meetreserve.web.dto.resquest.UserRequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/dashboard/administrator")
public class AdministratorDashboardServlet extends HttpServlet {

	private UserService userService;
	private RoomService roomService;
	private ReservationService reservationService;
	
	@Override
	public void init() throws ServletException{
		this.userService = new UserService();
		this.roomService = new RoomService();
		this.reservationService = new ReservationService();
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
    	
    	List<String> roles = Arrays.stream(Role.values()).map(Role::name).toList();
    	List<UserResponse> users = userService.getAllUsers();
    	List<RoomResponse> rooms = roomService.getAllRooms();
    	List<ReservationResponse> reservations = reservationService.getReservations();

    
    	long employeeCount = users.stream().filter(u -> "EMPLOYEE".equals(u.getRole())).count();
        long managerCount = users.stream().filter(u -> "MANAGER".equals(u.getRole())).count();
        long pendingCount = reservations.stream()
                .filter(r -> r.getStatus().equals(ReservationStatus.PENDING))
                .count();

    	
        request.setAttribute("roles", roles);
        request.setAttribute("users", users);
        request.setAttribute("rooms", rooms);
        request.setAttribute("currentUser", user);
        request.setAttribute("totalUsers", users.size());
        request.setAttribute("employeeCount", employeeCount);
        request.setAttribute("managerCount", managerCount);
        request.setAttribute("totalRooms", rooms.size());
        request.setAttribute("totalReservations", reservations.size());
        request.setAttribute("pendingCount", pendingCount);

        request.getRequestDispatcher("/administrator/dashboard.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException{

    	String action = request.getParameter("action");
		boolean isUserCreated = action != null && action.equalsIgnoreCase("createUser");
		String message = "";

		if(isUserCreated) {
			String name = request.getParameter("name");
			String surname = request.getParameter("surname");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String roleParam = request.getParameter("role");

			var validation = userService.validateRegistration(name, surname, email, password);
			if(!validation.isValid()) {
				message = validation.getMessage();
				request.setAttribute("error" , message);
				response.sendRedirect(request.getContextPath() + "/dashboard/administrator");
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

			if(created) {
				message = String.format("Successfully registered user with email: %s", email);
				request.setAttribute("success" , message);
				response.sendRedirect(request.getContextPath() + "/dashboard/administrator");
			} else {
				message = String.format("User with email: %s not register due to some error. Please try again", email);
				request.setAttribute("error" , message);
				response.sendRedirect(request.getContextPath() + "/dashboard/administrator");
			}
    	}
		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
    }
}
