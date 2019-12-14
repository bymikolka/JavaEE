package by.javaeecources.entities;

public class Teacher extends Person {
	public Teacher(Long id, String firstname, String surname, String userName, String email, String description) {
		super(id, firstname, surname, userName, email, description);
	}

	@Override
	public Long getRole() {
		return 1L;
	}

	@Override
	public String toString() {
		return "Teacher [getFirstname()=" + getFirstname() + ", getSurname()=" + getSurname() + ", getEmail()="
				+ getEmail() + ", getId()=" + getId() + ", getDescription()=" + getDescription() + ", getUsername()="
				+ getUsername() + ", getFullName()=" + getFullName() + "]";
	}



}
