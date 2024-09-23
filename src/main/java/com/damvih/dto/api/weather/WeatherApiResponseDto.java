package com.damvih.dto.api.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponseDto {

    @JsonProperty("weather")
    private List<WeatherApiDto> weather;

    @JsonProperty("main")
    private MainApiDto main;

    @JsonProperty("wind")
    private WindApiDto wind;

    @JsonProperty("clouds")
    private CloudsApiDto clouds;

    private Integer visibility;

}
