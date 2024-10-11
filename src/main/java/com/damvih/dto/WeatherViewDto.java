package com.damvih.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WeatherViewDto {

    private BigDecimal longitude;
    private BigDecimal latitude;
    private String locationName;
    private String country;

    private BigDecimal temperature;
    private BigDecimal feelsLike;
    private String description;

    private Integer windSpeed;
    private Integer windDirection;

}
