package by.javaeecources;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import by.javaeecources.model.Person;
import by.javaeecources.repository.PersonRepository;

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
		//optionalPersons.forEach(System.out::println);
	}
	@Test
	void testFindAll() {
		
		List<Person> optionalPersons = (List<Person>) repository.findAll();
		assertNotNull(optionalPersons);
		//optionalPersons.forEach(System.out::println);
	}

}
