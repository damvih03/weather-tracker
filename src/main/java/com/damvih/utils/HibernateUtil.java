package com.damvih.utils;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final EntityManagerFactory INSTANCE;

    static {
        Configuration configuration = new Configuration();

        String username = System.getenv("DB_USERNAME");
        String password = System.getenv("DB_PASSWORD");

        if (username != null && password != null) {
            configuration.setProperty("jakarta.persistence.jdbc.user", username);
            configuration.setProperty("jakarta.persistence.jdbc.password", password);
        }

        addEntities(configuration);
        INSTANCE = configuration.buildSessionFactory();
    }

    public static EntityManagerFactory getInstance() {
        return INSTANCE;
    }

    private HibernateUtil() {

    }

    private static void addEntities(Configuration configuration) {

    }

}
