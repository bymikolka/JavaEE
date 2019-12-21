package by.javaeecources.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import by.javaeecources.interfaces.IPerson;
import lombok.Data;
import lombok.ToString;
@Data
@Entity(name = "person")
@ToString
public class Person implements IPerson ,Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull
	@Column(nullable = false)
	private String username;
	@NotNull
	@Column(nullable = false)
	private String firstname;
	@NotNull
	@Column(nullable = false)
	private String lastname;
	@NotNull
	@Column(nullable = false)
	private String description;
	@NotNull
	@Column(nullable = false)
	private String email;
	@NotNull
	@Column(nullable = false)
	private Long role = 0L;
	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
	@SequenceGenerator(name="pk_sequence",sequenceName="person_id_seq", allocationSize=1)
	private Long id = 0L;

	public Person() {
		// Auto-generated constructor stub
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public Person(Long id, String firstname, String lastname, String username, String email, String description) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.description = description;
		this.role = getRole();
	}

	public Person(Long id, String firstname, String lastname, String username, String role, String email, String description) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.description = description;
		this.role = Long.parseLong(role);
	}

	
	@Override
	public String getFullName() {
		return String.format("%s %s", this.firstname, this.lastname);
	}

	@Override
	public Long getId() {
		return this.id;
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
	public Long getRole() {
		return this.role;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

}
