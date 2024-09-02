package co.luisfbejaranob.backend.users.app.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Builder
@Getter
@Setter
public class RegisteredDto implements Serializable
{
    private UUID id;

    private String name;

    private String username;

    private String role;

    private String jwt;
}
