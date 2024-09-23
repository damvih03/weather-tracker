package com.damvih.dto.api.geocoding;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class GeocodingApiResponseDto {

    private String name;
    private List<LocationApiDto> locations;

}
