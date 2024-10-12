package com.damvih.servlets;

import com.damvih.dto.LocationRequestDto;
import com.damvih.dto.api.geocoding.GeocodingApiResponseDto;
import com.damvih.entities.Location;
import com.damvih.entities.Session;
import com.damvih.services.LocationService;
import com.damvih.services.UserLocationService;
import com.damvih.services.WeatherApiService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/locations")
public class LocationsServlet extends BaseServlet {

    private WeatherApiService weatherApiService;
    private LocationService locationService;
    private UserLocationService userLocationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        weatherApiService = (WeatherApiService) config
                .getServletContext()
                .getAttribute("WeatherApiService");

        locationService = (LocationService) config
                .getServletContext()
                .getAttribute("LocationService");

        userLocationService = (UserLocationService) config
                .getServletContext()
                .getAttribute("UserLocationService");

        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cityName = request.getParameter("locationName");

        GeocodingApiResponseDto geocodingApiResponse = weatherApiService.getLocationsByName(cityName);

        webContext.setVariable("geocodingApiResponse", geocodingApiResponse);
        templateEngine.process("locations", webContext, response.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = (Session) request.getAttribute("session");

        String cityName = request.getParameter("locationName");
        String longitude = request.getParameter("lon");
        String latitude = request.getParameter("lat");
        
        LocationRequestDto locationRequestDto = LocationRequestDto.builder()
                .name(cityName)
                .longitude(getValueInBigDecimal(longitude))
                .latitude(getValueInBigDecimal(latitude))
                .build();

        Location location = locationService.findOrSave(locationRequestDto);
        userLocationService.save(session.getUser(), location);
        response.sendRedirect("/home");
    }

}
