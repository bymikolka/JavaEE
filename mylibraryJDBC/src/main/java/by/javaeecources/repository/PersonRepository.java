package by.javaeecources.repository;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import by.javaeecources.entities.Person;
import by.javaeecources.exceptions.PersonNotFoundException;
import by.javaeecources.interfaces.Col;
import by.javaeecources.interfaces.IPerson;
import by.javaeecources.interfaces.IPersonRepository;
import by.javaeecources.repository.PersonFactory.PersonRole;

public abstract class PersonRepository implements IPersonRepository {
	Connection connection;
	public PersonRepository() {
	}

	private List<IPerson> personList = null;

	@Override
	public void setConnection(Connection connection) {
		this.connection = connection;
	}


	
	@Override
	public List<IPerson> getPersonList() {
		if(personList!=null) {
			return personList;
		}
		return null;
	}

	static Map<Long, IPersonRepository> map = null;
	Long role;

	public static IPersonRepository getRepository(Long role) {
		// potentially fck up place
		if (map == null) {
			map = new HashMap<>();
		}
		if (map.get(role) != null) {
			return map.get(role);
		}
		IPersonRepository personRepository = null;
		if (role.longValue() == PersonRole.STUDENT.getRole().longValue()) {
			personRepository = new StudentsRepository();
		} else {
			personRepository = new TeachersRepository();
		}
		map.put(role, personRepository);
		return personRepository;
	};

	
	private List<IPerson> getAllPersonRecords(String sql, Long limit, Long offset){
		List<Field> fields = Arrays.asList(Person.class.getDeclaredFields());
	    for(Field field: fields) {
	        field.setAccessible(true);
	    }

		try (PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setLong(1, this.getRole());
			if(limit !=null && offset!=null) {
				statement.setLong(2, limit);
				statement.setLong(3, offset);
			}
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			personList = new ArrayList<>();
			
		    while(resultSet.next()) {
		        Person dto = Person.class.getConstructor().newInstance();
		        for(Field field: fields) {
		        	Col col = field.getAnnotation(Col.class);
		        	if(col!=null) {
			        	String name = field.getName();

			            try{
			                String value = resultSet.getString(name);
			                field.set(dto, field.getType().getConstructor(String.class).newInstance(value));
			            } catch (Exception e) {
			                e.printStackTrace();
			            }
		        	}
		        }
				personList.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return personList;
	}
	
	
	@Override
	public List<IPerson> getAllPersons() {
		String query = "SELECT * from person WHERE role = ? order by id";
		return this.getAllPersonRecords(query, null, null);
	}
	@Override
	public int getAllPersonsCount() {
		String query = "SELECT count(*) from person WHERE role = ?";
		try (PreparedStatement statement = connection.prepareStatement(query)){
			statement.setLong(1, this.getRole());
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
		    while(resultSet.next()) {
		    	return resultSet.getInt(1);
		    }
		}catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public List<IPerson> getAllPersonsParts(Long role, int recordsPerPage, int currentPage) {
		int start = currentPage * recordsPerPage - recordsPerPage;
		currentPage = start;
		Long limit = Long.valueOf(recordsPerPage);
		Long offset;
		if (currentPage > 1) {
			offset = Long.valueOf(currentPage - 1L);
		} else {
			offset = 0L;
		}
		String query = "SELECT * from person WHERE role = ? order by id limit ? offset ?";
		return this.getAllPersonRecords(query, limit, offset);
	}

	@Override
	public Long getNewId() {
		try {
			IPerson person = this.getPersonList().stream().max(Comparator.comparing(IPerson::getId))
					.orElseThrow(Exception::new);
			return person.getId() + 1;
		} catch (Exception e) {
			return 1L;
		}
	}

	@Override
	public Long addPerson(IPerson person) {
		List<Field> fields = Arrays.asList(person.getClass().getDeclaredFields());

		String fieldEntity = fields.stream()
				.map(field -> {field.setAccessible(true); return field;})
				.filter(field -> field.getAnnotation(Col.class) != null)
				.filter(field -> !field.getName().equals("id"))
				.map(Field::getName)
				.collect(Collectors.joining(", "));

		String fieldQ = fieldEntity.replaceAll("\\w+", "?");
		StringBuilder sql = new StringBuilder("INSERT INTO ").append(person.getClass().getSimpleName());
		sql.append("(").append(fieldEntity).append(")");
		sql.append(" VALUES ");
		sql.append(" (").append(fieldQ).append(") ");
		
		
	    //String s = "INSERT INTO person(username, lastname, firstname, description, email, role, \"DTYPE\") VALUES  (?, ?, ?, ?, ?, ?, ?) ";
		try (PreparedStatement statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS)){

			//??????
			
			statement.setString(1,person.getUsername());
			statement.setString(2,person.getFirstname());
			statement.setString(3,person.getLastname());
			statement.setString(4,person.getDescription());
			statement.setString(5,person.getEmail());
			statement.setLong(6,person.getRole());
			statement.setString(7,PersonFactory.getPersonTypeByRole(person.getRole()).getName());
			int affectedRows = statement.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating person failed, no rows affected.");
	        }
			
	        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
	            if (generatedKeys.next()) {
	                person.setId(generatedKeys.getLong(1));
	            }
	            else {
	                throw new SQLException("Creating user failed, no ID obtained.");
	            }
	        }
	        
		}catch (Exception e) {
			e.printStackTrace();
		}

		return person.getId();
	}

