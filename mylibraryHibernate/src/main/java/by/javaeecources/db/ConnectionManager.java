package by.javaeecources.db;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
public class ConnectionManager {
	
	
	
	
	private static final Logger logger = LogManager.getLogger(ConnectionManager.class);
	public static Logger getLogger() {
		return logger;
	}
	static {
		System.setProperty("log4j.configurationFile","/src/main/resources/log4j2.xml");
		
//		String log4jConfigFile = System.getProperty("log4j.configurationFile");
//        DOMConfigurator.configure(ConnectionManager.class.getResource("log4j2.xml").toURI() + log4jConfigFile);
//		
	}
	private static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
    	
    	if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration.configure("/by/javaeecources/db/hibernate.cfg.xml");
                configuration.addAnnotatedClass(by.javaeecources.entities.Person.class);
                configuration.addAnnotatedClass(by.javaeecources.entities.UserAccount.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
                logger.info("!!!---!!!");
       
            } catch (Exception e) {
            	logger.error("Error message on getting session factory", e);
            }
        }
        return sessionFactory;
    }
}
