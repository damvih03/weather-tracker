package com.damvih.utils;

import com.damvih.entities.Session;
import com.damvih.entities.User;
import jakarta.persistence.EntityManagerFactory;
import org.hibernate.cfg.Configuration;

public class PersistenceUtil {

    private static final EntityManagerFactory INSTANCE;

    static {
        Configuration configuration = new Configuration();
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
    }

}
