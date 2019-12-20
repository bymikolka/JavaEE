package by.javaeecources.entities;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Student extends Person {

	public Student(Long id, String firstname, String surname, String username, String email, String description) {
		super(id, firstname, surname, username, email, description);
	}

	@Override
	public Long getRole() {
		return 2L;
	}

}
