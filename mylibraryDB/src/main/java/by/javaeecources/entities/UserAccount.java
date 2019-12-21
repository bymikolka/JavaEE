package by.javaeecources.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
@Entity(name = "users")
public class UserAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	@NotNull
	@Column(nullable = false)
	private String username;
	@NotNull
	@Column(nullable = false)
	private String password;
	@NotNull
	@Column(nullable = false)
	private String group;
	@Id
	@GeneratedValue
	private Long id = 0L;
	
	
	@Override
	public int hashCode() {
		return Objects.hash(password, username);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserAccount other = (UserAccount) obj;
		return Objects.equals(password, other.password) && Objects.equals(username, other.username);
	}

	public UserAccount() {
		// not used
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isEditorMode() {
		return true;
		//return "1".equals(this.getGroup());
	}
}
