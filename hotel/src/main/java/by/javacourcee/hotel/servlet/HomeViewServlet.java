package by.javacourcee.hotel.servlet;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import by.javacourcee.hotel.RoomType;
import by.javacourcee.hotel.entities.Room;
import by.javacourcee.hotel.repository.RoomRepository;
@WebServlet(urlPatterns = "/")
public class HomeViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1412835304633690845L;
	private static final String HOMEVIEW = "/views/homeView.jsp";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Map<Integer, Set<Room>> repository = RoomRepository.getAllDataByType();
		req.setAttribute("rooms", repository);
		getServletContext().getRequestDispatcher(HOMEVIEW).forward(req, resp);
	}
	
}
