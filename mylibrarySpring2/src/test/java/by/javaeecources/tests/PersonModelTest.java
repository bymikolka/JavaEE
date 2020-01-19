package by.javaeecources.tests;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import by.javaeecources.model.Person;
@Transactional
class PersonModelTest {

	@Test
	@Tag("PersonModel")
	void testPersonModel() {
		Person person = new Person(1L, "firstname", "lastname", "username", "email", "description");
		person = new Person(1L, "firstname", "lastname", "username", "2", "email", "description");
		Assertions.assertNotNull(person);
		person.setId(100L);
		Assert.assertEquals(100L, person.getId().longValue());
		
		person.setFirstname("firstnameTest");
		Assert.assertEquals(person.getFirstname(), "firstnameTest");
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

}
