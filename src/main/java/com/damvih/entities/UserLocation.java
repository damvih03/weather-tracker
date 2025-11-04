package com.damvih.entities;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users_locations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLocation {

    @EmbeddedId
    private UserLocationKey userLocationKey;

}
