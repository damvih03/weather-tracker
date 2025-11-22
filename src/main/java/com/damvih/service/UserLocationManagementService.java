package com.damvih.service;

import com.damvih.dao.LocationDao;
import com.damvih.dao.UserLocationDao;
import com.damvih.dto.location.LocationDto;
import com.damvih.dto.location.LocationRequestDto;
import com.damvih.dto.user.UserDto;
import com.damvih.entity.Location;
import com.damvih.entity.User;
import com.damvih.entity.UserLocation;
import com.damvih.entity.UserLocationKey;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    public List<LocationDto> get(UserDto userDto) {
        List<Location> userLocations = userLocationDao.findAllByUser(modelMapper.map(userDto, User.class));
        return userLocations.stream()
                .map(location -> modelMapper.map(location, LocationDto.class))
                .toList();
    }

}
