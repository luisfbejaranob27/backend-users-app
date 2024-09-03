package co.luisfbejaranob.backend.users.app.security.services;

import co.luisfbejaranob.backend.users.app.dto.UserDto;
import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.security.dto.*;
import co.luisfbejaranob.backend.users.app.security.exceptions.UserErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import static co.luisfbejaranob.backend.users.app.utils.mappers.UserMappers.*;

@Service
public class AuthenticationService
{
    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserService userService, JwtService jwtService, PasswordEncoder passwordEncoder, RoleService roleService, AuthenticationManager authenticationManager)
    {
        this.userService = userService;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
    }

    public User create(UserDto user)
    {
        User newUser = toEntity(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(roleService.findByName(user.getRole()));
        return userService.create(newUser);
    }

    public RegisteredDto register(UserRegisterDto userRegisterDto)
    {
        validatePassword(userRegisterDto);

        userRegisterDto.setRole("USER");
        User newUser = create(toUserDto(userRegisterDto));

        return toRegisteredDto(newUser, jwtService.generateToken(newUser));
    }

    private void validatePassword(UserRegisterDto userDto)
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
