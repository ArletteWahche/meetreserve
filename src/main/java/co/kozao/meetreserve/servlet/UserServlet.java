
package co.kozao.meetreserve.servlet;

import java.io.IOException;

import co.kozao.meetreserve.dao.UserDao;
import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet ("/user")
public class UserServlet extends HttpServlet{
	
		private UserDao userDao;
		
		@Override
		public void init() throws ServletException{
			
			 userDao = new UserDao();
		}
 	
		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
			
				String email = request.getParameter("email");
				String password = request.getParameter("password");
				
				User user = userDao.login(email, password);
				
				if(user != null) {
					HttpSession session = request.getSession();
					session.setAttribute("user", user);
					session.setAttribute("role", user.getRole());
					
					Role role = user.getRole();
					
					if(role == Role.ADMINISTRATOR) {
						response.sendRedirect(request.getContextPath() + "/administrator/dashboard.jsp");
					}else if(role == Role.MANAGER) {
						response.sendRedirect(request.getContextPath() + "/employee/dashboard.jsp");
					}else if(role == Role.EMPLOYEE) {
						response.sendRedirect(request.getContextPath() + "/manager/dashboard.jsp");
					}else {
							request.setAttribute("error", "Unknown role");
						
						request.getRequestDispatcher("/login.jsp").forward(request, response);
					} 
					
				} else {
					request.setAttribute("error", "Incorrect Email or Password");
				}
					
				}
				
				@Override
				protected void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException{
					
					response.sendRedirect(request.getContextPath() + "/login.jsp");
					
				
		}
		
	
}