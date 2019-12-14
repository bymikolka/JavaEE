package by.javaeecources.entities;

import by.javaeecources.interfaces.IPerson;

public class Person implements IPerson {

	private String username;
	private String firstname;
	private String surname;
	private String description;
	private String email;
	private Long role = 0L;
	private Long id = 0L;

	public Person(Long id, String firstname, String surname, String username, String email, String description) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.description = description;
		this.role = getRole();
	}

	public Person(Long id, String firstname, String surname, String username, String role, String email, String description) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.surname = surname;
		this.email = email;
		this.description = description;
		this.role = Long.parseLong(role);
	}

	
	public String getFirstname() {
		return firstname;
	}

	public String getSurname() {
		return surname;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public String getFullName() {
		return String.format("%s %s", this.firstname, this.surname);
	}

	@Override
	public Long getRole() {
		return role;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}
}
