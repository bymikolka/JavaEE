package by.javaeecources.tests;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import by.javaeecources.model.Person;
import by.javaeecources.service.PersonService;
@SpringBootTest
class PersonServiceTest {

	@Autowired
	PersonService personService;
	@Test
	void testCreateOrUpdatePerson(){
		Person person = new Person(1L, "firstname", "lastname", "username", "email", "description");
		person.setRole(1L);
		personService.createOrUpdatePerson(person);
	    Assert.assertNotNull(personService.findByFirstname("firstname"));
	    
	   // Assert.assertTrue(personService.findById(1L).isPresent());
	    //personService.delete(1L);
	}
	@Test
	void testFindByIdPerson(){
		Person person = new Person(null, "firstname", "lastname", "username", "email", "description");
		person.setRole(1L);
		personService.createOrUpdatePerson(person);
		Assert.assertNotNull(personService.findByFirstname("firstname"));
	   Assert.assertTrue(personService.findById(person.getId()).isPresent());
	   
	    //personService.delete(1L);
	}
	@Test
	void testDeletePerson(){
		Person person = new Person(null, "firstname", "lastname", "username", "email", "description");
		person.setRole(1L);
		personService.createOrUpdatePerson(person);
		Assert.assertNotNull(personService.findByFirstname("firstname"));
	   Assert.assertTrue(personService.findById(person.getId()).isPresent());
	   
	    personService.delete(person.getId());
	    Assert.assertFalse(personService.findById(person.getId()).isPresent());
	}
	@Test
	void testcreateOrUpdatePersonWithId(){
		Person person = new Person(null, "firstname", "lastname", "username", "1", "email", "description");
		personService.createOrUpdatePerson(person);
		person = new Person(person.getId(), "firstname", "lastname", "username", "email", "description");
		personService.createOrUpdatePerson(person);
	   Assert.assertTrue(personService.findById(person.getId()).isPresent());
	}
//	@Test
//	void testFindAllPageble() {
//		int page = 1;
//		int pageSize = 10;
//		int evalPageSize = pageSize;
//        int tempPageNumber = 1;
//        int evalPage = (page < 1) ? 0 : tempPageNumber;
//        Page<Person> personsList = null;
//        
//        
//        personsList = personService.findAll(PageRequest.of(evalPage, evalPageSize));
//        Assert.assertNotNull(personsList);
//        
//	}
//	@Test
//	void testFindAllPagebleByName() {
//		int page = 1;
//		int pageSize = 10;
//		int evalPageSize = pageSize;
//        int tempPageNumber = 1;
//        int evalPage = (page < 1) ? 0 : tempPageNumber;
//        Page<Person> personsList = null;
//        
//        String name = "firstname";
//        
//        personsList = personService.findAllPersonWithPagination(PageRequest.of(evalPage, evalPageSize), name);
//        Assert.assertNotNull(personsList);
//        
//	}
	
}
