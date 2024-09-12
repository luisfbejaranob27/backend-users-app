package co.luisfbejaranob.backend.users.app.security.services;

import co.luisfbejaranob.backend.users.app.dto.UserDto;
import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.security.dto.*;
import co.luisfbejaranob.backend.users.app.security.entities.RefreshToken;
import co.luisfbejaranob.backend.users.app.security.exceptions.RefreshTokenErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.exceptions.UserErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

import static co.luisfbejaranob.backend.users.app.utils.mappers.UserMappers.*;

@Service
public class AuthenticationService
{
    private final UserService userService;

    private final JwtService jwtService;

    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    private final RoleService roleService;

    private final AuthenticationManager authenticationManager;

    @Value("${jwt.token.expiration}")
    private Integer expirationToken;

    @Value("${jwt.refresh.expiration}")
    private Integer expirationRefreshToken;

    public AuthenticationService(UserService userService, JwtService jwtService, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder, RoleService roleService, AuthenticationManager authenticationManager)
    {
        this.userService = userService;
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public User create(UserDto user)
    {
        User newUser = toEntity(user);
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(roleService.findByName(user.getRole()));
        return userService.create(newUser);
    }

    @Transactional
    public RegisteredDto register(UserRegisterDto userRegisterDto)
    {
        validatePassword(userRegisterDto);

        userRegisterDto.setRole("USER");
        User newUser = create(toUserDto(userRegisterDto));

        return toRegisteredDto(newUser);
    }

    @Transactional(readOnly = true)
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

    @Transactional
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto request)
    {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );

        authenticationManager.authenticate(authentication);

        User user = userService.findByUsername(request.getUsername());
        String token = jwtService.generateToken(user, expirationToken);
        RefreshToken refresh = refreshTokenService.create(user.getId());

        return new AuthenticationResponseDto(
                token,
                refresh.getToken()
        );
    }

    @Transactional(readOnly = true)
    public AuthenticationResponseDto refreshToken(RefreshTokenRequestDto request)
    {
        RefreshToken refresh = refreshTokenService.findByToken(request.getToken());

        if(refresh != null)
        {
            User owner = refresh.getOwner();
            return new AuthenticationResponseDto(
                    jwtService.generateToken(owner, expirationRefreshToken),
                    refresh.getToken()
            );
        }

        throw new RefreshTokenExpiredException();
    }

    @Transactional
    public LogoutResponseDto logout(UUID id)
    {
        User user = userService.findById(id);
        refreshTokenService.deleteByOwner(user);
        return new LogoutResponseDto("User logged out");
    }

    @Transactional(readOnly = true)
    public boolean validateJwt(String jwt)
    {
        return jwtService.validate(jwt);
    }

    @Transactional(readOnly = true)
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
