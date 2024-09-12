package co.luisfbejaranob.backend.users.app.security.controllers;

import co.luisfbejaranob.backend.users.app.exceptions.UserErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.dto.*;
import co.luisfbejaranob.backend.users.app.security.exceptions.RefreshTokenErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.services.AuthenticationService;
import co.luisfbejaranob.backend.users.app.utils.exceptions.dto.ApiErrorDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class AuthenticationController
{
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service)
    {
        this.service = service;
    }

    @PostMapping("users/register")
    public ResponseEntity<RegisteredDto> register(@RequestBody @Valid UserRegisterDto userDto)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.register(userDto));
    }

    @GetMapping("users/profile")
    public ResponseEntity<UserResponseDto> findProfile()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findLoggedInUser());
    }

    @PostMapping("auth/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(
            @RequestBody @Valid AuthenticationRequestDto request
    )
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.authenticate(request));
    }

    @PostMapping("auth/refresh-token")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(@RequestBody @Valid RefreshTokenRequestDto request)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.refreshToken(request));
    }

    @GetMapping("auth/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.validateJwt(jwt));
    }

    @GetMapping("auth/logout/{id}")
    public ResponseEntity<LogoutResponseDto> logout(@PathVariable UUID id)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.logout(id));
    }

    @ExceptionHandler(RefreshTokenNotFoundException.class)
    public ResponseEntity<Object> handleRefreshTokenNotFoundException(RefreshTokenNotFoundException ex)
    {
        ApiErrorDto apiError =
                new ApiErrorDto(HttpStatus.NOT_FOUND, null, ex.getMessage());

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(RefreshTokenExpiredException.class)
    public ResponseEntity<Object> handleRefreshTokenExpiredException(RefreshTokenExpiredException ex)
    {
        ApiErrorDto apiError =
                new ApiErrorDto(HttpStatus.UNAUTHORIZED, ex.getMessage());

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex)
    {
        ApiErrorDto apiError =
                new ApiErrorDto(HttpStatus.NOT_FOUND, ex.getCode(), ex.getMessage());

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
