package by.javaeecources.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.javaeecources.servlet.MyHttpServletLayer;

@WebFilter("/LoginAttemptFilter")
public class LoginAttemptFilter implements Filter {

	private static final Logger logger = LogManager.getLogger(LoginAttemptFilter.class);
	
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		logger.info("Requested Resource:: {}", uri);

		HttpSession session = req.getSession(false);
		if (session!= null && MyHttpServletLayer.getLoginedUser(session) != null && uri.endsWith("/login")) {
			logger.info("Successfully logined user!");
			res.sendRedirect(req.getContextPath());
		
		}else {
			logger.warn("Login attempt registred!");
		} 
			chain.doFilter(request, response);

	}

	@Override
	public void destroy() {
		// close any resources here
	}
}