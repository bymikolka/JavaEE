package by.javaeecources.repository;

import by.javaeecources.entities.UserAccount;

public class UserAccountRepository {
	private UserAccountRepository() {
		// Auto-generated constructor stub
	}

	private static final String USERID = "admin"; // temp mode
	private static final String PASSWORD = "password";

	public static UserAccount findUser(UserAccount user) {
//		 if(!(USERID.equals(user.getUsername()) && PASSWORD.equals(user.getPassword()))){
//	        return null;
//	    }
		 return user;
	}
}
