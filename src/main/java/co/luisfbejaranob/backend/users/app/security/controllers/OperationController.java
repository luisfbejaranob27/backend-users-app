package co.luisfbejaranob.backend.users.app.security.controllers;

import co.luisfbejaranob.backend.users.app.security.entities.Operation;
import co.luisfbejaranob.backend.users.app.security.exceptions.OperationErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.services.OperationService;
import co.luisfbejaranob.backend.users.app.utils.exceptions.dto.ApiErrorDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("operations")
public class OperationController
{
    private final OperationService operationService;

    public OperationController(OperationService operationService)
    {
        this.operationService = operationService;
    }

    @GetMapping
    public ResponseEntity<Page<Operation>> findAll(Pageable pageable)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(operationService.findAll(pageable));
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Operation> findById(@PathVariable Long id)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(operationService.findById(id));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Operation> findByName(@PathVariable String name)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(operationService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Operation> create(@RequestBody @Valid Operation operation)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(operationService.create(operation));
    }

    @PutMapping
    public ResponseEntity<Operation> update(@RequestBody @Valid Operation operation) throws IllegalAccessException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(operationService.update(operation));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id)
    {
        operationService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
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
