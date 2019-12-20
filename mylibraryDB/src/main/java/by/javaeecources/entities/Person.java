package by.javaeecources.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import by.javaeecources.interfaces.IPerson;
import lombok.Data;
import lombok.ToString;
@Data
@Entity
@ToString
public class Person implements IPerson {
	@NotNull
	@Column(nullable = false)
	private String username;
	@NotNull
	@Column(nullable = false)
	private String firstname;
	@NotNull
	@Column(nullable = false)
	private String surname;
	@NotNull
	@Column(nullable = false)
	private String description;
	@NotNull
	@Column(nullable = false)
	private String email;
	private Long role = 0L;
	@Id
	@Column(name="id")
	@GeneratedValue
	private Long id = 0L;

	public Person() {
		// Auto-generated constructor stub
	}
	
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

	
	@Override
	public String getFullName() {
		return String.format("%s %s", this.firstname, this.surname);
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getRole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		
	}

}
