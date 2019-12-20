package by.javaeecources.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.javaeecources.entities.UserAccount;
import by.javaeecources.repository.UserAccountRepository;
import lombok.Data;

@Data
@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends MyHttpServletLayer{

	private static final long serialVersionUID = 1L;

	
	
	public LoginServlet() {
		super();
	}

	// Show Login page.
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			this.getServletContext().getRequestDispatcher(LOGINVIEW).forward(request, response);	
		} catch (Exception e) {
			// handle exception
		}
		

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		UserAccount user = null;
		boolean hasError = false;
		String message = null;

		if (username == null || password == null || username.isEmpty()|| password.isEmpty()) {
			hasError = true;
			message = "Required username and password!";
		} else {
			
			try {
				user = new UserAccount();
				user.setUsername(username);
				user.setPassword(password);
				if(UserAccountRepository.findUser(user)!=null) {
					getStoredUser(request, response, user);	

				}else {
					hasError = true;
					message = "Username or password invalid";
				}
			} catch (Exception e) {
				hasError = true;
				message = e.getMessage();
			}
		}
		// If error, forward to /views/login.jsp
		if (hasError) {
			request.setAttribute("message", message);
			request.setAttribute("user", user);

			try {
				this.getServletContext().getRequestDispatcher(LOGINVIEW).forward(request, response);	
			} catch (Exception e) {
				// handle exception
			}
		}
		else {
			HttpSession session = request.getSession();
			storeLoginedUser(session, user);
			String encodedURL = response.encodeRedirectURL(request.getContextPath()+ "/");
			try {
				response.sendRedirect(encodedURL);
			} catch (IOException e) {
				message = e.getMessage();
				request.setAttribute("message", message);
			}

			//response.sendRedirect(request.getContextPath() + "/111	");

		
		}
	}

}