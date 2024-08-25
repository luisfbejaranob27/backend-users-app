package co.luisfbejaranob.backend.users.app.utils;

import co.luisfbejaranob.backend.users.app.controllers.dto.UserDto;
import co.luisfbejaranob.backend.users.app.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public final class UserMappers
{
    private UserMappers()
    {}

    public static UserDto toUserDto(User user)
    {
        return UserDto.builder()
                .id(user.getId().toString())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createAt(user.getCreateAt())
                .updateAt(user.getUpdateAt())
                .build();
    }

    public static List<UserDto> userDtoList(List<User> users)
    {
        return users.stream()
                .map(UserMappers::toUserDto)
                .collect(Collectors.toList());
    }
}
