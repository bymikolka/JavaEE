package by.javaeecources.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import by.javaeecources.db.ConnectionManager;
import by.javaeecources.entities.UserAccount;
import by.javaeecources.interfaces.IPersonRepository;
import by.javaeecources.repository.UserAccountRepository;
@TestMethodOrder(OrderAnnotation.class)
class UserRepositoryTest {

	
		
		//private static final Logger logger = LogManager.getLogger(PersonRepositoryTest.class);
		static Session session = null;
		static IPersonRepository  repository = null;
		
		static Long role = 2L;
		static Long id;
		
		@BeforeAll
		static void init() {
			//System.setProperty("log4j2.configurationFile","src\\test\\resources\\log4j2-test.xml");
			session = ConnectionManager.getSessionFactory().openSession();
		}

		@Test
		void testSessionFactory() {
			assertNotNull(session);
			//logger.info("App test testSessionFactory passed.");
		}
		
		@Test
		void testFindUser() {
			UserAccount user = new UserAccount();
			user = new UserAccount();
			user.setUsername("username");
			user.setPassword("username");
			user = UserAccountRepository.findUser(user);
			
			assertNull(user);
			user = new UserAccount();
			user.setUsername("admin");
			user.setPassword("admin");
			user = UserAccountRepository.findUser(user);
			assertNotNull(user);
			
		}

}
