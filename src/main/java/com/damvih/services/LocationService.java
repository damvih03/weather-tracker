package com.damvih.services;

import com.damvih.dao.LocationDao;
import com.damvih.dto.LocationRequestDto;
import com.damvih.entities.Location;
import com.damvih.utils.MapperUtil;
import lombok.RequiredArgsConstructor;

import java.math.RoundingMode;
import java.util.Optional;

@RequiredArgsConstructor
public class LocationService {

    private static final int ROUNDING_ACCURACY = 2;
    private static final RoundingMode ROUNDING_MODE = RoundingMode.UP;
    private final LocationDao locationDao;

    public Location findOrSave(LocationRequestDto locationRequestDto) {
        return findByCoordinates(locationRequestDto).orElseGet(() -> save(locationRequestDto));
    }

    public Optional<Location> findByCoordinates(LocationRequestDto locationRequestDto) {
        roundCoordinates(locationRequestDto);
        return locationDao.findByCoordinates(locationRequestDto.getLongitude(), locationRequestDto.getLatitude());
    }

    private Location save(LocationRequestDto locationRequestDto) {
        roundCoordinates(locationRequestDto);
        Location location = MapperUtil.getInstance().map(locationRequestDto, Location.class);
        return locationDao.save(location);
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
