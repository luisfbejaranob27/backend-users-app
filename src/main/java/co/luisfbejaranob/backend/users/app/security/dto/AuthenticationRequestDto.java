package co.luisfbejaranob.backend.users.app.security.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AuthenticationRequestDto implements Serializable
{
    private String username;

    private String password;
}
