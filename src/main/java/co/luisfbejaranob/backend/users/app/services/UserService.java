package co.luisfbejaranob.backend.users.app.services;

import co.luisfbejaranob.backend.users.app.dto.UserDto;
import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.exceptions.UserErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.repositories.UserRepository;
import co.luisfbejaranob.backend.users.app.security.services.RoleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static co.luisfbejaranob.backend.users.app.utils.ObjectUtil.updateValues;
import static co.luisfbejaranob.backend.users.app.utils.mappers.UserMappers.toEntity;

@Service
public class UserService
{
    private final UserRepository repository;

    private final RoleService roleService;

    public UserService(UserRepository repository, RoleService roleService)
    {
        this.repository = repository;
        this.roleService = roleService;
    }

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable)
    {
        var users = repository.findAll(pageable);

        if (users.isEmpty())
        {
            throw new UsersNotFoundException();
        }

        return users;
    }

    @Transactional(readOnly = true)
    public User findById(UUID id)
    {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    @Transactional(readOnly = true)
    public User findByUsername(String username)
    {
       return repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    @Transactional(readOnly = true)
    public boolean existsByUsername(String username)
    {
        return repository.existsByUsername(username);
    }

    @Transactional(readOnly = true)
    public boolean existsByEmail(String email)
    {
        return repository.existsByEmail(email);
    }

    @Transactional
    public User create(User user)
    {
        return repository.save(user);
    }

    @Transactional
    public User update(UUID id, UserDto user) throws IllegalAccessException {
        var userFound = findById(id);
        User newUser = toEntity(user);

        newUser.setRole(roleService.findByName(user.getRole()));
        return repository.save(updateValues(userFound, newUser));
    }

    @Transactional
    public void deleteById(UUID id)
    {
        var exists = repository.existsById(id);

        if (exists)
        {
            repository.deleteById(id);
        }
        else
        {
            throw new UserNotFoundException(id.toString());
        }
    }
}
