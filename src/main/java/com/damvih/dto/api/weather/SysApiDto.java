package com.damvih.dto.api.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysApiDto {

    @JsonProperty("sunrise")
    @JsonDeserialize()
    private LocalDateTime sunriseTime;

    @JsonProperty("sunset")
    @JsonDeserialize()
    private LocalDateTime sunsetTime;

}
