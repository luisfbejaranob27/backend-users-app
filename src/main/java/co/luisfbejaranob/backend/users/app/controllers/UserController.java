package co.luisfbejaranob.backend.users.app.controllers;

import co.luisfbejaranob.backend.users.app.dto.UserDto;
import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.exceptions.UserErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.services.AuthenticationService;
import co.luisfbejaranob.backend.users.app.utils.exceptions.dto.ApiErrorDto;
import co.luisfbejaranob.backend.users.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
public class UserController
{
    private final UserService userService;

    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService)
    {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @GetMapping
    public ResponseEntity<Page<User>> findAll(Pageable pageable)
    {
        Page<User> users = userService.findAll(pageable);

        if(users.hasContent())
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(users);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(users);
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findById(id));
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findByUsername(username));
    }

    @GetMapping("exists/{filter}/{value}")
    public ResponseEntity<Boolean> exist(@PathVariable String filter, @PathVariable String value)
    {
        var result = switch (filter) {
            case "username" -> userService.existsByUsername(value);
            case "email" -> userService.existsByEmail(value);
            default -> throw new IllegalArgumentException("Invalid filter: %s".formatted(filter));
        };

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid UserDto user)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.create(user));
    }

    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable UUID id, @RequestBody UserDto user) throws IllegalAccessException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.update(id, user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id)
    {
        userService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @ExceptionHandler(UsersNotFoundException.class)
    public ResponseEntity<Object> handleUsersNotFoundException(UsersNotFoundException ex)
    {
        ApiErrorDto apiError =
                new ApiErrorDto(HttpStatus.NOT_FOUND, ex.getCode(), ex.getMessage());

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
