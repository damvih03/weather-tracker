package com.damvih.services;

import com.damvih.dto.GeocodedWeatherDto;
import com.damvih.dto.GeodataApiRequestDto;
import com.damvih.dto.LocationApiResponseDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClient.RequestBodyUriSpec;
import org.springframework.web.client.RestClient.ResponseSpec;

import java.math.BigDecimal;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class WeatherApiServiceTest {

    @InjectMocks
    private WeatherApiService weatherApiService;

    @Mock
    private RestClient restClient;

    @Mock
    private RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private ResponseSpec responseSpec;

    @Test
    public void testGettingLocationByExistingName() {
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

        Mockito.when(restClient.method(HttpMethod.GET)).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri(Mockito.any(Function.class))).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.onStatus(Mockito.any(Predicate.class), Mockito.any(ResponseSpec.ErrorHandler.class))).thenReturn(responseSpec);
        Mockito.when(responseSpec.body(Mockito.any(ParameterizedTypeReference.class))).thenAnswer(invocation -> {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, new TypeReference<List<LocationApiResponseDto>>() {});
        });
        List<LocationApiResponseDto> locations = weatherApiService.getLocationsByName("Khabarovsk");

        Assertions.assertEquals(2, locations.size());
        Assertions.assertEquals(new BigDecimal("135.076935"), locations.getFirst().getLongitude());
        Assertions.assertEquals("RU",  locations.getFirst().getCountry());
        Assertions.assertEquals("AE", locations.getLast().getCountry());
    }

    @Test
    public void testGettingLocationByNonExistingName() {
        String jsonResponse = "[]";

        Mockito.when(restClient.method(HttpMethod.GET)).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri(Mockito.any(Function.class))).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.onStatus(Mockito.any(Predicate.class), Mockito.any(ResponseSpec.ErrorHandler.class))).thenReturn(responseSpec);
        Mockito.when(responseSpec.body(Mockito.any(ParameterizedTypeReference.class))).thenAnswer(invocation -> {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, new TypeReference<List<LocationApiResponseDto>>() {});
        });
        List<LocationApiResponseDto> locations = weatherApiService.getLocationsByName("1109unkn0Wn1907");

        Assertions.assertTrue(locations.isEmpty());
    }

    @Test
    public void testGettingGeocodedWeather() {
        String jsonResponse = "{\"coord\":{\"lon\":135.0769,\"lat\":48.4814},\"weather\":[{\"id\":800," +
                "\"main\":\"Clear\",\"description\":\"clear sky\",\"icon\":\"01d\"}]," +
                "\"base\":\"stations\",\"main\":{\"temp\":10.94,\"feels_like\":9.4,\"temp_min\":10.94," +
                "\"temp_max\":10.94,\"pressure\":1020,\"humidity\":30,\"sea_level\":1020,\"grnd_level\":1009}," +
                "\"visibility\":10000,\"wind\":{\"speed\":5,\"deg\":250},\"clouds\":{\"all\":0},\"dt\":1728027545," +
                "\"sys\":{\"type\":1,\"id\":8867,\"country\":\"RU\",\"sunrise\":1727989385,\"sunset\":1728030814}" +
                ",\"timezone\":36000,\"id\":2022890,\"name\":\"Khabarovsk\",\"cod\":200}";;

        Mockito.when(restClient.method(HttpMethod.GET)).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.uri(Mockito.any(Function.class))).thenReturn(requestBodyUriSpec);
        Mockito.when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        Mockito.when(responseSpec.onStatus(Mockito.any(Predicate.class), Mockito.any(ResponseSpec.ErrorHandler.class))).thenReturn(responseSpec);
        Mockito.when(responseSpec.body(GeocodedWeatherDto.class)).thenAnswer(invocation -> {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(jsonResponse, GeocodedWeatherDto.class);
        });

        GeodataApiRequestDto geodataApiRequestDto = new GeodataApiRequestDto(new BigDecimal("48.4814"), new BigDecimal("135.0769"));
        GeocodedWeatherDto geocodedWeatherDto = weatherApiService.getWeatherByCoordinates(geodataApiRequestDto);

        Assertions.assertEquals(1020, geocodedWeatherDto.getPressure());
        Assertions.assertEquals(30, geocodedWeatherDto.getHumidity());
        Assertions.assertEquals("Clear", geocodedWeatherDto.getMainWeatherGroup());
    }

}
