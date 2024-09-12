package co.luisfbejaranob.backend.users.app.security.controllers;

import co.luisfbejaranob.backend.users.app.security.entities.Module;
import co.luisfbejaranob.backend.users.app.security.exceptions.ModuleErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.services.ModuleService;
import co.luisfbejaranob.backend.users.app.utils.exceptions.dto.ApiErrorDto;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("modules")
public class ModuleController
{
    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public ResponseEntity<Page<Module>> findAll(Pageable pageable)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(moduleService.findAll(pageable));
    }

    @GetMapping("id/{id}")
    public ResponseEntity<Module> findById(@PathVariable Long id)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(moduleService.findById(id));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Module> findByName(@PathVariable String name)
    {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(moduleService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Module> create(@RequestBody @Valid Module module)
    {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(moduleService.create(module));
    }

    @PutMapping
    public ResponseEntity<Module> update(@RequestBody @Valid Module module) throws IllegalAccessException {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(moduleService.update(module));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        moduleService.deleteById(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @ExceptionHandler(ModuleNotFoundException.class)
    public ResponseEntity<ApiErrorDto> handleModuleNotFoundException(Exception ex)
    {
        ApiErrorDto apiError = new ApiErrorDto(
                HttpStatus.NOT_FOUND,
                ex.getLocalizedMessage());

        return new ResponseEntity<>(
                apiError, new HttpHeaders(), apiError.getStatus());
    }
}
