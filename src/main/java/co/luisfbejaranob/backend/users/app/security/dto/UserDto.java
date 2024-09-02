package co.luisfbejaranob.backend.users.app.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class UserDto implements Serializable
{
    @NotBlank
    @Size(min = 4)
    private String username;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    @Size(min = 6)
    private String repeatPassword;

    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 4)
    private String name;
}
