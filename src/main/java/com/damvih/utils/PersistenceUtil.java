package com.damvih.utils;

import com.damvih.entities.Location;
import com.damvih.entities.Session;
import com.damvih.entities.User;
import com.damvih.entities.UserLocation;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

public class PersistenceUtil {

    private static final EntityManagerFactory INSTANCE;

    static {
        Configuration configuration = new Configuration();

        String jdbcUrl = System.getenv("JDBC_URL");
        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        if (jdbcUrl != null && username != null && password != null) {
            configuration.setProperty("jakarta.persistence.jdbc.url", jdbcUrl);
            configuration.setProperty("jakarta.persistence.jdbc.user", username);
            configuration.setProperty("jakarta.persistence.jdbc.password", password);
        }

        addEntities(configuration);
        INSTANCE = configuration.buildSessionFactory();
    }

    public static EntityManagerFactory getInstance() {
        return INSTANCE;
    }

    private PersistenceUtil() {

    }

    private static void addEntities(Configuration configuration) {
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Session.class);
        configuration.addAnnotatedClass(Location.class);
        configuration.addAnnotatedClass(UserLocation.class);
    }

}
