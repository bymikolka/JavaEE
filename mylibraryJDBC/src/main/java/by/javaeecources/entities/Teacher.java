package by.javaeecources.entities;

import lombok.ToString;

@ToString
public class Teacher extends Person {
	public Teacher(Long id, String firstname, String lastname, String userName, String email, String description) {
		super(id, firstname, lastname, userName, email, description);
	}
public Teacher() {
	// Auto-generated constructor stub
}
	@Override
	public Long getRole() {
		return 1L;
	}


}
