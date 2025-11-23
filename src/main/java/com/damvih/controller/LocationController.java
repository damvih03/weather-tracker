package com.damvih.controller;

import com.damvih.dto.api.LocationApiResponseDto;
import com.damvih.dto.location.LocationRequestDto;
import com.damvih.dto.session.SessionDto;
import com.damvih.dto.user.UserDto;
import com.damvih.service.UserLocationManagementService;
import com.damvih.service.WeatherApiService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class LocationController {

    private final WeatherApiService weatherApiService;
    private final UserLocationManagementService userLocationManagementService;
    private final ModelMapper modelMapper;

    @GetMapping("/search-locations")
    public String searchLocations(@RequestParam(name = "locationName") String name, Model model, HttpServletRequest request) {
        UserDto userDto = ((SessionDto) request.getAttribute("session")).getUserDto();
        Set<LocationApiResponseDto> addedLocations = userLocationManagementService.get(userDto)
                .stream()
                .map(locationDto -> modelMapper.map(locationDto, LocationApiResponseDto.class))
                .collect(Collectors.toSet());
        List<LocationApiResponseDto> locations = weatherApiService.getLocationsByName(name);

        model.addAttribute("username", userDto.getUsername());
        model.addAttribute("addedLocations", addedLocations);
        model.addAttribute("locations", locations);
        return "search-locations";
    }

    @PostMapping("/add-location")
    public String add(@ModelAttribute @Valid LocationRequestDto locationRequestDto, HttpServletRequest request) {
        UserDto userDto = ((SessionDto) request.getAttribute("session")).getUserDto();
        userLocationManagementService.add(userDto, locationRequestDto);
        return "redirect:/";
    }

    @PostMapping("/delete-location")
    public String delete(@ModelAttribute @Valid LocationRequestDto locationRequestDto, HttpServletRequest request) {
        UserDto userDto = ((SessionDto) request.getAttribute("session")).getUserDto();
        userLocationManagementService.delete(userDto, locationRequestDto);
        return "redirect:/";
    }

}
