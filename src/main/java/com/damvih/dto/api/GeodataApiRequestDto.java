package com.damvih.dto.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GeodataApiRequestDto {

    private BigDecimal latitude;
    private BigDecimal longitude;

}
