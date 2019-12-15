package by.javaeecources.repository;

import by.javaeecources.entities.UserAccount;

public class UserAccountRepository {
	private UserAccountRepository() {
		// Auto-generated constructor stub
	}

	private final static String userID = "admin"; // temp mode
	private final static String password = "password";

	public static UserAccount findUser(UserAccount user) {
		 if(!(userID.equals(user.getUsername()) && password.equals(user.getPassword()))){
	        return null;
	    }
		 return user;
	}
}
