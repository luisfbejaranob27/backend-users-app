package co.luisfbejaranob.backend.users.app.security.services;

import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.exceptions.UserErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.entities.RefreshToken;
import co.luisfbejaranob.backend.users.app.security.exceptions.RefreshTokenErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.repositories.RefreshTokenRepository;
import co.luisfbejaranob.backend.users.app.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class RefreshTokenService
{
    private final RefreshTokenRepository repository;

    private final UserService userService;

    @Value("${jwt.refresh.expiration}")
    private Integer expiration;

    public RefreshTokenService(RefreshTokenRepository repository, UserService userService)
    {
        this.repository = repository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public RefreshToken findByToken(String token)
    {
        return repository.findByToken(token).orElseThrow(() -> new RefreshTokenNotFoundException(token));
    }

    @Transactional
    public RefreshToken create(UUID userId)
    {
        User user = userService.findById(userId);
        RefreshToken refreshToken = RefreshToken.builder()
                .token(UUID.randomUUID().toString())
                .owner(user)
                .build();

        return repository.save(refreshToken);
    }

    @Transactional
    public void deleteByOwner(User owner)
    {
        boolean exists = repository.existsByOwner(owner);

        if(exists)
        {
            repository.deleteByOwner(owner);
        }
        else
        {
            throw new UserNotFoundException(owner.getId().toString());
        }
    }
}
