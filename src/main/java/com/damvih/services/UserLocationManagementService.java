package com.damvih.services;

import com.damvih.dao.LocationDao;
import com.damvih.dao.UserLocationDao;
import com.damvih.dto.LocationRequestDto;
import com.damvih.dto.UserDto;
import com.damvih.entities.Location;
import com.damvih.entities.User;
import com.damvih.entities.UserLocation;
import com.damvih.entities.UserLocationKey;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserLocationManagementService {

    private final UserLocationDao userLocationDao;
    private final LocationDao locationDao;
    private final ModelMapper modelMapper;

    public void add(UserDto userDto, LocationRequestDto locationRequestDto) {
        Location location = locationDao.findByCoordinates(locationRequestDto.getLatitude(), locationRequestDto.getLongitude())
                .orElseGet(() -> locationDao.save(modelMapper.map(locationRequestDto, Location.class)));

        userLocationDao.save(new UserLocation(
                new UserLocationKey(modelMapper.map(userDto, User.class), location)
        ));
    }

    public void delete(UserDto userDto, LocationRequestDto locationRequestDto) {
        Location location = locationDao.findByCoordinates(locationRequestDto.getLatitude(), locationRequestDto.getLongitude())
                .orElseThrow(() -> new EntityNotFoundException("Location is not found."));
        userLocationDao.delete(new UserLocation(
                new UserLocationKey(modelMapper.map(userDto, User.class), location)
        ));
    }

}