	@Override
	public boolean updatePerson(IPerson person) {
		String s = "UPDATE person SET username = ?, lastname = ?, firstname= ?, description = ?, email = ? WHERE id = ? ";
		try (PreparedStatement statement = connection.prepareStatement(s, Statement.RETURN_GENERATED_KEYS)){

			//??????
			
			statement.setString(1,person.getUsername());
			statement.setString(2,person.getLastname());
			statement.setString(3,person.getFirstname());
			statement.setString(4,person.getDescription());
			statement.setString(5,person.getEmail());
			statement.setLong(6,person.getId());
			int affectedRows = statement.executeUpdate();

	        if (affectedRows == 0) {
	            throw new SQLException("Creating person failed, no rows affected.");
	        }
	        
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;

	}

	@Override
	public boolean deletePerson(Long id) {
		IPerson pDB;
		try {
			pDB = this.getPersonById(id);
			if (pDB != null) {
				String s = "DELETE FROM person WHERE id = ? ";
				try (PreparedStatement statement = connection.prepareStatement(s)){
					statement.setLong(1,id);
					return statement.executeUpdate()>0;
			        
				}catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		} catch (PersonNotFoundException e) {
		}
		return false;
	}

	@Override
	public List<IPerson> searchPersonByName(String searchParam) {

		String sql = "SELECT * FROM person WHERE firstname like ? or lastname like ?";
		
		List<Field> fields = Arrays.asList(Person.class.getDeclaredFields());
	    for(Field field: fields) {
	        field.setAccessible(true);
	    }

		try (PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, searchParam+"%");
			statement.setString(2, searchParam+"%");

			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			personList = new ArrayList<>();
			
		    while(resultSet.next()) {
		        Person dto = Person.class.getConstructor().newInstance();
		        for(Field field: fields) {
		        	Col col = field.getAnnotation(Col.class);
		        	if(col!=null) {
			        	String name = field.getName();

			            try{
			                String value = resultSet.getString(name);
			                field.set(dto, field.getType().getConstructor(String.class).newInstance(value));
			            } catch (Exception e) {
			                e.printStackTrace();
			            }
		        	}
		        }
				personList.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return personList;
	
	}

	@Override
	public IPerson getPersonById(Long id) throws PersonNotFoundException {
		
		
		String sql = "SELECT id, username, lastname, firstname, description, email, role, DTYPE FROM person WHERE id = ? ";
		
		
		List<Field> fields = Arrays.asList(Person.class.getDeclaredFields());
	    for(Field field: fields) {
	        field.setAccessible(true);
	    }

		try (PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setLong(1, id);

			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			personList = new ArrayList<>();
			
		    while(resultSet.next()) {
		        Person dto = Person.class.getConstructor().newInstance();
		        for(Field field: fields) {
		        	Col col = field.getAnnotation(Col.class);
		        	if(col!=null) {
			        	String name = field.getName();

			            try{
			                String value = resultSet.getString(name);
			                field.set(dto, field.getType().getConstructor(String.class).newInstance(value));
			            } catch (Exception e) {
			                e.printStackTrace();
			            }
		        	}
		        }
				personList.add(dto);
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return personList!=null?personList.get(0):null;
	}

}
