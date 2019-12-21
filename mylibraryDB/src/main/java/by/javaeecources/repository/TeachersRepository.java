package by.javaeecources.repository;

import by.javaeecources.repository.PersonFactory.PersonRole;

public class TeachersRepository extends PersonRepository {

	public TeachersRepository() {
		super();
	}
	public Long getRole() {
		return PersonRole.TEACHER.getRole();
	};

//	@Override
//	public void fillRepoWithFakeData() {
//		if (this.getAllPersons() == null || this.getAllPersons().isEmpty()) {
//			Faker faker = new Faker();
//
//			for (int i = 0; i < 25; i++) {
//				Teacher teacher = new Teacher(faker.number().randomNumber(), faker.name().firstName(),
//						faker.name().lastName(), faker.name().username(), faker.internet().emailAddress(),
//						faker.educator().university());
//				this.addPerson(teacher);
//
//			}
//		}
//	}
}
