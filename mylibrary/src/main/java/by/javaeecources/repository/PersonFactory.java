package by.javaeecources.repository;

public class PersonFactory {

	public enum PersonRole{
		TEACHER(1L, "Teacher"),
		STUDENT(2L, "Student");
		
		final Long role;
		final String name;
		final String shortName;
		public Long getRole() {
			return role;
		}
		private PersonRole(Long role, String name) {
			this.role = role;
			this.name = name;
			this.shortName = name.substring(0, 1);
		}
		
		public String getName() {
			return this.name;
		}
		public String getShortName() {
			return this.shortName;
		}
	}

	public static PersonRole getPersonTypeByRole(Long pt) {
		for (PersonRole types : PersonRole.values()) {
			
			if (types.getRole().longValue() == pt.longValue()) {
				return types;
			}
		}
		return null;
	}
	private PersonFactory() {
	}
}
