package com.damvih.services;

import com.damvih.dao.UserLocationDao;
import com.damvih.entities.Location;
import com.damvih.entities.User;
import com.damvih.entities.UserLocation;
import com.damvih.entities.UserLocationCompositeKey;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserLocationService {

    public UserLocationService(UserLocationDao userLocationDao) {
        this.userLocationDao = userLocationDao;
    }

    public UserLocation save(User user, Location location) {
        UserLocationCompositeKey userLocationCompositeKey = new UserLocationCompositeKey(user, location);
        return userLocationDao.save(new UserLocation(userLocationCompositeKey));
    }

    public void delete(User user, Location location) {
        UserLocationCompositeKey userLocationCompositeKey = new UserLocationCompositeKey(user, location);
        userLocationDao.delete(new UserLocation(userLocationCompositeKey));
    }

}
