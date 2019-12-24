package mylibraryDB;

import com.github.javafaker.Faker;

import by.javaeecources.db.PostgresDBConnection;
import by.javaeecources.entities.Student;
import by.javaeecources.entities.Teacher;
import by.javaeecources.repository.StudentsRepository;
import by.javaeecources.repository.TeachersRepository;

public class FillData {

	public static void main(String[] args) {
		Faker faker = new Faker();

		for (int i = 0; i < 25; i++) {
			Teacher teacher = new Teacher(faker.number().randomNumber(), faker.name().firstName(),
					faker.name().lastName(), faker.name().username(), faker.internet().emailAddress(),
					faker.educator().university());
			TeachersRepository rep = new TeachersRepository();
			rep.setConnection(PostgresDBConnection.getConnection());
			rep.addPerson(teacher);

		}
		for (int i = 0; i < 50; i++) {
			Student teacher = new Student(faker.number().randomNumber(), faker.name().firstName(),
					faker.name().lastName(), faker.name().username(), faker.internet().emailAddress(),
					faker.educator().university());
			StudentsRepository repS = new StudentsRepository();
			repS.setConnection(PostgresDBConnection.getConnection());
			repS.addPerson(teacher);
		}
	}

}
