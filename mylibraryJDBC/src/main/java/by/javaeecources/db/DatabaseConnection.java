package by.javaeecources.db;

import java.sql.Connection;

public class DatabaseConnection {
	private DatabaseConnection() {
		// 
	}
	 public static Connection getConnection() {
       return PostgresDBConnection.getConnection();
   }
    
   public static void closeQuietly(Connection conn) {
       try {
           conn.close();
       } catch (Exception e) {
    	   //
       }
   }

   public static void rollbackQuietly(Connection conn) {
       try {
           conn.rollback();
       } catch (Exception e) {
    	   //
       }
   }
}
