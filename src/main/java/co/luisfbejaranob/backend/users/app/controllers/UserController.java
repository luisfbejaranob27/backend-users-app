package co.luisfbejaranob.backend.users.app.controllers;

import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.exceptions.UserExceptions.*;
import co.luisfbejaranob.backend.users.app.security.services.AuthenticationService;
import co.luisfbejaranob.backend.users.app.utils.exceptions.dto.ApiErrorDto;
import co.luisfbejaranob.backend.users.app.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "http://localhost:5173/")
public class UserController
{
    private final UserService userService;

    private final AuthenticationService authenticationService;

    public UserController(UserService userService, AuthenticationService authenticationService)
    {
        this.userService = userService;
        this.authenticationService = authenticationService;
    }

    @PreAuthorize("hasAuthority('READ_ALL_USERS')")
    @GetMapping
    public ResponseEntity<List<User>> findAll()
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findAll());
    }

    @PreAuthorize("hasAuthority('READ_USER_BY_ID')")
    @GetMapping("{id}")
    public ResponseEntity<User> findById(@PathVariable UUID id)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findById(id));
    }

    @PreAuthorize("hasAuthority('READ_USER_BY_USERNAME')")
    @GetMapping("username/{username}")
    public ResponseEntity<User> findByUsername(@PathVariable String username)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.findByUsername(username));
    }

    @PreAuthorize("hasAuthority('EXIST_USER')")
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

    @PreAuthorize("hasAuthority('CREATE_USER')")
    @PostMapping
    public ResponseEntity<User> create(@RequestBody @Valid User user)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(authenticationService.create(user));
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    @PutMapping("{id}")
    public ResponseEntity<User> update(@PathVariable UUID id, @RequestBody User user) throws IllegalAccessException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.update(id, user));
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
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
