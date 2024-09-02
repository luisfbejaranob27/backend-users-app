package co.luisfbejaranob.backend.users.app.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;

@Builder
@Getter
@Setter
public class UserResponseDto implements Serializable
{
    private String username;

    private String name;

    private Collection<? extends GrantedAuthority> authorities;
}
