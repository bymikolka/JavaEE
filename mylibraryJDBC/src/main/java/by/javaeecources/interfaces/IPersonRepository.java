package by.javaeecources.interfaces;

import java.sql.Connection;
import java.util.List;

import by.javaeecources.exceptions.PersonNotFoundException;

public interface IPersonRepository {

	List<IPerson> getAllPersons(Connection connection);
	
	public List<IPerson> getPersonList();
	public List<IPerson> getAllPersonsParts(Connection connection, Long id, int pageSize, int page);
	public List<IPerson> searchPersonByName(Connection connection, String searchParam);
	
	public int getAllPersonsCount(Connection connection);
	
	public IPerson getPersonById(Connection connection, Long id) throws PersonNotFoundException;
	public boolean updatePerson(IPerson person, Connection connection);
	public Long addPerson(IPerson person, Connection connection);
	public boolean deletePerson(Connection connection, Long id);
	public Long getNewId();
	public Long getRole();
}
