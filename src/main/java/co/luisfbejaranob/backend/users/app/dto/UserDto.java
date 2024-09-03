package co.luisfbejaranob.backend.users.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
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
    private String name;

    @NotBlank
    @NotBlank
    @Size(min = 5, max = 50)
    private String username;

    @NotBlank
    @Size(min = 8, message = "Must be at least 8 characters long")
    @Pattern(regexp = ".*[A-Z].*", message = "Must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*", message = "Must contain at least one lowercase letter")
    @Pattern(regexp = ".*\\d.*", message = "Must contain at least one digit")
    @Pattern(regexp = ".*[@$!%*?&].*", message = "Must contain at least one special character (@$!%*?&)")
    private String password;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "The email must have a valid format")
    private String email;

    @NotBlank
    private String role;
}
