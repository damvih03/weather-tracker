package com.damvih.dto.api.weather;

import com.damvih.utils.UnixLocalDateTimeDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import java.time.LocalDateTime;
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

    @JsonProperty("sys")
    private SysApiDto sys;

    @JsonProperty("visibility")
    private Integer visibility;

    @JsonProperty("dt")
    @JsonDeserialize(using = UnixLocalDateTimeDeserializer.class)
    private LocalDateTime calculationDateTime;

}
