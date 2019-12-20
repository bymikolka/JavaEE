package by.javaeecources.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.javaeecources.entities.Person;
import by.javaeecources.entities.UserAccount;
import by.javaeecources.interfaces.IPerson;
import by.javaeecources.interfaces.IPersonRepository;
import by.javaeecources.repository.PersonRepository;

// It's class should be named as Context or smth like this by not now 
public class MyHttpServletLayer extends HttpServlet {
	private static final String IDPERSON = "idPerson";
	private static final String MESSAGETEXT = "message";
	private static final String LOGINEDUSER = "loginedUser";
	private static final long serialVersionUID = 1L;
	protected static final String HOMEVIEW = "/views/homeView.jsp";
	protected static final String NEWPERSON = "/views/newPerson.jsp";
	protected static final String LOGINVIEW = "/views/loginView.jsp";

	IPersonRepository  repository = null;

	protected Long getRole(HttpServletRequest req) {
		String sRole = req.getParameter("role");
		Long role = 1L;
		if (sRole != null) {
			role = Long.parseLong(sRole);
		}
		return role;
	}

	protected void searchPersonById(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long idPerson = Long.parseLong(req.getParameter(MyHttpServletLayer.IDPERSON));
		IPerson person = null;
		try {
			person = repository.getPersonById(idPerson);
		} catch (Exception ex) {
			System.err.println(" Error on searchPersonById " + ex.getMessage());
		}
		req.setAttribute("person", person);
		req.setAttribute("action", "edit");
		getServletContext().getRequestDispatcher(NEWPERSON).forward(req, resp);
	}

	protected void searchPersonByName(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String personName = req.getParameter("personName");
		List<IPerson> result = repository.searchPersonByName(personName);
		forwardList(req, resp, result);
	}

	protected void forwardList(HttpServletRequest req, HttpServletResponse resp, List<IPerson> personList)
			throws ServletException, IOException {
		req.setAttribute("personList", personList);
		getServletContext().getRequestDispatcher(HOMEVIEW).forward(req, resp);
	}

	protected void editPersonAction(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String firstname = req.getParameter("firstname");
		String surname = req.getParameter("surname");
		String role = req.getParameter("role");
		String description = req.getParameter("description");
		String email = req.getParameter("email");
		String username = req.getParameter("username");

		Long idPerson = Long.parseLong(req.getParameter(MyHttpServletLayer.IDPERSON));

		IPerson person = new Person(idPerson, firstname, surname, username, role, description, email);
		person.setId(idPerson);
		boolean success = repository.updatePerson(person);
		String message = null;
		if (success) {
			message = "The Person has been successfully updated.";
		}
		List<IPerson> personList = this.paginatedResult(req);
		req.setAttribute(MyHttpServletLayer.IDPERSON, idPerson);
		req.setAttribute(MyHttpServletLayer.MESSAGETEXT, message);
		forwardList(req, resp, personList);
	}

	protected void addPersonAction(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String firstname = req.getParameter("firstname");
		String surname = req.getParameter("surname");
		String username = req.getParameter("username");
		String role = req.getParameter("role");
		String description = req.getParameter("description");
		String email = req.getParameter("email");
		Long repRole = (Long) getServletContext().getAttribute("role");

		repository = PersonRepository.getRepository(repRole);
		Long id = repository.getNewId(); // it's not a great idea, but w/o DB it works
		IPerson person = new Person(id, firstname, surname, username, role, description, email);

		long idPerson = repository.addPerson(person);
		List<IPerson> personList = this.paginatedResult(req);
		req.setAttribute(MyHttpServletLayer.IDPERSON, idPerson);
		String message = "The new Person has been successfully created.";
		req.setAttribute(MyHttpServletLayer.MESSAGETEXT, message);
		req.setAttribute("personList", personList);
		resp.sendRedirect(req.getContextPath() + "/");
		// forwardList(req, resp, personsList);
	}

	protected void removePersonByName(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long idPerson = Integer.valueOf(req.getParameter(MyHttpServletLayer.IDPERSON));
		boolean confirm = repository.deletePerson(idPerson);
		if (confirm) {
			String message = "The Person has been successfully removed.";
			req.setAttribute(MyHttpServletLayer.MESSAGETEXT, message);
		}
		List<IPerson> personList = this.paginatedResult(req);
		forwardList(req, resp, personList);
	}

	protected void getStoredUser(HttpServletRequest req, HttpServletResponse resp, UserAccount user) {
		HttpSession session = req.getSession();

		session.setMaxInactiveInterval(30 * 60);
		Cookie userName = new Cookie(MyHttpServletLayer.LOGINEDUSER, user.getUsername());
		userName.setHttpOnly(true);
		resp.addCookie(userName);
	}

	public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
		session.setAttribute(MyHttpServletLayer.LOGINEDUSER, loginedUser);
	}

	protected void logout(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("text/html");
		Cookie[] cookies = req.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("JSESSIONID")) {
					System.out.println("JSESSIONID=" + cookie.getValue());
				}
				cookie.setMaxAge(0);
				resp.addCookie(cookie);
			}
		}
		HttpSession session = req.getSession(false);
		System.out.println("User=" + session.getAttribute(MyHttpServletLayer.LOGINEDUSER));
		session.invalidate();
		// no encoding because we have invalidated the session
		try {
			resp.sendRedirect(req.getContextPath() + "/login");
		} catch (IOException e) {
			// Auto-generated catch block
			System.err.println("Error on logout" + e.getMessage());
		}
	}

	protected List<IPerson> paginatedResult(HttpServletRequest req) {
		int currentPage = 1;
		int recordsPerPage = 10; // temporary mode
		try {
			currentPage = Integer.valueOf(req.getParameter("currentPage"));
			recordsPerPage = Integer.valueOf(req.getParameter("recordsPerPage"));

		} catch (Exception e) {
			//
		}
		List<IPerson> result = repository.getAllPersonsParts(recordsPerPage, currentPage);
		int rows = repository.getAllPersonsCount();

		int nOfPages = rows / recordsPerPage;
		if (nOfPages % recordsPerPage > 0 && (nOfPages * recordsPerPage != rows)) {
			nOfPages++;
		}
		req.setAttribute("noOfPages", nOfPages);
		req.setAttribute("currentPage", currentPage);
		req.setAttribute("recordsPerPage", recordsPerPage);
		return result;
	}
}
