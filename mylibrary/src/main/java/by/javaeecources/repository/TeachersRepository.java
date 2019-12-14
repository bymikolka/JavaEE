package by.javaeecources.repository;

import com.github.javafaker.Faker;

import by.javaeecources.entities.Teacher;

public class TeachersRepository extends PersonRepository {

	@Override
	public void fillRepoWithFakeData() {
		if (this.getAllPersons() == null || this.getAllPersons().isEmpty()) {
			Faker faker = new Faker();

			for (int i = 0; i < 4; i++) {
				Teacher teacher = new Teacher(faker.number().randomNumber(), faker.name().firstName(),
						faker.name().lastName(), faker.name().username(), faker.address().state(),
						faker.educator().university());
				this.addPerson(teacher);

			}
		}
	}
}
