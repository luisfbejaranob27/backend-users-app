package co.luisfbejaranob.backend.users.app.security.repositories;

import co.luisfbejaranob.backend.users.app.security.entities.Module;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModuleRepository extends JpaRepository<Module, Long>
{
    Optional<Module> findByName(String name);
}
