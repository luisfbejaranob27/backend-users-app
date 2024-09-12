package co.luisfbejaranob.backend.users.app.security.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshTokenRequestDto
{
    private String token;
}
