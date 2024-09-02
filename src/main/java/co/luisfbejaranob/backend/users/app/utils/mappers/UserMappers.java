package co.luisfbejaranob.backend.users.app.utils.mappers;

import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.entities.enumerations.Role;
import co.luisfbejaranob.backend.users.app.security.dto.RegisteredDto;
import co.luisfbejaranob.backend.users.app.security.dto.UserDto;
import co.luisfbejaranob.backend.users.app.security.services.JwtService;

public class UserMappers
{
    private static JwtService service;

    private UserMappers()
    {
        service = new JwtService();
    }

    public static User toEntity(UserDto dto)
    {
        return User
                .builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .role(Role.ROLE_USER)
                .build();
    }

    public static RegisteredDto toRegisteredDto(User user, String token)
    {
        return RegisteredDto.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .role(user.getRole().name())
                .jwt(token)
                .build();
    }
}
