package by.javaeecources.interfaces;

import java.sql.Connection;
import java.util.List;

import by.javaeecources.exceptions.PersonNotFoundException;

public interface IPersonRepository {

	List<IPerson> getAllPersons();
	
	public List<IPerson> getPersonList();
	public List<IPerson> getAllPersonsParts(Long id, int pageSize, int page);
	public List<IPerson> searchPersonByName(String searchParam);
	
	public int getAllPersonsCount();
	
	public IPerson getPersonById(Long id) throws PersonNotFoundException;
	public boolean updatePerson(IPerson person);
	public Long addPerson(IPerson person);
	public boolean deletePerson(Long id);
	public Long getNewId();
	public Long getRole();

	public void setConnection(Connection connection);


}
