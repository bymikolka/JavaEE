package by.javaeecources.repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import by.javaeecources.db.ConnectionManager;
import by.javaeecources.entities.Person;
import by.javaeecources.exceptions.PersonNotFoundException;
import by.javaeecources.interfaces.IPerson;
import by.javaeecources.interfaces.IPersonRepository;
import by.javaeecources.repository.PersonFactory.PersonRole;

public abstract class PersonRepository implements IPersonRepository {
	private static final Logger logger = LogManager.getLogger(PersonRepository.class);
	public PersonRepository() {
	}

	// private static final EntityManagerFactory emFactory;
//	static {
//		emFactory = Persistence.createEntityManagerFactory("by.javaeecources");
//	}
//
//	public static EntityManager getEntityManager() {
//		return emFactory.createEntityManager();
//	}

	@Override
	public List<IPerson> getPersonList() {
		if (personList == null) {
			personList = new LinkedList<>();
		}
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
		logger.info("getRepository created.");
		return personRepository;
	}

	@Override
	public List<IPerson> getAllPersons() {
		Transaction transaction;
		try (Session session = ConnectionManager.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			Query<Person> query = session.createQuery("from Person WHERE role = :role order by id", Person.class);
			query.setParameter("role", this.getRole());
			List<IPerson> iPersons = new LinkedList<>();
			iPersons.addAll(query.getResultList());
			transaction.commit();
			logger.info("getAllPersons executed.");
			return iPersons;
		}

//		Query query = getEntityManager().createQuery("from person WHERE role = :role order by id", IPerson.class);
//		query.setParameter("role", this.getRole());
//		return query.getResultList();
	}

	@Override
	public int getAllPersonsCount() {
		Transaction transaction;
		try (Session session = ConnectionManager.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			int count = (int)((long) session.createQuery("select count(p.id) from Person p where role=:pRole")
					.setParameter("pRole", this.getRole())
					.uniqueResult());
			transaction.commit();
			return count;
		}
	}

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
		logger.warn("getAllPersonsParts {}, {}, {}", role, recordsPerPage, currentPage);
		Transaction transaction = null;
		try (Session session = ConnectionManager.getSessionFactory().openSession()) {

			transaction = session.beginTransaction();
			// Query query = getEntityManager().createQuery("from person where role = :role
			// order by id", IPerson.class);
			Query<Person> query = session.createQuery("from Person where role = :role order by id", Person.class);
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			query.setParameter("role", role);
			List<IPerson> iPersons = new LinkedList<>();
			iPersons.addAll(query.getResultList());
			transaction.commit();
			session.close();
			logger.info("getAllPersonsParts {} {} {}", role, recordsPerPage, currentPage);
			return iPersons;

		} catch (Exception e) {
//            if (transaction != null) {
//                transaction.rollback();
//            }
			logger.error("getAllPersonsParts executed with error .", e);
		}
		return null;

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
		Transaction transaction = null;
		Long id = 0L;
		try (Session session = ConnectionManager.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			id = (Long) session.save(person);
			transaction.commit();
			logger.info("addPerson added");
		} catch (Exception e) {
			logger.error("addPerson executed with error .", e);
		}
		return id;

		/*
		 * EntityManager em = getEntityManager(); em.getTransaction().begin();
		 * 
		 * em.merge(person); em.getTransaction().commit(); em.clear(); return
		 * person.getId();
		 */
	}

	@Override
	public boolean updatePerson(IPerson person) {
		try {
			IPerson pDB = this.getPersonById(person.getId());
			if (pDB != null) {
				Transaction transaction = null;
				try (Session session = ConnectionManager.getSessionFactory().openSession()) {
					transaction = session.beginTransaction();
					pDB.cloneObj(person);
					session.update(pDB);
					transaction.commit();
				} catch (Exception e) {
					if (transaction != null) {
						transaction.rollback();
					}
					logger.error("updatePerson executed with error .", e);
				}

//				EntityManager em = getEntityManager();
//				em.getTransaction().begin();
//				pDB.cloneObj(person);
//				em.merge(pDB);
//				em.getTransaction().commit();
//				em.clear();
				return true;
			}
		} catch (PersonNotFoundException e) {
			logger.error("updatePerson executed with PersonNotFoundException .", e);
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

				Transaction transaction = null;
				try (Session session = ConnectionManager.getSessionFactory().openSession()) {
					transaction = session.beginTransaction();
					session.delete(pDB);
					transaction.commit();
				} catch (Exception e) {
					if (transaction != null) {
						transaction.rollback();
					}
					logger.error("deletePerson executed with error ", e);
				}

//				EntityManager em = getEntityManager();
//				em.getTransaction().begin();
//
//				em.remove(em.contains(pDB) ? pDB : em.merge(pDB));
//				em.getTransaction().commit();
//				em.clear();
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
		Transaction transaction = null;
		List<IPerson> list = null;
		try (Session session = ConnectionManager.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();

			CriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Person> query = cb.createQuery(Person.class);
			Root<Person> person = query.from(Person.class);
			query.where(cb.like(person.get("firstname"), "%" + searchParam + "%"));
			TypedQuery<Person> typedQuery = session.createQuery(query);
			list = new ArrayList<>(); // List<Person> -> List<IPerson> ??
			list.addAll(typedQuery.getResultList());
			transaction.commit();
			logger.info("searchPersonByName executed with {}", searchParam);
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("searchPersonByName executed with error with "+searchParam, e);
		}
		return list;

//		CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//		CriteriaQuery<Person> query = cb.createQuery(Person.class);
//		Root<Person> person = query.from(Person.class);
//		query.where(cb.like(person.get("firstname"), "%" + searchParam + "%"));
//		TypedQuery<Person> typedQuery = getEntityManager().createQuery(query);
//		List<IPerson> list = new ArrayList<>(); //List<Person> -> List<IPerson> ??
//		list.addAll(typedQuery.getResultList());
//		return list;
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
		Transaction transaction = null;
		IPerson iPerson = null;

		try (Session session = ConnectionManager.getSessionFactory().openSession()) {
			transaction = session.beginTransaction();
			iPerson = session.get(Person.class, id);
			transaction.commit();
		} catch (Exception e) {
			if (transaction != null) {
				transaction.rollback();
			}
			logger.error("getPersonById executed with error with id"+id, e);
		}
		return iPerson;

		/*
		 * Query query = getEntityManager().createQuery("from person WHERE id = :id",
		 * IPerson.class); query.setParameter("id", id); if (query.getResultList() ==
		 * null || query.getResultList().isEmpty()) { return null; } else { return
		 * (IPerson) query.getResultList().get(0); }
		 */
//		Optional<IPerson> match = personList.stream().filter(e -> e.getId().longValue() == id.longValue()).findFirst();
//		if (match.isPresent()) {
//			return match.get();
//		} else {
//			throw new PersonNotFoundException(String.format("The Person with id %s not found", id));
//		}
	}

}
