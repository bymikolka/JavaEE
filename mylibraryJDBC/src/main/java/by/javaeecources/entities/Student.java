package by.javaeecources.entities;

import lombok.ToString;

@ToString
public class Student extends Person {

	public Student(Long id, String firstname, String lastname, String username, String email, String description) {
		super(id, firstname, lastname, username, email, description);
	}

	public Student() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Long getRole() {
		return 2L;
	}

}
