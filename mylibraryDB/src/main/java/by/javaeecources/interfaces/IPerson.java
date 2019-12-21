package by.javaeecources.interfaces;

public interface IPerson {
	public Long getId();

	public String getDescription();

	public String getUsername();

	public String getFullName();

	public String getFirstname();

	public String getLastname();

	public String getEmail();

	public Long getRole();

	public void setId(Long id);

	public void setUsername(String username);

	public void setFirstname(String firstname);

	public void setLastname(String lastname);

	public void setDescription(String description);

	public void setEmail(String email);

	public void setRole(Long role);
	
	public IPerson cloneObj(IPerson person);
	
}
