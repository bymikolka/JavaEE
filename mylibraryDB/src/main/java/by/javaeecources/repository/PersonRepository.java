package by.javaeecources.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import by.javaeecources.entities.Person;
import by.javaeecources.exceptions.PersonNotFoundException;
import by.javaeecources.interfaces.IPerson;
import by.javaeecources.interfaces.IPersonRepository;
import by.javaeecources.repository.PersonFactory.PersonRole;

//https://www.concretepage.com/java/jpa/java-persistence-api-example
//https://stackoverflow.com/questions/7748223/jpa-createentitymanagerfactory-returns-null
public abstract class PersonRepository implements IPersonRepository {
	public PersonRepository() {
	}

	private static final EntityManagerFactory emFactory;
	static {
		emFactory = Persistence.createEntityManagerFactory("by.javaeecources");
	}

	public static EntityManager getEntityManager() {
		return emFactory.createEntityManager();
	}

	@Override
	public List<IPerson> getPersonList() {
		return personList;
	}

	private List<IPerson> personList = null;

	static Map<Long, IPersonRepository> map = null;
	Long role;

	public static IPersonRepository getRepository(Long role) {
		// potentially fck up place
		if (map == null) {
			map = new HashMap<>();
		}
		;
		if (map.get(role) != null) {
			return map.get(role);
		}
		IPersonRepository personRepository = null;
		if (role.longValue() == PersonRole.STUDENT.getRole().longValue()) {
			personRepository = new StudentsRepository();
		} else {
			personRepository = new TeachersRepository();
		}
		map.put(role, personRepository);
		return personRepository;
	};

	@Override
	public List<IPerson> getAllPersons() {
		Query query = getEntityManager().createQuery("from person WHERE role = :role order by id", IPerson.class);
		query.setParameter("role", this.getRole());
		return query.getResultList();
	}

	@Override
	public int getAllPersonsCount() {
		return this.getAllPersons().size();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<IPerson> getAllPersonsParts(Long role, int recordsPerPage, int currentPage) {
		int start = currentPage * recordsPerPage - recordsPerPage;
		currentPage = start;
		int limit = recordsPerPage;
		int offset = 0;
		if (currentPage > 1) {
			offset = currentPage - 1;
		} else {
			offset = 0;
		}

		Query query = getEntityManager().createQuery("from person where role = :role order by id", IPerson.class);
		query.setFirstResult(offset);
		query.setMaxResults(limit);

		query.setParameter("role", role);
		return query.getResultList();

//		Map<Object, Object> getAllPersonsParts = getAllParts(this.getAllPersons(), recordsPerPage);
//		return (List<IPerson>) getAllPersonsParts.get(Integer.valueOf(currentPage - 1)); // by the reason of array index always
//																					// starts with 0
	}

//	private Map<Object, Object> getAllParts(List<IPerson> list, int recordsPerPage) {
//		return IntStream.iterate(0, i -> i + recordsPerPage).limit((list.size() + recordsPerPage - 1) / recordsPerPage).boxed()
//				.collect(Collectors.toMap(i -> i / recordsPerPage, i -> list.subList(i, min(i + recordsPerPage, list.size()))));
//	}

	@Override
	public Long getNewId() {
		try {
			IPerson person = this.getAllPersons().stream().max(Comparator.comparing(IPerson::getId))
					.orElseThrow(Exception::new);
			return person.getId() + 1;
		} catch (Exception e) {
			return 1L;
		}
	}

	@Override
	public Long addPerson(IPerson person) {
		EntityManager em = getEntityManager();
		em.getTransaction().begin();

		em.merge(person);
		em.getTransaction().commit();
		em.clear();
		return person.getId();
	}

	@Override
	public boolean updatePerson(IPerson person) {
		try {
			IPerson pDB = this.getPersonById(person.getId());
			if (pDB != null) {
				EntityManager em = getEntityManager();
				em.getTransaction().begin();
				pDB.cloneObj(person);
				em.merge(pDB);
				em.getTransaction().commit();
				em.clear();
				return true;
			}
		} catch (PersonNotFoundException e) {
			return false;
		}
		return false;

//		int indx = 0;
//		Optional<IPerson> match = personList.stream().filter(c -> c.getId().longValue() == person.getId().longValue()).findFirst();
//		if (match.isPresent()) {
//			indx = personList.indexOf(match.get());
//			personList.set(indx, person);
//			return true;
//		} else {
//			return false;
//		}
	}

	@Override
	public boolean deletePerson(Long id) {
		IPerson pDB;
		try {
			pDB = this.getPersonById(id);
			if (pDB != null) {
				EntityManager em = getEntityManager();
				em.getTransaction().begin();

				em.remove(em.contains(pDB) ? pDB : em.merge(pDB));
				em.getTransaction().commit();
				em.clear();
				return true;

//		Predicate<IPerson> person = e -> e.getId().longValue() == id.longValue();
//		return personList.removeIf(person);
			}
		} catch (PersonNotFoundException e) {
		}
		return false;
	}

	@Override
	public List<IPerson> searchPersonByName(String searchParam) {
		// String sql = "from person WHERE (firstname like %:name or lastname like
		// %:name)";

		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Person> query = cb.createQuery(Person.class);
		Root<Person> person = query.from(Person.class);
		query.where(cb.like(person.get("firstname"), "%" + searchParam + "%"));
		TypedQuery<Person> typedQuery = getEntityManager().createQuery(query);
		List<IPerson> list = new ArrayList<>();
		list.addAll(typedQuery.getResultList());
		return list;
		/*
		 * Query query = this.getEntityManager().createQuery(sql, IPerson.class);
		 * query.setParameter("name", searchParam); query.setParameter("name",
		 * searchParam); if(query.getResultList()==null ||
		 * query.getResultList().isEmpty()) { return null; } else { return
		 * (List<IPerson>) query.getResultList(); }
		 */
//		Comparator<IPerson> groupByComparator = Comparator.comparing(IPerson::getFullName)
//				.thenComparing(IPerson::getUsername);
//		return personList.stream()
//				.filter(e -> e.getFullName().contains(searchParam) || e.getUsername().equalsIgnoreCase(searchParam))
//				.sorted(groupByComparator).collect(Collectors.toList());
	}

	@Override
	public IPerson getPersonById(Long id) throws PersonNotFoundException {
		Query query = getEntityManager().createQuery("from person WHERE id = :id", IPerson.class);
		query.setParameter("id", id);
		if (query.getResultList() == null || query.getResultList().isEmpty()) {
			return null;
		} else {
			return (IPerson) query.getResultList().get(0);
		}
//		Optional<IPerson> match = personList.stream().filter(e -> e.getId().longValue() == id.longValue()).findFirst();
//		if (match.isPresent()) {
//			return match.get();
//		} else {
//			throw new PersonNotFoundException(String.format("The Person with id %s not found", id));
//		}
	}

}
