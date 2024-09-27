package com.damvih.services;

import com.damvih.dto.LocationRequestDto;
import com.damvih.dto.api.geocoding.GeocodingApiResponseDto;
import com.damvih.dto.api.geocoding.LocationApiDto;
import com.damvih.dto.api.weather.WeatherApiResponseDto;
import com.damvih.exceptions.ExternalApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

public class WeatherApiService {

    private static final String MAIN_URL = "https://api.openweathermap.org";
    private static final String WEATHER_SUFFIX = "/data/2.5/weather";
    private static final String GEOCODING_SUFFIX = "/geo/1.0/direct";
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public GeocodingApiResponseDto getLocationsByName(String locationName) {
        try {
            URI uri = buildUriForGeocodingRequest(locationName);
            HttpRequest request = buildRequest(uri);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            List<LocationApiDto> locations = objectMapper.readValue(response.body(), new TypeReference<>() {});
            return new GeocodingApiResponseDto(locationName, locations);
        } catch (Exception exception) {
            throw new ExternalApiException();
        }
    }

    public WeatherApiResponseDto getWeatherByCoordinates(BigDecimal longitude, BigDecimal latitude) {
        try {
            URI uri = buildUriForWeatherRequest(longitude, latitude);
            HttpRequest request = buildRequest(uri);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return objectMapper.readValue(response.body(), WeatherApiResponseDto.class);
        } catch (Exception exception) {
            throw new ExternalApiException();
        }
    }

    private HttpRequest buildRequest(URI uri) {
        return HttpRequest.newBuilder()
                .uri(uri)
                .GET()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    private URI buildUriForGeocodingRequest(String locationName) {
        return URI.create(
                MAIN_URL
                        + GEOCODING_SUFFIX
                        + "?q=" + locationName
                        + "&limit=5"
                        + "&appid=" + getKey()
        );
    }

    private URI buildUriForWeatherRequest(BigDecimal longitude, BigDecimal latitude) {
        return URI.create(
                MAIN_URL
                        + WEATHER_SUFFIX
                        + "?lat=" + latitude
                        + "&lon=" + longitude
                        + "&appid=" + getKey()
        );
    }

    private String getKey() {
        return Objects.requireNonNull(System.getenv("API_KEY"));
    }

}
