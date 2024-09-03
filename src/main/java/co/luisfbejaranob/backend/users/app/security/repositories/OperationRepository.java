package co.luisfbejaranob.backend.users.app.security.repositories;

import co.luisfbejaranob.backend.users.app.security.entities.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OperationRepository extends JpaRepository<Operation, Long>
{
    List<Operation> findAllByPermitAllTrue();

    Optional<Operation> findByName(String name);
}
