package by.javaeecources.repository;

import by.javaeecources.repository.PersonFactory.PersonRole;

public class StudentsRepository extends PersonRepository {

	public StudentsRepository() {
		super();
	}

	@Override
	public Long getRole() {
		return PersonRole.STUDENT.getRole();
	}
	
}
