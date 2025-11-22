package com.damvih.dao;

import com.damvih.entity.Location;
import com.damvih.entity.User;
import com.damvih.entity.UserLocation;
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
