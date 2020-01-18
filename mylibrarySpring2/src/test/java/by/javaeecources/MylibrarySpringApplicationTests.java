package by.javaeecources;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import by.javaeecources.model.PagerModel;
import by.javaeecources.model.Person;
import by.javaeecources.repository.PersonRepository;
import by.javaeecources.service.PersonService;

@SpringBootTest
class MylibrarySpringApplicationTests {

	@Autowired
	private PersonRepository repository;

	@Test
	void testFindByFirstnameAndLastname() {
		List<Person> optionalPersons = repository.findByFirstname("Mikalai");
		assertNotNull(optionalPersons);
		// optionalPersons.forEach(System.out::println);
	}

	@Test
	void testFindAll() {

		List<Person> optionalPersons = (List<Person>) repository.findAll();
		assertNotNull(optionalPersons);
		// optionalPersons.forEach(System.out::println);
	}
	@Test
	void testPager() {
		PagerModel model = new PagerModel(10, 3, 5);
		assertNotNull(model);
	}

	@Test
	void testSetButtonsToShow() {

		PagerModel model = new PagerModel(10, 3, 5);
		assertNotNull(model);
		Assert.assertFalse(model.toString().isEmpty());
		model.setButtonsToShow(5);
		assertEquals(5, model.getButtonsToShow());
	}

	@Test
	void testSetButtonsToShowMustBeOdd() {
		int totalPages = 10;
		PagerModel model = new PagerModel(totalPages, 3, 5);
		
		assertTrue(10 > model.getStartPage());
		assertTrue(0 < model.getEndPage());
		IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class,
				() -> model.setButtonsToShow(10), "Expected error to throw, but it didn't");

		assertTrue(thrown.getMessage().contains("Must be"));
	}
	

	
	
	
	@Test
	void testRepository() {
		Person person = new Person(1L, "firstname", "lastname", "username", "email", "description");
		repository.save(person);
	    Assert.assertNotNull(repository.findByFirstname("firstname"));
	    
	    //Assert.assertTrue(repository.findById(1L).isPresent());
	    
	}
	@Autowired
	PersonService personService;
	@Test
	void testService(){
		Person person = new Person(1L, "firstname", "lastname", "username", "email", "description");
		personService.createOrUpdatePerson(person);
	    Assert.assertNotNull(personService.findByFirstname("firstname"));
	    
	   // Assert.assertTrue(personService.findById(1L).isPresent());
	    //personService.delete(1L);
	    
	    
	}
}
