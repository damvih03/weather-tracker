package com.damvih.services;

import com.damvih.dto.GeocodedWeatherDto;
import com.damvih.dto.GeodataApiRequestDto;
import com.damvih.dto.LocationApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherApiService {

    private static final String WEATHER_SUFFIX = "/data/2.5/weather";
    private static final String GEOCODING_SUFFIX = "/geo/1.0/direct";
    private static final String UNITS_OF_MEASUREMENT = "metric";

    private final RestClient restClient;

    public List<LocationApiResponseDto> getLocationsByName(String locationName){
        return restClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path(GEOCODING_SUFFIX)
                        .queryParam("q", locationName)
                        .queryParam("limit", 5)
                        .build()
                )
                .retrieve()
                .body(new ParameterizedTypeReference<>() {});
    }

    public GeocodedWeatherDto getWeatherByCoordinates(GeodataApiRequestDto geodataApiRequestDto) {
        return restClient
                .method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder
                        .path(WEATHER_SUFFIX)
                        .queryParam("lat", geodataApiRequestDto.getLatitude())
                        .queryParam("lon", geodataApiRequestDto.getLongitude())
                        .queryParam("units", UNITS_OF_MEASUREMENT)
                        .build()
                )
                .retrieve()
                .body(GeocodedWeatherDto.class);
    }

}
