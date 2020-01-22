package by.javaeecources.entities;

import java.io.Serializable;
import java.util.Objects;

import by.javaeecources.interfaces.Col;
import lombok.Data;
@Data
public class UserAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	@Col(name = "username")
	private String username;
	@Col(name = "password")
	private String password;
	@Col(name = "group")
	private String group;
	@Col(name = "id")
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
