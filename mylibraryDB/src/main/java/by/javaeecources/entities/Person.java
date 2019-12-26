package by.javaeecources.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.DynamicInsert;

import by.javaeecources.interfaces.IPerson;

@Entity(name = "person")
@DynamicInsert
@DiscriminatorColumn(
	    name="dtype",
	    discriminatorType=DiscriminatorType.STRING
	)
public class Person implements IPerson, Serializable{

	
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
	@Column(nullable = false, insertable = false, updatable = false)
	private String dtype;

	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pk_sequence")
	@SequenceGenerator(name = "pk_sequence", sequenceName = "person_id_seq", allocationSize = 1)
	private Long id = 0L;

	public Person() {
		// Auto-generated constructor stub
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String getFirstname() {
		return firstname;
	}

	@Override
	public String getLastname() {
		return lastname;
	}

	@Override
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

	public Person(Long id, String firstname, String lastname, String username, String role, String email,
			String description) {
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

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	@Override
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public void setRole(Long role) {
		this.role = role;
	}

	@Override
	public IPerson cloneObj(IPerson person) {
		this.setFirstname(person.getFirstname());
		this.setUsername(person.getUsername());
		this.setLastname(person.getLastname());
		this.setDescription(person.getDescription());
		this.setEmail(person.getEmail());
		this.setRole(person.getRole());
		return this;
	}


}
