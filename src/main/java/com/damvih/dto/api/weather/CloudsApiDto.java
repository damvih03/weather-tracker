package com.damvih.dto.api.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CloudsApiDto {

    private Integer all;

}
