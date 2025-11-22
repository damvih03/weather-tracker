package com.damvih.dao;

import com.damvih.entity.Location;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public class LocationDao extends BaseDao<Location> {

    public Optional<Location> findByCoordinates(BigDecimal latitude, BigDecimal longitude) {
        Location location = sessionFactory.getCurrentSession()
                .createQuery("from Location where latitude=:latitude and longitude=:longitude", Location.class)
                .setParameter("latitude", latitude)
                .setParameter("longitude", longitude)
                .uniqueResult();
        return Optional.ofNullable(location);
    }

}
