package com.damvih.services;

import com.damvih.dto.api.geocoding.GeocodingApiResponseDto;
import com.damvih.dto.api.geocoding.LocationApiDto;
import com.damvih.dto.api.weather.WeatherApiResponseDto;
import com.damvih.exceptions.ExternalApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class WeatherApiService {

    private static final String MAIN_URL = "https://api.openweathermap.org";
    private static final String WEATHER_SUFFIX = "/data/2.5/weather";
    private static final String GEOCODING_SUFFIX = "/geo/1.0/direct";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final HttpClient client;

    public WeatherApiService() {
        this.client = HttpClient.newHttpClient();
    }

    public GeocodingApiResponseDto getLocationsByName(String locationName) {
        try {
            URI uri = buildUriForGeocodingRequest(locationName);
            HttpRequest request = buildRequest(uri);
            HttpResponse<String> response = getResponseIfValid(request);
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
            HttpResponse<String> response = getResponseIfValid(request);
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

    private HttpResponse<String> getResponseIfValid(HttpRequest request) throws IOException, InterruptedException {

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        int responseCode = response.statusCode();

        if (isClientError(responseCode) || isServerError(responseCode)) {
            throw new ExternalApiException();
        }

        return response;

    }

    private boolean isClientError(int responseCode) {
        return responseCode >= 400 && responseCode < 500;
    }

    private boolean isServerError(int responseCode) {
        return responseCode >= 500 && responseCode < 600;
    }

    private String getKey() {
        return Objects.requireNonNull(System.getenv("API_KEY"));
    }

}
