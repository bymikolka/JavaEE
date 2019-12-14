package by.javaeecources.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.javaeecources.interfaces.IPerson;
import by.javaeecources.repository.PersonFactory.PersonRole;
import by.javaeecources.repository.PersonRepository;

@WebServlet(urlPatterns = "/")
public class HomeServlet extends MyHttpServletLayer {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PersonRole> personTypes = new ArrayList<PersonRole>(Arrays.asList(PersonRole.values()));

		getServletContext().setAttribute("personRoles", personTypes);
		String action = req.getParameter("searchAction");
		Long role = getRole(req);
		this.getServletContext().setAttribute("role", role);
		
		if (action != null) {
			switch (action) {
			case "searchById":
				searchPersonById(req, resp);
				break;
			case "searchByName":
				searchPersonByName(req, resp);
				break;
			case "delete":
				removePersonByName(req, resp);
				break;
			}
		} else {
			if (role != null) {
				repository = PersonRepository.getRepository(role);
			}
			List<IPerson> result = repository.getAllPersons();
			forwardList(req, resp, result);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		switch (action) {
		case "edit":
			editPersonAction(req, resp);
			break;
		}

	}

}
