package com.damvih.dao;

import com.damvih.entity.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDao extends BaseDao<User> {

    public Optional<User> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        User user = session
                .createQuery("from User where username=:username", User.class)
                .setParameter("username", username)
                .uniqueResult();
        return Optional.ofNullable(user);
    }

}
