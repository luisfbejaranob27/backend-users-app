package co.luisfbejaranob.backend.users.app.security.services;

import co.luisfbejaranob.backend.users.app.security.entities.Operation;
import co.luisfbejaranob.backend.users.app.security.exceptions.OperationErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.repositories.OperationRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static co.luisfbejaranob.backend.users.app.utils.ObjectUtil.updateValues;

@Service
public class OperationService
{
    private final OperationRepository repository;

    public OperationService(OperationRepository repository)
    {
        this.repository = repository;
    }

    public Page<Operation> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public List<Operation> findAllByPermitAllTrue() {
        return repository.findAllByPermitAllTrue();
    }

    public Operation findById(Long id)
    {
        return repository.findById(id).orElseThrow(() -> new OperationFoundException(id));
    }

    public Operation findByName(String name)
    {
        return repository.findByName(name).orElseThrow(() -> new OperationFoundException(name));
    }

    public Operation create(Operation operation)
    {
        return repository.save(operation);
    }

    public Operation update(Operation operation) throws IllegalAccessException
    {
        Operation operationFound = findById(operation.getId());

        return repository.save(updateValues(operationFound, operation));
    }

    public void deleteById(Long id)
    {
        boolean exists = repository.existsById(id);

        if(exists)
        {
            repository.deleteById(id);
        }
        else
        {
            throw new OperationFoundException(id);
        }
    }
}
