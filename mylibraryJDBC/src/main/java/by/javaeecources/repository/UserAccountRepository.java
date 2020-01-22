package by.javaeecources.repository;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

import by.javaeecources.entities.UserAccount;
import by.javaeecources.interfaces.Col;


public class UserAccountRepository {

	private UserAccountRepository() {
	}

	public static UserAccount findUser(UserAccount user, Connection connection) {
		UserAccount userA = null;
		String sql=" select * from users where username= ?";
		try (PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, user.getUsername());
			statement.execute();
			ResultSet resultSet = statement.getResultSet();
			List<Field> fields = Arrays.asList(UserAccount.class.getDeclaredFields());
		    for(Field field: fields) {
		        field.setAccessible(true);
		    }

		    while(resultSet.next()) {
		    	userA = UserAccount.class.getConstructor().newInstance();
		        for(Field field: fields) {
		        	Col col = field.getAnnotation(Col.class);
		        	if(col!=null) {
			        	String name = field.getName();

			            try{
			                String value = resultSet.getString(name);
			                field.set(userA, field.getType().getConstructor(String.class).newInstance(value));
			            } catch (Exception e) {
			                e.printStackTrace();
			            }
		        	}
		        }
		    }
		    if(userA!=null && userA.getUsername().equals(user.getUsername()) && userA.getPassword().equals(user.getPassword()) ) {
		    	return userA;
		    }else {
		    	return null;
		    }
		    
		    
		    }catch (Exception e) {
				e.printStackTrace();
			}
		
		
//		Query query = PersonRepository.getEntityManager()
//				.createQuery("from users WHERE username = :name", UserAccount.class);
//		query.setParameter("name", user.getUsername());
//		
//		if(query.getResultList()==null || query.getResultList().isEmpty()) {
//			return null;
//		}
//		UserAccount usrFromDB =  (UserAccount) (query.getResultList()).get(0);
//		if(usrFromDB!=null && usrFromDB.equals(user)) {
//			return user;
//		}
		 return null;
	}
}
