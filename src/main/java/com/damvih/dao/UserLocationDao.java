package com.damvih.dao;

import com.damvih.entities.Location;
import com.damvih.entities.User;
import com.damvih.entities.UserLocation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserLocationDao extends BaseDao<UserLocation> {

    public List<Location> findAllByUser(User user) {
        return sessionFactory.getCurrentSession()
                .createQuery("""
                        select location from Location location
                        join UserLocation userLocation on userLocation.userLocationKey.location = location
                        where userLocation.userLocationKey.user = :user
                        """, Location.class)
                .setParameter("user", user)
                .getResultList();
    }

}
