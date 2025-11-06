package com.damvih.dao;

import com.damvih.entities.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDao {

    private final SessionFactory sessionFactory;

    public User save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
        return user;
    }

    public Optional<User> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        User user = session
                .createQuery("from User where username=:username", User.class)
                .setParameter("username", username)
                .list()
                .getFirst();
        return Optional.ofNullable(user);
    }

}
