package by.javaeecources.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.github.javafaker.Faker;

import by.javaeecources.db.ConnectionManager;
import by.javaeecources.entities.Person;
import by.javaeecources.exceptions.PersonNotFoundException;
import by.javaeecources.interfaces.IPerson;
import by.javaeecources.interfaces.IPersonRepository;

@TestMethodOrder(OrderAnnotation.class)
class PersonRepositoryTest {
	
	private static final Logger logger = LogManager.getLogger(PersonRepositoryTest.class);
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
	
	@BeforeAll
	static void testRepository() {
		repository = PersonRepository.getRepository(role);
		
		assertNotNull(repository);
	}
	
	
	@Test
	void testGetPersonList() {
		assertNotEquals(0, repository.getAllPersons().size());
	}


	@Test
	void testGetAllPersons() {
		assertNotEquals(0, repository.getAllPersons().size());
	}

	@Test
	@DisplayName("Persons count in repository")
	void testGetAllPersonsCount() {
		
		assertTrue(repository.getAllPersonsCount()>0);
	}

	@Test
	void testGetAllPersonsParts() {
		List<IPerson> list = repository.getAllPersonsParts(role, 10, 2);
		assertNotNull(list);
		assertNotEquals(0, list.size());

	}

	@Test
	void testGetNewId() {
		assertNotEquals(0, repository.getAllPersons().size());
		Long id = repository.getNewId();
		assertTrue(id>0);
		
	}

	@Test
	@Order(1)
	void testAddPerson() {
		Faker faker = new Faker();
		IPerson teacher = new Person(faker.number().randomNumber(), faker.name().firstName(),
				faker.name().lastName(), faker.name().username(), role.toString(), faker.internet().emailAddress(),
				faker.educator().university());
		
		id = repository.addPerson(teacher);
		assertNotEquals(0, id);
		try {
			assertNotNull(repository.getPersonById(id));
		} catch (PersonNotFoundException e) {
			logger.error("Error in testAddPerson ", e);
		}
		

	}

	@Test
	@Order(2)   
	void testUpdatePerson() {
		try {
			IPerson iPerson = repository.getPersonById(id);
			assertNotNull(iPerson);
			String oldUserName = iPerson.getUsername();
			iPerson.setUsername(oldUserName+"_tmp");
			repository.updatePerson(iPerson);
			iPerson = repository.getPersonById(id);
			assertEquals(oldUserName+"_tmp", iPerson.getUsername());
			
			
		} catch (PersonNotFoundException e) {
			logger.error("Error in testUpdatePerson ", e);
		}

	}

	@Test
	@Order(4)
	void testDeletePerson() {
		try {
			IPerson iPerson = repository.getPersonById(id);
			assertNotNull(iPerson);
			repository.deletePerson(id);
			iPerson = repository.getPersonById(id);
			assertTrue(iPerson == null);
		} catch (PersonNotFoundException e) {
			logger.error("Error in testDeletePerson ", e);
		}

	}

	@Test
	void testSearchPersonByName() {
		List<IPerson> iPerson = repository.searchPersonByName("D");
		assertNotNull(iPerson);
	}

	@Test
	@Order(3)
	void testGetPersonById() {
		try {
			assertNotNull(repository.getPersonById(id));
		} catch (PersonNotFoundException e) {
			logger.error("Error in testAddPerson ", e);
		}

	}

}
