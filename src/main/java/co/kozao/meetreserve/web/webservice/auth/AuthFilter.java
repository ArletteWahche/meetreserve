package co.kozao.meetreserve.web.webservice.auth;

import java.io.IOException;

import co.kozao.meetreserve.model.Role;
import co.kozao.meetreserve.web.dto.response.UserResponse;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter(urlPatterns = {"/dashboard/employee", "/dashboard/manager", "/dashboard/administrator"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(jakarta.servlet.ServletRequest request,
                          jakarta.servlet.ServletResponse response,
                          FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        HttpSession session = httpRequest.getSession(false);
        UserResponse user = (session != null) ? (UserResponse) session.getAttribute("userConnected") : null;

        if (user == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=loginRequired");
            return;
        }

        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());

        Role roleRequis;
        if (path.endsWith("/employee")) {
            roleRequis = Role.EMPLOYEE;
        } else if (path.endsWith("/manager")) {
            roleRequis = Role.MANAGER;
        } else if (path.endsWith("/administrator")) {
            roleRequis = Role.ADMINISTRATOR;
        } else {
            roleRequis = null;
        }

        if (roleRequis != null && !roleRequis.name().equals(user.getRole())) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp?error=accessDenied");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}