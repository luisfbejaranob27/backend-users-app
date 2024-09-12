package co.luisfbejaranob.backend.users.app.security.repositories;

import co.luisfbejaranob.backend.users.app.entities.User;
import co.luisfbejaranob.backend.users.app.security.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>
{
    Optional<RefreshToken> findByToken(String token);

    Optional<RefreshToken> findByOwner(User user);

    boolean existsByOwner(User owner);

    void deleteByOwner(User owner);
}
