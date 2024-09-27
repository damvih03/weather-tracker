package com.damvih.servlets;

import com.damvih.dto.LocationRequestDto;
import com.damvih.dto.api.geocoding.GeocodingApiResponseDto;
import com.damvih.services.LocationService;
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

    @Override
    public void init(ServletConfig config) throws ServletException {
        weatherApiService = (WeatherApiService) config
                .getServletContext()
                .getAttribute("WeatherApiService");

        locationService = (LocationService) config
                .getServletContext()
                .getAttribute("LocationService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cityName = request.getParameter("city");

        GeocodingApiResponseDto geocodingApiResponse = weatherApiService.getLocationsByName(cityName);
        request.setAttribute("locations", geocodingApiResponse);
        request.getRequestDispatcher("/WEB-INF/locations.html").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cityName = request.getParameter("city");
        String longitude = request.getParameter("lon");
        String latitude = request.getParameter("lat");
        
        LocationRequestDto locationRequestDto = LocationRequestDto.builder()
                .name(cityName)
                .longitude(getValueInBigDecimal(longitude))
                .latitude(getValueInBigDecimal(latitude))
                .build();

        locationService.save(locationRequestDto);
        response.sendRedirect("/home");
    }

    private BigDecimal getValueInBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (Exception exception) {
            throw new IllegalArgumentException();
        }
    }

}
