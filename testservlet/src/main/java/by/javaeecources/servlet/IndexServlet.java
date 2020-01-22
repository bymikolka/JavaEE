package by.javaeecources.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/")
public class IndexServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			PrintWriter out = response.getWriter();
			//ServletOutputStream out = response.getOutputStream();

			out.println("<html>");
			out.println("<head>");
			out.println(
					"<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\">");
			out.println("<title>Hello Servlet</title></head>");

			out.println("<body>");
			out.println("<h3>Hello, I'm Index Servlet!!</h3>");
			out.println("<div class=\"alert alert-dark\" role=\"alert\">");
			out.println("<h6 class=\"display-1\"><a href=\"hello\">to Hello World Servlet</a></h6>");
			out.println("</div>");

			out.println("</body>");
			out.println("<html>");
		  }
		  catch (Exception uhex) {
			  System.err.println("IndexServlet "+uhex.getMessage());
		  }

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
}
