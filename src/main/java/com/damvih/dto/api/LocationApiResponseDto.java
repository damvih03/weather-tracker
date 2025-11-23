package com.damvih.dto.api;

import com.damvih.dto.location.LocationDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LocationApiResponseDto {

    private String name;

    @JsonProperty("lat")
    private BigDecimal latitude;

    @JsonProperty("lon")
    private BigDecimal longitude;

    private String country;

    private String state;

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof LocationApiResponseDto that)) return false;
        return Objects.equals(latitude, that.latitude) && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }

}
