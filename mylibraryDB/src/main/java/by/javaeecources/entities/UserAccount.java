package by.javaeecources.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
@Entity
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
