package co.luisfbejaranob.backend.users.app.security.services;

import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.security.dto.*;
import co.luisfbejaranob.backend.users.app.security.exceptions.UserErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static co.luisfbejaranob.backend.users.app.utils.mappers.UserMappers.*;

@Service
public class AuthenticationService
{
    private final UserService userService;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserService userService, JwtService jwtService, AuthenticationManager authenticationManager)
    {
        this.userService = userService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public RegisteredDto register(UserDto userDto)
    {
        validatePassword(userDto);

        User newUser = userService.create(toEntity(userDto));
        return toRegisteredDto(newUser, jwtService.generateToken(newUser));
    }

    private void validatePassword(UserDto userDto)
    {
        if(!StringUtils.hasText(userDto.getPassword()) || !StringUtils.hasText(userDto.getRepeatPassword()))
        {
            throw new InvalidPasswordException();
        }

        if(!userDto.getPassword().equals(userDto.getRepeatPassword()))
        {
            throw new InvalidPasswordException();
        }
    }

    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request)
    {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        authenticationManager.authenticate(authentication);

        return new AuthenticationResponseDto(jwtService.generateToken(
                userService.findByUsername(request.getUsername())
        ));
    }

    public boolean validateJwt(String jwt)
    {
        return jwtService.validate(jwt);
    }

    public UserResponseDto findLoggedInUser()
    {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findByUsername(authenticationToken.getPrincipal().toString());

        return UserResponseDto
                .builder()
                .username(user.getUsername())
                .name(user.getName())
                .authorities(user.getAuthorities())
                .build();
    }
}
