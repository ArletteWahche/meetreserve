package co.kozao.meetreserve.web.webservice.dashboard;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
		
	private UserResponse requireAdmin(HttpServletRequest request, HttpServletResponse response)
    		throws IOException {
    	
    	HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userConnected") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return null;
        }
        
        UserResponse user = (UserResponse) session.getAttribute("userConnected");
        if(!Role.ADMINISTRATOR.name().equals(user.getRole())) {
        	response.sendError(HttpServletResponse.SC_FORBIDDEN, "Administrator acess only.");
        	return null;
        }
        
        return user;
    	
    }
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	/**/
    	
    	UserResponse currentUser = requireAdmin(request, response);
    	if(currentUser == null) {
    		return;
    	}
    	
    	List<String> roles = Arrays.stream(Role.values()).map(Role::name).toList();
    	List<UserResponse> users = userService.getAllUsers();
    	List<RoomResponse> rooms = roomService.getAllRooms();
    	List<ReservationResponse> reservations = reservationService.getReservations();

    
    	long employeeCount = users.stream().filter(u -> "EMPLOYEE".equals(u.getRole())).count();
        long managerCount = users.stream().filter(u -> "MANAGER".equals(u.getRole())).count();
        long pendingCount = reservations.stream()
                .filter(r -> r.getStatus() == co.kozao.meetreserve.model.ReservationStatus.PENDING)
                .count();

    	
        request.setAttribute("roles", roles);
        request.setAttribute("users", users);
        request.setAttribute("rooms", rooms);
        request.setAttribute("currentUser", currentUser);
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
    	
    	UserResponse currentUser = requireAdmin(request, response);
    	if(currentUser == null) {
    		return;
    	}
    	
    	String action = request.getParameter("action");
    	if(action == null) {
    		action = "createUser";
    	}
    	
    	if("createUser".equals(action)) {
    		handleCreateUser(request, response);
    	}else {
    		response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown action: " + action);
    	}
    }
    
    private void handleCreateUser(HttpServletRequest request, HttpServletResponse response)
    		throws IOException{
    	
    	String name = request.getParameter("name");
    	String surname = request.getParameter("surname");
    	String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	String roleParam = request.getParameter("role");
    	
    	Role role;
    	try {
    		role = Role.valueOf(roleParam);
    	} catch(IllegalArgumentException | NullPointerException e) {
    		response.sendRedirect(request.getContextPath() + "/dashboard/administrator?error=invalid_role");
    		return;
    	}
    	
    	var validation = userService.validateRegistration(name, surname, email, password);
    	if(!validation.isValid()) {
    		response.sendRedirect(request.getContextPath() + "/dashboard/administrator?error"
    				+ validation.getMessage());
    		return;
    	}
    	
    	UserRequest userRequest = new UserRequest.Builder()
    			.name(name)
    			.surname(surname)
    			.email(email)
    			.password(password)
    			.build();
    	
    	Boolean created = userService.registerByAdmin(userRequest, role);
    	
    	if(created) {
    		response.sendRedirect(request.getContextPath() + "/dashboard/administrator?success=user_created");
    	} else {
    		response.sendRedirect(request.getContextPath() + "/dashboard/administrator?error=creation_failed");
    	}
    			
    }
     
}
