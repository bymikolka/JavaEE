package by.javaeecources;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import by.javaeecources.model.PagerModel;
import by.javaeecources.model.Person;
import by.javaeecources.model.PersonDto;
import by.javaeecources.repository.PersonRepository;
import by.javaeecources.service.PersonService;

@SpringBootTest
class MylibrarySpringApplicationTests {

	@Test
	void contextLoads() {
	}

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
	@Tag("PersonModel")
	void testPersonModel() {
		Person person = new Person(1L, "firstname", "lastname", "username", "email", "description");
		person = new Person(1L, "firstname", "lastname", "username", "2", "email", "description");
		Assertions.assertNotNull(person);
		person.setId(100L);
		Assert.assertEquals(100L, person.getId().longValue());
		
		person.setFirstname("firstname");
		Assert.assertEquals(person.getFirstname(), "firstname");
		person.setLastname("lastname");
		Assert.assertEquals(person.getLastname(), "lastname");
		person.setUsername("username");
		Assert.assertEquals(person.getUsername(), "username");
		person.setRole(2L);
		Assert.assertEquals(2L, person.getRole().longValue());
		person.setEmail("email");
		Assert.assertEquals(person.getEmail(), "email");
		person.setDescription("description");
		Assert.assertEquals(person.getDescription(), "description");
		Assert.assertEquals(person.getFullName(), person.getFirstname()+" "+person.getLastname());
		Assert.assertFalse(person.toString().isEmpty());
		
		
	}
	
	@Test
	void testPersonDto() {
		PersonDto person = new PersonDto();
		Assertions.assertNotNull(person);
		person.setId(100L);
		Assert.assertEquals(100L, person.getId().longValue());
		
		person.setFirstname("firstname");
		Assert.assertEquals("firstname", person.getFirstname());
		person.setLastname("lastname");
		Assert.assertEquals("lastname", person.getLastname());
		person.setUsername("username");
		Assert.assertEquals("username", person.getUsername());
		person.setRole(2L);
		Assert.assertEquals(person.getRole().longValue(), 2L);
		person.setEmail("email");
		Assert.assertEquals(person.getEmail(), "email");
		person.setDescription("description");
		Assert.assertEquals("description", person.getDescription());
		person.setDtype("Teacher");
		Assert.assertEquals("Teacher", person.getDtype());
		
		
	}
	
	@Test
	void testRepository() {
		Person person = new Person(1L, "firstname", "lastname", "username", "email", "description");
		repository.save(person);
	    Assert.assertNotNull(repository.findByFirstname("firstname"));
	    
	    Assert.assertTrue(repository.findById(1L).isPresent());
	    
	}
	@Autowired
	PersonService personService;
	@Test
	void testService(){
		Person person = new Person(1L, "firstname", "lastname", "username", "email", "description");
		personService.createOrUpdatePerson(person);
	    Assert.assertNotNull(personService.findByFirstname("firstname"));
	    
	    Assert.assertTrue(personService.findById(1L).isPresent());
	    personService.delete(1L);
	    
	    
	}
}
