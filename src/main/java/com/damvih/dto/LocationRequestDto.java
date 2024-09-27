package com.damvih.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocationRequestDto {

    private String name;
    private BigDecimal longitude;
    private BigDecimal latitude;

}
