package com.damvih.servlets;

import com.damvih.dto.LocationRequestDto;
import com.damvih.dto.api.geocoding.GeocodingApiResponseDto;
import com.damvih.entities.Location;
import com.damvih.entities.Session;
import com.damvih.exceptions.LocationNotFoundException;
import com.damvih.services.LocationService;
import com.damvih.services.UserLocationService;
import com.damvih.services.WeatherApiService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/locations")
public class LocationsServlet extends HttpServlet {

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
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cityName = request.getParameter("city");

        GeocodingApiResponseDto geocodingApiResponse = weatherApiService.getLocationsByName(cityName);
        request.setAttribute("locations", geocodingApiResponse);
        request.getRequestDispatcher("/WEB-INF/templates/locations.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = ((Session) request.getServletContext().getAttribute("session"));

        String cityName = request.getParameter("city");
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

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = ((Session) request.getServletContext().getAttribute("session"));

        String longitude = request.getParameter("lon");
        String latitude = request.getParameter("lat");

        LocationRequestDto locationRequestDto = LocationRequestDto.builder()
                .longitude(getValueInBigDecimal(longitude))
                .latitude(getValueInBigDecimal(latitude))
                .build();

        Location location = locationService
                .findByCoordinates(locationRequestDto)
                .orElseThrow(LocationNotFoundException::new);

        userLocationService.delete(session.getUser(), location);
    }

    private BigDecimal getValueInBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (Exception exception) {
            throw new IllegalArgumentException();
        }
    }

}
