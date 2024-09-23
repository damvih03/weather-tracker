package com.damvih.dto.api.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiDto {

    private Integer id;
    private String main;
    private String description;

}
