package by.javaeecources.service;

import java.util.List;
import java.util.Optional;

import by.javaeecources.model.Person;

public interface PersonService {

	public List<Person> findAll(Integer pageNo, Integer pageSize, String sortBy);
	
	public void delete(Long id);
	
	public Optional<Person> findById(Long id);
	
	public List<Person> findAll();
	
	public List<Person> findByFirstname(String name);
	
	public Person createOrUpdatePerson(Person person);
	
	List<Person> findByFirstnameOrLastnameOrderById(String firstName, String lastName);
	
	public Long count();
}
