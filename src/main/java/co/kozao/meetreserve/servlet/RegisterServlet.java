package co.kozao.meetreserve.servlet;

import java.io.IOException;

import org.mindrot.jbcrypt.BCrypt;

import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.model.User;
import co.kozao.meetreserve.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
	
			if (name == null || name.isBlank()
					|| surname == null || surname.isBlank()
					|| email == null || email.isBlank()
					|| password == null || password.isBlank()) {
				request.setAttribute("message", "Veuillez remplir tous les champs.");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
				return;
			}
	
			User user = new User();
			user.setName(name);
			user.setSurname(surname);
			user.setEmail(email);
			user.setPassword(BCrypt.hashpw(password, BCrypt.gensalt())); // hashage avant stockage
			user.setRole(Role.EMPLOYEE);

		boolean created = userService.register(user);

		if (created) {
			response.sendRedirect(request.getContextPath() + "/login.jsp");
		} else {
			request.setAttribute("message", "An account with this email already exists, or an error occurred.");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
	}
}