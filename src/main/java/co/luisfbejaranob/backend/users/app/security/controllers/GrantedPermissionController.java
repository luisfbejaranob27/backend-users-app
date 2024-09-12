package co.luisfbejaranob.backend.users.app.security.controllers;

import co.luisfbejaranob.backend.users.app.security.dto.GrantedPermissionDto;
import co.luisfbejaranob.backend.users.app.security.dto.ShowGrantedPermissionDto;
import co.luisfbejaranob.backend.users.app.security.exceptions.GrantedPermissionErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.exceptions.OperationErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.exceptions.RoleErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.services.GrantedPermissionService;
import co.luisfbejaranob.backend.users.app.utils.exceptions.dto.ApiErrorDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("permissions")
public class GrantedPermissionController
{
    private final GrantedPermissionService grantedPermissionService;

    public GrantedPermissionController(GrantedPermissionService grantedPermissionService)
    {
        this.grantedPermissionService = grantedPermissionService;
    }

    @GetMapping
    public ResponseEntity<Page<ShowGrantedPermissionDto>> findAll(Pageable pageable)
    {
        Page<ShowGrantedPermissionDto> showGrantedPermissionDtoPage = grantedPermissionService.findAll(pageable);

        if(showGrantedPermissionDtoPage.hasContent())
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(showGrantedPermissionDtoPage);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(showGrantedPermissionDtoPage);
    }

    @GetMapping("{id}")
    public ResponseEntity<ShowGrantedPermissionDto> findById(@PathVariable Long id)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(grantedPermissionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ShowGrantedPermissionDto> create(@RequestBody GrantedPermissionDto grantedPermissionDto)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(grantedPermissionService.create(grantedPermissionDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        grantedPermissionService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @ExceptionHandler(GrantedPermissionNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleGrantedPermissionNotFoundException(Exception ex)
    {
        ApiErrorDto apiError = new ApiErrorDto(
                HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage());

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
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

    @ExceptionHandler(OperationFoundException.class)
    public ResponseEntity<ApiErrorDto> handleOperationFoundException(Exception ex)
    {
        ApiErrorDto apiError = new ApiErrorDto(
                HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage());

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
