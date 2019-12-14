package by.javaeecources.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/create" })
public class CreatePersonServlet extends MyHttpServletLayer {
	private static final long serialVersionUID = 1L;

	public CreatePersonServlet() {
		super();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long role = (Long) getServletContext().getAttribute("role");
		if(role==null) {
			role = getRole(request);
		}
		this.getServletContext().setAttribute("role", role);
		getServletContext().getRequestDispatcher(NEWPERSON).forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		addPersonAction(request, response);
	}

}
