package com.damvih.services;

import com.damvih.dao.LocationDao;
import com.damvih.dto.LocationRequestDto;
import com.damvih.entities.Location;
import com.damvih.utils.MapperUtil;

import java.math.RoundingMode;
import java.util.Optional;

public class LocationService {

    private static final int ROUNDING_ACCURACY = 3;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.UP;
    private final LocationDao locationDao;

    public LocationService(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

    public void save(LocationRequestDto locationRequestDto) {
        roundCoordinates(locationRequestDto);
        Location location = MapperUtil.getInstance().map(locationRequestDto, Location.class);
        locationDao.save(location);
    }

    public Optional<Location> findByCoordinates(LocationRequestDto locationRequestDto) {
        roundCoordinates(locationRequestDto);
        return locationDao.findByCoordinates(locationRequestDto.getLongitude(), locationRequestDto.getLatitude());
    }

    private void roundCoordinates(LocationRequestDto locationRequestDto) {
        locationRequestDto.setLongitude(locationRequestDto
                .getLongitude()
                .setScale(ROUNDING_ACCURACY, ROUNDING_MODE)
        );
        locationRequestDto.setLatitude(locationRequestDto
                .getLatitude()
                .setScale(ROUNDING_ACCURACY, ROUNDING_MODE)
        );
    }

}
