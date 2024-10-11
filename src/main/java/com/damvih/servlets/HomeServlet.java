package com.damvih.servlets;

import com.damvih.dao.UserLocationDao;
import com.damvih.dto.WeatherViewDto;
import com.damvih.dto.api.weather.WeatherApiDto;
import com.damvih.dto.api.weather.WeatherApiResponseDto;
import com.damvih.entities.Location;
import com.damvih.entities.Session;
import com.damvih.services.WeatherApiService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        Session session = (Session) request.getAttribute("session");

        List<Location> locations = userLocationDao.findLocationsByUser(session.getUser());
        List<WeatherViewDto> weatherCards = new ArrayList<>();
        for (Location location : locations) {
            BigDecimal longitude = location.getLongitude();
            BigDecimal latitude = location.getLatitude();
            WeatherApiResponseDto weatherApiResponseDto = weatherApiService.getWeatherByCoordinates(longitude, latitude);

            weatherCards.add(buildWeatherCard(location, weatherApiResponseDto));
        }

        webContext.setVariable("weatherCards", weatherCards);
        templateEngine.process("index", webContext, response.getWriter());
    }

    private WeatherViewDto buildWeatherCard(Location location, WeatherApiResponseDto weatherApiResponseDto) {
        WeatherApiDto weatherApiDto = weatherApiResponseDto.getWeather().getFirst();
        return new WeatherViewDto(
                location.getLongitude(),
                location.getLatitude(),
                location.getName(),
                weatherApiResponseDto.getSys().getCountry(),

                weatherApiResponseDto.getMain().getTemperature(),
                weatherApiResponseDto.getMain().getFeelsLike(),
                weatherApiDto.getDescription(),

                weatherApiResponseDto.getWind().getSpeed(),
                weatherApiResponseDto.getWind().getDirection()
        );
    }

}
