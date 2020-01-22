package by.javaeecources.servlet.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mockito;

public class HelloServletTest {
	
	
	@DisplayName("Test my servlet")
	@Test
	public void testHelloServlet() {
		HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
		HttpServletRequest request = Mockito.mock(HttpServletRequest.class);
		RequestDispatcher requestDispatcher = Mockito.mock(RequestDispatcher.class);
		HttpSession session = Mockito.mock(HttpSession.class);

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		try {
			when(response.getWriter()).thenReturn(printWriter);
			when(request.getSession()).thenReturn(session);
			when(request.getRequestDispatcher("/projects.jsp")).thenReturn(requestDispatcher);
		} catch (IOException ex) {
			fail(ex.getMessage());
		}

		try {
			new Projects().doGet(request, response);
			verify(requestDispatcher).forward(request, response);
		} catch (ServletException | IOException ex) {
			fail(ex.getMessage());
		}

		printWriter.flush();

		System.err.println(stringWriter.getBuffer().toString().trim());
	}

}
