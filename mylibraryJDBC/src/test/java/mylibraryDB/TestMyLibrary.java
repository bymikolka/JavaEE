package mylibraryDB;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import by.javaeecources.entities.Person;
import by.javaeecources.interfaces.Col;

public class TestMyLibrary {

	private static Connection connection;
	
	@Before
	public void init() throws SQLException {
		connection = getNewConnection();
	}
	private Connection getNewConnection() throws SQLException {
		String url = "jdbc:postgresql://127.0.0.1:5432/library";
		String user = "myuser";
		String passwd = "mypass";
		return DriverManager.getConnection(url, user, passwd);
	}
	@After
	public void close() throws SQLException {
		connection.close();
	}
	
	@Test
	public void shouldGetJdbcConnection() throws SQLException {
		try(Connection connection = getNewConnection()) {
			assertTrue(connection.isValid(1));
			assertFalse(connection.isClosed());
		}
	}
	
	@Test
	public void shouldInsertInResultSet() throws SQLException {
		Person person = new Person();
		List<Field> fields = Arrays.asList(person.getClass().getDeclaredFields());
	    for(Field field: fields) {
	        field.setAccessible(true);
	    }
		 StringBuilder sql = new StringBuilder("INSERT INTO person ");
		    sql.append("(");
		    
		    ArrayList<String> fieldEntity = new ArrayList<>();
		    ArrayList<String> fieldQ = new ArrayList<>();
	        for(Field field: fields) {
	        	Col col = field.getAnnotation(Col.class);
	        	if(col!=null) {
		        	String name = field.getName();
		        	fieldEntity.add((new StringBuilder().append("'").append(name).append("'")).toString());
		        	fieldQ.add("?");
	        	}
	        }
	        sql.append(String.join(",", fieldEntity));
	        sql.append(")");
	        sql.append(" VALUES ");
	        sql.append(" (");
	        sql.append(String.join(",", fieldQ));
	        sql.append(") ");
	    System.out.println(sql.toString());
	        
		/*Statement statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet resultSet = statement.executeQuery("SELECT * FROM customers");
		resultSet.moveToInsertRow();
		resultSet.updateLong("id", 3L);
		resultSet.updateString("name", "John");
		resultSet.updateInt("age", 18);
		resultSet.insertRow();
		resultSet.moveToCurrentRow();*/
	}
}
