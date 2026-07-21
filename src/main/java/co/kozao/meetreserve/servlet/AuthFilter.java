package co.kozao.meetreserve.servlet;

import java.io.IOException;

import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.model.User;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter ("/employee/*")
public class AuthFilter implements Filter {
	
	@Override
	public void doFilter(jakarta.servlet.ServletRequest request,
						jakarta.servlet.ServletResponse response,
						FilterChain chain)
				throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		HttpSession session = httpRequest.getSession(false);
		User user = (session != null) ? (User) session.getAttribute("userConnect") : null;
		
			if(user == null) {
				httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=loginRequired");
				return;
				
			}
			
			String path = httpRequest .getRequestURI().substring(httpRequest.getContextPath().length());
			
				Role roleRequis = null;
				if (path.startsWith("/employee")) {
					roleRequis = Role.EMPLOYEE;
				}
				if(path.startsWith("/manager")) {
					roleRequis = Role.MANAGER;
				}
				if(path.startsWith("/administrator")) {
					roleRequis = Role.ADMINISTRATOR;
				}
				
				if(roleRequis != null && user.getRole() != roleRequis ) {
					httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=accessdenied");
					return;
				}
	
				chain.doFilter(request, response);
			}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException{
		
	}
	
	@Override
	public void destroy(){
		
	}
}
