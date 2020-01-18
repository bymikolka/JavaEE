package by.javaeecources.tests;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import by.javaeecources.model.PersonDto;

class PersonDtoTest {

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

}
