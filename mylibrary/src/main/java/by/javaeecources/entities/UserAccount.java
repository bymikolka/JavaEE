package by.javaeecources.entities;

public class UserAccount {

	private String username;
	private String password;
	private String group;

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

	public String getGroup() {
		return this.group;
	}

	public void setGroup(String group) {
		this.group = group;
		
	}
	
	public boolean isEditorMode() {
		return true;
		//return "1".equals(this.getGroup());
	}
}
