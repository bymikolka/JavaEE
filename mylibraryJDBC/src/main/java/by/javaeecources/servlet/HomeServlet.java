package by.javaeecources.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.javaeecources.db.ConnectionContext;
import by.javaeecources.interfaces.IPerson;
import by.javaeecources.repository.PersonFactory.PersonRole;
import by.javaeecources.repository.PersonRepository;

@WebServlet(urlPatterns = "/")
public class HomeServlet extends MyHttpServletLayer {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<PersonRole> personTypes = new ArrayList<>(Arrays.asList(PersonRole.values()));

		getServletContext().setAttribute("personRoles", personTypes);
		String action = req.getParameter("searchAction");
		Long role = getRole(req);
		getServletContext().setAttribute("role", role);
		Connection conn = ConnectionContext.getStoredConnection(req);
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
				repository.setConnection(ConnectionContext.getStoredConnection(req));
			}

			List<IPerson> result = paginatedResult(req);
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
		case "logout":
			logout(req, resp);
			break;

		}

	}

}
