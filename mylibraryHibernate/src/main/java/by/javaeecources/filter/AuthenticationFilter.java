package by.javaeecources.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.javaeecources.db.ConnectionManager;
import by.javaeecources.servlet.MyHttpServletLayer;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

	private ServletContext context;
	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.context = fConfig.getServletContext();
		this.context.log("AuthenticationFilter initialized");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String uri = req.getRequestURI();
		ConnectionManager.getLogger().info("AuthenticationFilter Requested Resource:: {}", uri);

		HttpSession session = req.getSession(false);
		if (session!= null && MyHttpServletLayer.getLoginedUser(session) == null && uri.endsWith("/create")) {
			ConnectionManager.getLogger().info("Unauthorized access request");
			res.sendRedirect(req.getContextPath()+MyHttpServletLayer.LOGINVIEW);
		} else {
			// pass the request along the filter chain
			chain.doFilter(request, response);
		}

	}

	@Override
	public void destroy() {
		// close any resources here
	}
}