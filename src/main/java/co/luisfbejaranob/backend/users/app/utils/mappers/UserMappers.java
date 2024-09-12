package co.luisfbejaranob.backend.users.app.utils.mappers;

import co.luisfbejaranob.backend.users.app.dto.UserDto;
import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.security.dto.RegisteredDto;
import co.luisfbejaranob.backend.users.app.security.dto.UserRegisterDto;

public class UserMappers
{
    private UserMappers()
    {}

    public static User toEntity(UserDto dto)
    {
        return User
                .builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .build();
    }

    public static UserDto toUserDto(UserRegisterDto dto)
    {
        return UserDto
                .builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();
    }

    public static RegisteredDto toRegisteredDto(User user)
    {
        return RegisteredDto.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole().getName())
                .build();
    }
}
