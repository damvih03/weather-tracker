package com.damvih.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonDeserialize(using = GeocodingApiResponseDeserializer.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeocodedWeatherDto {

    private String country;

    private String mainWeatherGroup;
    private String weatherIcon;
    private Integer actualTemperature;
    private Integer feelsLikeTemperature;
    private Integer pressure;
    private Integer humidity;
    private Double windSpeed;

}
