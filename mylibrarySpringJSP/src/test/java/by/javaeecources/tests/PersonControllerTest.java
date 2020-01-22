package by.javaeecources.tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import by.javaeecources.controller.PersonController;
import by.javaeecources.repository.PersonRepository;
import by.javaeecources.service.PersonService;

@SpringBootTest
@Transactional
class PersonControllerTest {
	
	private final Logger log = LoggerFactory.getLogger(PersonControllerTest.class);
	
	private MockMvc mockMvc;
	@Autowired
	@InjectMocks
	PersonController personController;
	@Mock
	private PersonRepository personRepository;
	@Autowired
	@Mock
	PersonService personService;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();

	}

	@Test
	void testGetRoles() {
		assertNotNull(personController.getRoles());
	}

//	@Test
//	void testHome() {
//		personService = org.mockito.Mockito.mock(PersonService.class);
//		Pageable pageble = PageRequest.of(1, 3);
//		Page<Person> personsList = personService.findAll(pageble);
//		when(personService.findAll(pageble)).thenReturn(personsList);
//		try {
//			mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
//			mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"))
//					.andExpect(model().attributeExists("personsList"));
//		} catch (Exception e) {
//			log.error("Error", e);
//		}
//
//	}

	//@Test
	public void deleteById() {
		personService = org.mockito.Mockito.mock(PersonService.class);
		mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
		try {
			mockMvc.perform(MockMvcRequestBuilders
			        .delete("/delete/{id}",261))
			        .andExpect(status().isOk());
		} catch (Exception e) {
			log.error("Error", e);
		}

	}



}
