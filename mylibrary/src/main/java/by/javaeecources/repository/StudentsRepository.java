package by.javaeecources.repository;

import com.github.javafaker.Faker;

import by.javaeecources.entities.Student;

public class StudentsRepository extends PersonRepository {

	@Override
	public void fillRepoWithFakeData() {
		if (this.getAllPersons() == null || this.getAllPersons().isEmpty()) {
			Faker faker = new Faker();

			for (int i = 0; i < 20; i++) {
				Student student = new Student(faker.number().randomNumber(), faker.name().firstName(),
						faker.name().lastName(), faker.name().username(), faker.address().fullAddress(),
						faker.educator().course());
				this.addPerson(student);

			}
		}
	}
}
