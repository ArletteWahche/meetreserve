package co.kozao.meetreserve.servlet;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet ("/Logout")
public class LogoutServlet extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws IOException, ServletException {
		
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
		}
		
		response.sendRedirect(request.getContextPath() + "/login.jsp");
	}

}
