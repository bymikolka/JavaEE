package by.javaeecources.entities;

public class Student extends Person {

	public Student(Long id, String firstname, String surname, String username, String email, String description) {
		super(id, firstname, surname, username, email, description);
	}



	@Override
	public String toString() {
		return "Student [getFirstname()=" + getFirstname() + ", getSurname()=" + getSurname() + ", getEmail()="
				+ getEmail() + ", getId()=" + getId() + ", getDescription()=" + getDescription() + ", getUsername()="
				+ getUsername() + ", getFullName()=" + getFullName() + "]";
	}



	@Override
	public Long getRole() {
		return 2L;
	}

}
