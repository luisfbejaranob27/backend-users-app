package co.luisfbejaranob.backend.users.app.security.controllers;

import co.luisfbejaranob.backend.users.app.security.dto.AuthenticationRequestDto;
import co.luisfbejaranob.backend.users.app.security.dto.AuthenticationResponseDto;
import co.luisfbejaranob.backend.users.app.security.dto.RegisteredDto;
import co.luisfbejaranob.backend.users.app.security.dto.UserDto;
import co.luisfbejaranob.backend.users.app.security.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthenticationController
{
    private final AuthenticationService service;

    public AuthenticationController(AuthenticationService service)
    {
        this.service = service;
    }

    @PostMapping("users/register")
    public ResponseEntity<RegisteredDto> register(@RequestBody @Valid UserDto userDto)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.register(userDto));
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

    @GetMapping("auth/validate-token")
    public ResponseEntity<Boolean> validate(@RequestParam String jwt)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.validateJwt(jwt));
    }
}
