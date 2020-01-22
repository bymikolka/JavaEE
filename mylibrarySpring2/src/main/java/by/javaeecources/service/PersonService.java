package by.javaeecources.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import by.javaeecources.model.Person;
import by.javaeecources.repository.PersonRepository;

@Service
public class PersonService {

	@Autowired
	PersonRepository repository;
	
	public Page<Person> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}
	
	
	public void delete(Long id) {
		Optional<Person> optional = repository.findById(id);
		if(optional.isPresent()) {
			repository.delete(optional.get());	
		}
		
	}


	public Optional<Person> findById(Long id) {
		return  repository.findById(id);
	}

	public List<Person> findByFirstname(String name) {
		return repository.findByFirstname(name);
	}
	
	

	public Person createOrUpdatePerson(Person person) {
		if(person.getId() == null ||(person.getFirstname()!=null && person.getId() == 0l && !person.getFirstname().isEmpty())) {
			return repository.save(person);
		}else {
			Optional<Person> requestedFromDB = repository.findById(person.getId());
			if(requestedFromDB.isPresent()) {
				Person person2Save = requestedFromDB.get();
				person2Save.setFirstname(person.getFirstname());
				person2Save.setLastname(person.getLastname());
				person2Save.setUsername(person.getUsername());
				person2Save.setEmail(person.getEmail());
				person2Save.setDescription(person.getDescription());
				person2Save.setRole(person.getRole());
				return repository.save(requestedFromDB.get());
			}
		}
		return person;
	}


	public Page<Person> findAllPersonWithPagination(PageRequest pageable, String name) {
		return repository.findAllPersonWithPagination(pageable, name);
	}

	
}
