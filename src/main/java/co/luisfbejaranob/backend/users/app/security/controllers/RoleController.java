package co.luisfbejaranob.backend.users.app.security.controllers;

import co.luisfbejaranob.backend.users.app.security.entities.Role;
import co.luisfbejaranob.backend.users.app.security.exceptions.RoleErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.services.RoleService;
import co.luisfbejaranob.backend.users.app.utils.exceptions.dto.ApiErrorDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("roles")
public class RoleController
{
    private final RoleService roleService;

    public RoleController(RoleService roleService)
    {
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<Page<Role>> findAll(Pageable pageable)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.findAll(pageable));
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Role> findById(@PathVariable Long id)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.findById(id));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Role> findByName(@PathVariable String name)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(roleService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody @Valid Role role)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roleService.create(role));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        roleService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleRoleNotFoundException(Exception ex)
    {
        ApiErrorDto apiError = new ApiErrorDto(
                HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage());

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
