package by.javaeecources.repository;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.Transaction;

import by.javaeecources.db.ConnectionManager;
import by.javaeecources.entities.UserAccount;
import by.javaeecources.interfaces.IPerson;

public class UserAccountRepository {
	private UserAccountRepository() {
		// Auto-generated constructor stub
	}

	public static UserAccount findUser(UserAccount user) {
		
		Transaction transaction = null;
		List<IPerson> list = null;
	try (Session session = ConnectionManager.getSessionFactory().openSession()) {
		transaction = session.beginTransaction();
		Query query = session.createQuery("from users WHERE username = :name", UserAccount.class);
		query.setParameter("name", user.getUsername());
		
		if(query.getResultList()==null || query.getResultList().isEmpty()) {
			return null;
		}
		UserAccount usrFromDB =  (UserAccount) (query.getResultList()).get(0);
		if(usrFromDB!=null && usrFromDB.equals(user)) {
			transaction.commit();
			return user;
		}
		 return null;
		
	} catch (Exception e) {
        if (transaction != null) {
            transaction.rollback();
        }
        e.printStackTrace();
    }
		return null;
		
		/*Query query = PersonRepository.getEntityManager()
				.createQuery("from users WHERE username = :name", UserAccount.class);
		query.setParameter("name", user.getUsername());
		
		if(query.getResultList()==null || query.getResultList().isEmpty()) {
			return null;
		}
		UserAccount usrFromDB =  (UserAccount) (query.getResultList()).get(0);
		if(usrFromDB!=null && usrFromDB.equals(user)) {
			return user;
		}
		 return null;*/
	}
}
