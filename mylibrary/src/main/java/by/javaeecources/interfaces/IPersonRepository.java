package by.javaeecources.interfaces;

import java.util.List;

import by.javaeecources.exceptions.PersonNotFoundException;

public interface IPersonRepository {

	List<IPerson> getAllPersons();
	public List<IPerson> getAllPersonsParts(int pageSize, int page);
	public List<IPerson> searchPersonByName(String searchParam);
	
	public int getAllPersonsCount();
	
	public IPerson getPersonById(Long id) throws PersonNotFoundException;
	public void fillRepoWithFakeData(); // temporary method
	public Long addPerson(IPerson person);
	public boolean updatePerson(IPerson person);
	public boolean deletePerson(Long id);
	public Long getNewId();
}
