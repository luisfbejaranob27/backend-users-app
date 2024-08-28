package co.luisfbejaranob.backend.users.app.services;

import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.exceptions.UserExceptions.*;
import co.luisfbejaranob.backend.users.app.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

import static co.luisfbejaranob.backend.users.app.utils.ObjectUtil.updateValues;

@Service
public class UserService
{
    private final UserRepository repository;

    public UserService(UserRepository repository)
    {
        this.repository = repository;
    }

    public List<User> findAll()
    {
        var users = repository.findAll();

        if (users.isEmpty())
        {
            throw new UsersNotFoundException();
        }

        return users;
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
        return repository.existsByUsername(username);
    }

    public boolean existsByEmail(String email)
    {
        return repository.existsByEmail(email);
    }

    public User create(User user)
    {
        return repository.save(user);
    }

    public User update(UUID id, User user) throws IllegalAccessException {
        var userFound = findById(id);

        return repository.save(updateValues(userFound, user));
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
