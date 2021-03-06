package by.javaeecources.repository;

import javax.persistence.Query;

import by.javaeecources.entities.UserAccount;

public class UserAccountRepository {
	private UserAccountRepository() {
		// Auto-generated constructor stub
	}

	public static UserAccount findUser(UserAccount user) {
		Query query = PersonRepository.getEntityManager()
				.createQuery("from users WHERE username = :name", UserAccount.class);
		query.setParameter("name", user.getUsername());
		
		if(query.getResultList()==null || query.getResultList().isEmpty()) {
			return null;
		}
		UserAccount usrFromDB =  (UserAccount) (query.getResultList()).get(0);
		if(usrFromDB!=null && usrFromDB.equals(user)) {
			return user;
		}
		 return null;
	}
}
