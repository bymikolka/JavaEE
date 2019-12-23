package by.javaeecources.repository;

import by.javaeecources.repository.PersonFactory.PersonRole;

public class StudentsRepository extends PersonRepository {

	public StudentsRepository() {
		super();
	}

	@Override
	public Long getRole() {
		return PersonRole.STUDENT.getRole();
	};
	
//	@Override
//	public void fillRepoWithFakeData() {
//		if (this.getAllPersons() == null || this.getAllPersons().isEmpty()) {
//			Faker faker = new Faker();
//
//			for (int i = 0; i < 50; i++) {
//				Student student = new Student(faker.number().randomNumber(), faker.name().firstName(),
//						faker.name().lastName(), faker.name().username(), faker.internet().emailAddress(),
//						faker.educator().course());
//				this.addPerson(student);
//
//			}
//		}
//	}
}
