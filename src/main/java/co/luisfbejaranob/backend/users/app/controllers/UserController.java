package co.luisfbejaranob.backend.users.app.controllers;

import co.luisfbejaranob.backend.users.app.controllers.dto.UserDto;
import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.exceptions.UserExceptions.*;
import co.luisfbejaranob.backend.users.app.exceptions.dto.ApiErrorDto;
import co.luisfbejaranob.backend.users.app.services.UserService;
import jakarta.validation.Valid;
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
    private final UserService service;

    public UserController(UserService service)
    {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> findAll()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findById(id));
    }

    @GetMapping("username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.findByUsername(username));
    }

    @GetMapping("{filter}/{value}")
    public ResponseEntity<Boolean> exits(@PathVariable String filter, @PathVariable String value)
    {
        var result = switch (filter) {
            case "username" -> service.existsByUsername(value);
            case "email" -> service.existsByEmail(value);
            default -> throw new IllegalArgumentException("Invalid filter: %s".formatted(filter));
        };

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(result);
    }

    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody @Valid User user)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(user));
    }

    @PutMapping("{id}")
    public ResponseEntity<UserDto> update(@PathVariable UUID id, @RequestBody User user) throws IllegalAccessException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(service.update(id, user));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable UUID id)
    {
        service.deleteById(id);

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
