package com.damvih.dto.api.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    @JsonProperty("dt")
    @JsonDeserialize()
    private LocalDateTime calculationDateTime;

}
