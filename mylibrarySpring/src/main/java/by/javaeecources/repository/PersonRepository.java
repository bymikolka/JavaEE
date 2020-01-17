package by.javaeecources.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import by.javaeecources.model.Person;
@Repository
public  interface PersonRepository extends CrudRepository<Person, Long>, PagingAndSortingRepository<Person, Long> {
	List<Person> findByFirstname(String name);
	@Override
	Optional<Person> findById(Long id);
	
	
	List<Person> findByFirstnameAndLastname(String firstName, String lastName);
	//Optional<Person> findFirst5ByFirstnameStartsWithOrderByFirstname(String firstName, String lastName);
	
}
