package co.luisfbejaranob.backend.users.app.security.services;

import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.services.UserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
    private final UserService userService;

    public UserDetailsServiceImpl(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException
    {
        return userService.findByUsername(username);
    }

    public User findById(UUID id)
    {
        return userService.findById(id);
    }
}
