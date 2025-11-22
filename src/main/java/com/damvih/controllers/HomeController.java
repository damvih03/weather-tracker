package com.damvih.controllers;

import com.damvih.dto.GeocodedWeatherDto;
import com.damvih.dto.GeodataApiRequestDto;
import com.damvih.dto.LocationDto;
import com.damvih.dto.SessionDto;
import com.damvih.services.UserLocationManagementService;
import com.damvih.services.WeatherApiService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserLocationManagementService userLocationManagementService;
    private final WeatherApiService weatherApiService;
    private final ModelMapper modelMapper;

    @GetMapping("/")
    public String getHomePage(HttpServletRequest request, Model model) {
        SessionDto sessionDto = (SessionDto) request.getAttribute("session");

        List<LocationDto> locations = userLocationManagementService.get(sessionDto.getUserDto());
        Map<LocationDto, GeocodedWeatherDto> weathers = locations.stream()
                .collect(Collectors.toMap(
                        location -> location,
                        location -> weatherApiService.getWeatherByCoordinates(
                                modelMapper.map(location, GeodataApiRequestDto.class)
                        ))
                );
        model.addAttribute("weathers", weathers);
        model.addAttribute("username", sessionDto.getUserDto().getUsername());
        return "index";
    }

}
