package com.damvih.servlets;

import com.damvih.dao.UserLocationDao;
import com.damvih.dto.LocationRequestDto;
import com.damvih.dto.api.geocoding.LocationApiDto;
import com.damvih.dto.api.weather.WeatherApiResponseDto;
import com.damvih.entities.Location;
import com.damvih.entities.Session;
import com.damvih.services.WeatherApiService;
import com.damvih.utils.MapperUtil;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/home", ""})
public class HomeServlet extends BaseServlet {

    private WeatherApiService weatherApiService;
    private UserLocationDao userLocationDao;

    @Override
    public void init(ServletConfig config) throws ServletException {
        weatherApiService = (WeatherApiService) config
                .getServletContext()
                .getAttribute("WeatherApiService");

        userLocationDao = (UserLocationDao) config
                .getServletContext()
                .getAttribute("UserLocationDao");

        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = ((Session) request.getServletContext().getAttribute("session"));

        List<Location> locations = userLocationDao.findLocationsByUser(session.getUser());
        Map<Location, WeatherApiResponseDto> locationsWeathers = new HashMap<>();
        for (Location location : locations) {
            BigDecimal longitude = location.getLongitude();
            BigDecimal latitude = location.getLatitude();
            locationsWeathers.put(location, weatherApiService.getWeatherByCoordinates(longitude, latitude));
        }

        webContext.setVariable("locationsWeathers", locationsWeathers);
        templateEngine.process("index", webContext, response.getWriter());
    }

}
