package com.damvih.servlets;

import com.damvih.dto.LocationRequestDto;
import com.damvih.entities.Location;
import com.damvih.entities.Session;
import com.damvih.exceptions.LocationNotFoundException;
import com.damvih.services.LocationService;
import com.damvih.services.UserLocationService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/delete-location")
public class DeleteLocationServlet extends BaseServlet {

    private LocationService locationService;
    private UserLocationService userLocationService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        locationService = (LocationService) config
                .getServletContext()
                .getAttribute("LocationService");

        userLocationService = (UserLocationService) config
                .getServletContext()
                .getAttribute("UserLocationService");

        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session session = (Session) request.getAttribute("session");

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
        response.sendRedirect("/home");
    }

}
