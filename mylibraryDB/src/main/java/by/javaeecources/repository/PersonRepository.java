package by.javaeecources.repository;

import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import by.javaeecources.exceptions.PersonNotFoundException;
import by.javaeecources.interfaces.IPerson;
import by.javaeecources.interfaces.IPersonRepository;
import by.javaeecources.repository.PersonFactory.PersonRole;
//https://www.concretepage.com/java/jpa/java-persistence-api-example
//https://stackoverflow.com/questions/7748223/jpa-createentitymanagerfactory-returns-null
public abstract class PersonRepository implements IPersonRepository {
	
	
	private static final EntityManagerFactory emFactory;
	static {
		   emFactory = Persistence.createEntityManagerFactory("by.javaeecources");
	}
	
	public static EntityManager getEntityManager(){
		return emFactory.createEntityManager();
	}
	
	private List<IPerson> personList = null;

	static Map<Long, IPersonRepository> map = null;
	public static IPersonRepository getRepository(Long role) {
		// potentially fck up place 
		if(map == null) {
			map = new HashMap<>();
		};
		if(map.get(role)!=null){
			return map.get(role);
		}
		IPersonRepository personRepository = null;
		if(role.longValue() == PersonRole.STUDENT.getRole().longValue()) {
			personRepository = new StudentsRepository();
		}else {
			personRepository = new TeachersRepository();
		}
		// data faker temporary method
		personRepository.fillRepoWithFakeData();
		map.put(role, personRepository);
		return personRepository;
	};
	
	@Override
	public List<IPerson> getAllPersons() {
		if (personList == null) {
			personList = new ArrayList<>();
		}
		return personList;
	}

	@Override
	public int getAllPersonsCount() {
		return this.getAllPersons().size();
	}

	
	@Override
	@SuppressWarnings("unchecked")
	public List<IPerson> getAllPersonsParts(int pageSize, int page) {
		Map<Object, Object> getAllPersonsParts = getAllParts(this.getAllPersons(), pageSize);
		return (List<IPerson>) getAllPersonsParts.get(Integer.valueOf(page-1)); // by the reason of array index always starts with 0 
	}
	
	
	private Map<Object, Object> getAllParts(List<IPerson> list, int pageSize) {
	    return IntStream.iterate(0, i -> i + pageSize)
	            .limit((list.size() + pageSize - 1) / pageSize)
	            .boxed()
	            .collect(Collectors.toMap(i -> i / pageSize,
	                           i -> list.subList(i, min(i + pageSize, list.size()))));
	}

	
	@Override
	public Long getNewId() {
		try {
			IPerson person = this.getAllPersons().stream().max(Comparator.comparing(IPerson::getId)).orElseThrow(Exception::new);
			return person.getId()+1;
		} catch (Exception e) {
			return new Long(1);
		}
	}
	
	
	@Override
	public Long addPerson(IPerson person) {
		EntityManager em = getEntityManager();	
		em.getTransaction().begin();
		
		em.persist(person);
		em.getTransaction().commit();
		em.clear();
		return person.getId();
	}

	@Override
	public boolean updatePerson(IPerson person) {
		int indx = 0;
		Optional<IPerson> match = personList.stream().filter(c -> c.getId().longValue() == person.getId().longValue()).findFirst();
		if (match.isPresent()) {
			indx = personList.indexOf(match.get());
			personList.set(indx, person);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean deletePerson(Long id) {
		Predicate<IPerson> person = e -> e.getId().longValue() == id.longValue();
		return personList.removeIf(person);
	}

	@Override
	public List<IPerson> searchPersonByName(String searchParam) {
		Comparator<IPerson> groupByComparator = Comparator.comparing(IPerson::getFullName)
				.thenComparing(IPerson::getUsername);
		return personList.stream()
				.filter(e -> e.getFullName().contains(searchParam) || e.getUsername().equalsIgnoreCase(searchParam))
				.sorted(groupByComparator).collect(Collectors.toList());
	}

	@Override
	public IPerson getPersonById(Long id) throws PersonNotFoundException {
		Optional<IPerson> match = personList.stream().filter(e -> e.getId().longValue() == id.longValue()).findFirst();
		if (match.isPresent()) {
			return match.get();
		} else {
			throw new PersonNotFoundException(String.format("The Person with id %s not found", id));
		}
	}

}
