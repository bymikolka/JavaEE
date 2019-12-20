package by.javaeecources.entities;

import javax.persistence.Entity;

import lombok.ToString;

@ToString
@Entity
public class Teacher extends Person {
	public Teacher(Long id, String firstname, String surname, String userName, String email, String description) {
		super(id, firstname, surname, userName, email, description);
	}
public Teacher() {
	// Auto-generated constructor stub
}
	@Override
	public Long getRole() {
		return 1L;
	}


}
