package com.damvih.dto.session;

import com.damvih.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SessionDto {

    private UUID id;
    private UserDto userDto;
    private LocalDateTime expiresAt;

}
