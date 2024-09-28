package com.damvih.dto.api.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainApiDto {

    @JsonProperty("temp")
    private BigDecimal temperature;

    @JsonProperty("feels_like")
    private BigDecimal feelsLike;

    @JsonProperty("temp_min")
    private BigDecimal minTemperature;

    @JsonProperty("temp_max")
    private BigDecimal maxTemperature;

    private Integer pressure;

    private Integer humidity;

}
