package by.javaeecources.repository;

import by.javaeecources.repository.PersonFactory.PersonRole;

public class TeachersRepository extends PersonRepository {

	public TeachersRepository() {
		super();
	}
	@Override
	public Long getRole() {
		return PersonRole.TEACHER.getRole();
	}
}
