package com.damvih.services;

import com.damvih.dto.api.geocoding.GeocodingApiResponseDto;
import com.damvih.dto.api.geocoding.LocationApiDto;
import com.damvih.dto.api.weather.WeatherApiResponseDto;
import com.damvih.exceptions.ExternalApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class WeatherApiServiceTest {

    @InjectMocks
    private WeatherApiService weatherApiService;
    @Mock
    private HttpClient httpClient;
    @Mock
    private HttpResponse<String> httpResponse;

    @BeforeEach
    public void setup() {
        weatherApiService = new WeatherApiService(httpClient);
    }

    @Test
    public void testSuccessfulFindingLocationsByName() throws IOException, InterruptedException {
        String jsonResponse = "[{\"name\":\"Khabarovsk\",\"local_names\":{\"ja\":\"ハバロフスク\",\"nl\":\"Chabarovsk\"," +
                "\"sk\":\"Chabarovsk\",\"da\":\"Khabarovsk\",\"it\":\"Chabarovsk\"," +
                "\"ru\":\"Хабаровск\",\"pl\":\"Chabarowsk\",\"ascii\":\"Khabarovsk\",\"fi\":\"Habarovsk\"," +
                "\"lt\":\"Chabarovskas\",\"feature_name\":\"Khabarovsk\",\"fa\":\"خاباروفسک\",\"fr\":\"Khabarovsk\",\"de\":\"Khabarovsk\"" +
                ",\"mr\":\"खबारोव्स्क\",\"be\":\"Хабараўск\",\"uk\":\"Хабаровськ\",\"he\":\"חברובסק\",\"ar\":\"خاباروفسك\",\"cs\":\"Chabarovsk\",\"ca\":\"Khabàrovsk\"," +
                "\"bs\":\"Habarovsk\",\"hr\":\"Habarovsk\",\"ro\":\"Habarovsk\",\"sl\":\"Habarovsk\",\"es\":\"Jabárovsk\",\"et\":\"Habarovsk\"" +
                ",\"bg\":\"Хабаровск\",\"zh\":\"伯力\",\"en\":\"Khabarovsk\",\"ko\":\"하바롭스크\",\"af\":\"Chabarofsk\"}" +
                ",\"lat\":48.481403,\"lon\":135.076935,\"country\":\"RU\",\"state\":\"Khabarovsk Krai\"}," +
                "{\"name\":\"Khabarovsk\",\"local_names\":{\"en\":\"Khabarovsk\",\"ar\":\"Khabarovsk\"}," +
                "\"lat\":25.2464012,\"lon\":55.17521464485682,\"country\":\"AE\",\"state\":\"Dubai\"}]";

        Mockito.when(httpClient.send(
                        Mockito.any(HttpRequest.class),
                        Mockito.eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(httpResponse);

        Mockito.when(httpResponse.body()).thenReturn(jsonResponse);

        GeocodingApiResponseDto geocodingResponse = weatherApiService.getLocationsByName("Khabarovsk");
        LocationApiDto locationApi = geocodingResponse.getLocations().getFirst();

        Assertions.assertEquals(new BigDecimal("135.076935"), locationApi.getLongitude());
        Assertions.assertEquals(new BigDecimal("48.481403"), locationApi.getLatitude());
        Assertions.assertEquals("RU", locationApi.getCountry());
    }

    @Test
    public void testFindingLocationsByNonExistingName() throws IOException, InterruptedException {
        String emptyResponse = "[]";

        Mockito.when(httpClient.send(
                        Mockito.any(HttpRequest.class),
                        Mockito.eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(httpResponse);

        Mockito.when(httpResponse.body()).thenReturn(emptyResponse);
        List<LocationApiDto> locations = weatherApiService
                .getLocationsByName("notExistingName")
                .getLocations();

        Assertions.assertTrue(locations.isEmpty());
    }

    @Test
    public void testSuccessfulGettingWeatherByCoordinates() throws IOException, InterruptedException {
        String jsonResponse = "{\"coord\":{\"lon\":135.0769,\"lat\":48.4814},\"weather\":[{\"id\":800," +
                "\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}]," +
                "\"base\":\"stations\",\"main\":{\"temp\":293.03,\"feels_like\":291.86,\"temp_min\":293.03," +
                "\"temp_max\":293.03,\"pressure\":1020,\"humidity\":30,\"sea_level\":1020,\"grnd_level\":1009}," +
                "\"visibility\":10000,\"wind\":{\"speed\":5,\"deg\":250},\"clouds\":{\"all\":0},\"dt\":1728027545," +
                "\"sys\":{\"type\":1,\"id\":8867,\"country\":\"RU\",\"sunrise\":1727989385,\"sunset\":1728030814}" +
                ",\"timezone\":36000,\"id\":2022890,\"name\":\"Khabarovsk\",\"cod\":200}";

        Mockito.when(httpClient.send(
                        Mockito.any(HttpRequest.class),
                        Mockito.eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(httpResponse);

        Mockito.when(httpResponse.body()).thenReturn(jsonResponse);

        BigDecimal longitude = new BigDecimal("135.0769");
        BigDecimal latitude = new BigDecimal("48.4814");
        WeatherApiResponseDto weatherResponse = weatherApiService.getWeatherByCoordinates(longitude, latitude);

        Assertions.assertEquals(1020, weatherResponse.getMain().getPressure());
        Assertions.assertEquals(250, weatherResponse.getWind().getDirection());
        Assertions.assertEquals("Clear", weatherResponse.getWeather().getFirst().getMain());
    }

    @Test
    public void testGettingResponseWithServerError() throws IOException, InterruptedException {
        Mockito.when(httpClient.send(
                        Mockito.any(HttpRequest.class),
                        Mockito.eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(httpResponse);

        Mockito.when(httpResponse.statusCode()).thenReturn(500);

        Assertions.assertThrows(
                ExternalApiException.class,
                () -> weatherApiService.getLocationsByName("Khabarovsk")
        );
    }

    @Test
    public void testGettingResponseWithClientError() throws IOException, InterruptedException {
        Mockito.when(httpClient.send(
                        Mockito.any(HttpRequest.class),
                        Mockito.eq(HttpResponse.BodyHandlers.ofString())))
                .thenReturn(httpResponse);

        Mockito.when(httpResponse.statusCode()).thenReturn(400);

        Assertions.assertThrows(
                ExternalApiException.class,
                () -> weatherApiService.getLocationsByName("Khabarovsk")
        );
    }

}
