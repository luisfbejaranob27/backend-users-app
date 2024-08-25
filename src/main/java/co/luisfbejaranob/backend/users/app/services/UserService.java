package co.luisfbejaranob.backend.users.app.services;

import co.luisfbejaranob.backend.users.app.controllers.dto.UserDto;
import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.exceptions.UserExceptions.*;
import co.luisfbejaranob.backend.users.app.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import static co.luisfbejaranob.backend.users.app.utils.ObjectUtil.updateValues;
import static co.luisfbejaranob.backend.users.app.utils.UserMappers.toUserDto;
import static co.luisfbejaranob.backend.users.app.utils.UserMappers.userDtoList;

@Service
public class UserService
{
    private final UserRepository repository;

    public UserService(UserRepository repository)
    {
        this.repository = repository;
    }

    public List<UserDto> findAll()
    {
        var users = repository.findAll();

        if (users.isEmpty())
        {
            throw new UsersNotFoundException();
        }

        return userDtoList(users);
    }

    public User findById(UUID id)
    {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException(id.toString()));
    }

    public User findByUsername(String username)
    {
       return repository.findByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    }

    public boolean existsByUsername(String username)
    {
        if (!repository.existsByUsername(username))
        {
           throw new UserNotFoundException(username);
        }
        return true;
    }

    public boolean existsByEmail(String email)
    {
        if (!repository.existsByEmail(email))
        {
            throw new UserNotFoundException(email);
        }
        return true;
    }

    public UserDto create(User user)
    {
        return toUserDto(repository.save(user));
    }

    public UserDto update(UUID id, User user) throws IllegalAccessException {
        var userFound = findById(id);

        return toUserDto(repository.save(updateValues(userFound, user)));
    }

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
