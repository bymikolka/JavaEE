package by.javaeecources.db;

import java.sql.Connection;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import by.javaeecources.entities.UserAccount;


public class ConnectionContext {
	private ConnectionContext() {}
	
	public static final String ATT_NAME_CONNECTION = "ATTRIBUTE_FOR_CONNECTION";
	private static final String ATT_NAME_USER_NAME = "ATTRIBUTE_FOR_STORE_USER_NAME_IN_COOKIE";
	public static final String ATT_NAME_REPOSITORY = "ATTRIBUTE_FOR_REPOSITORY";
	
	// Store Connection in request attribute.
	// (Information stored only exist during requests)
	public static void storeConnection(ServletRequest request, Connection conn) {
		request.setAttribute(ATT_NAME_CONNECTION, conn);
	}

	// Get the Connection object has been stored in attribute of the request.
	public static Connection getStoredConnection(ServletRequest request) {
		return (Connection) request.getAttribute(ATT_NAME_CONNECTION);
		//return (Connection) request.getServletContext().getAttribute("DBManager");
	}

	// Store user info in Session.
	public static void storeLoginedUser(HttpSession session, UserAccount loginedUser) {
		// On the JSP can access via ${loginedUser}
		session.setAttribute("loginedUser", loginedUser);
	}

//	public static void storeRepository(ServletRequest request, IDBRepository repository) {
//		request.setAttribute(ATT_NAME_CONNECTION, repository);
//	}
//
//	public static IDBRepository getRepository(ServletRequest request) {
//		return (IDBRepository) request.getAttribute(ATT_NAME_REPOSITORY);
//	}
	
	// Get the user information stored in the session.
	public static UserAccount getLoginedUser(HttpSession session) {
		return (UserAccount) session.getAttribute("loginedUser");
	}

	// Remove the user information stored in the session.
	public static void removeLoginedUser(HttpSession session) {
		session.removeAttribute("loginedUser");
	}

	
	// Store info in Cookie
	public static void storeUserCookie(HttpServletResponse response, UserAccount user) {
		System.out.println("Store user cookie");
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, user.getUsername());
		// 1 day (Converted to seconds)
		cookieUserName.setMaxAge(24 * 60 * 60);
		response.addCookie(cookieUserName);
	}

	public static String getUserNameInCookie(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (ATT_NAME_USER_NAME.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	// Delete cookie.
	public static void deleteUserCookie(HttpServletResponse response) {
		Cookie cookieUserName = new Cookie(ATT_NAME_USER_NAME, null);
		// 0 seconds (This cookie will expire immediately)
		cookieUserName.setMaxAge(0);
		response.addCookie(cookieUserName);
	}
}
