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
public class LocationRequestDto {

    private BigDecimal latitude;
    private BigDecimal longitude;
    private String name;

}
