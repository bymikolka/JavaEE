package by.javaeecources.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.javaeecources.entities.Person;
import by.javaeecources.interfaces.IPerson;
import by.javaeecources.interfaces.IPersonRepository;
import by.javaeecources.repository.PersonRepository;

public class MyHttpServletLayer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected static final String HOMEVIEW = "/views/homeView.jsp";
	protected static final String NEWPERSON = "/views/newPerson.jsp";
	
	IPersonRepository repository = null;

	protected Long getRole(HttpServletRequest req) {
		String sRole = req.getParameter("role");
		Long role = new Long(1);
		if (sRole != null) {
			role = Long.parseLong(sRole);
		}
		return role;
	}

	protected void searchPersonById(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long idPerson = Long.parseLong(req.getParameter("idPerson"));
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

		Long idPerson = Long.parseLong(req.getParameter("idPerson"));

		IPerson person = new Person(idPerson, firstname, surname, username, role, description, email);
		person.setId(idPerson.longValue());
		boolean success = repository.updatePerson(person);
		String message = null;
		if (success) {
			message = "The Person has been successfully updated.";
		}
		List<IPerson> personList = repository.getAllPersons();
		req.setAttribute("idPerson", idPerson);
		req.setAttribute("message", message);
		forwardList(req, resp, personList);
	}

	protected void addPersonAction(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String firstname = req.getParameter("firstname");
		String surname = req.getParameter("surname");
		String username = req.getParameter("username");
		String role = req.getParameter("role");
		String description = req.getParameter("description");
		String email = req.getParameter("email");
		Long id = new Long(-1);
		Long repRole = (Long) getServletContext().getAttribute("role");

		IPersonRepository repository = PersonRepository.getRepository(repRole);
		id = repository.getNewId(); // it's not a great idea, but w/o DB it works
		IPerson person = new Person(id, firstname, surname, username, role, description, email);

		long idPerson = repository.addPerson(person);
		List<IPerson> personsList = repository.getAllPersons();
		req.setAttribute("idPerson", idPerson);
		String message = "The new Person has been successfully created.";
		req.setAttribute("message", message);
		req.setAttribute("personList", personsList);
		resp.sendRedirect(req.getContextPath() + "/");
		// forwardList(req, resp, personsList);
	}

	protected void removePersonByName(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		long idPerson = Integer.valueOf(req.getParameter("idPerson"));
		boolean confirm = repository.deletePerson(idPerson);
		if (confirm) {
			String message = "The Person has been successfully removed.";
			req.setAttribute("message", message);
		}
		List<IPerson> personList = repository.getAllPersons();
		forwardList(req, resp, personList);
	}

}
