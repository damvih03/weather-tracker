package com.damvih.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "locations", uniqueConstraints = @UniqueConstraint(columnNames = {"latitude", "longitude"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locations_sequence")
    @SequenceGenerator(name = "locations_sequence", sequenceName = "locations_seq", allocationSize = 1, initialValue = 1)
    private Long id;

    private String name;

    private BigDecimal latitude;

    private BigDecimal longitude;

}
