package co.luisfbejaranob.backend.users.app.security.services;

import co.luisfbejaranob.backend.users.app.security.entities.Module;
import co.luisfbejaranob.backend.users.app.security.exceptions.ModuleErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.repositories.ModuleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static co.luisfbejaranob.backend.users.app.utils.ObjectUtil.updateValues;

@Service
public class ModuleService
{
    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository)
    {
        this.moduleRepository = moduleRepository;
    }

    public Page<Module> findAll(Pageable pageable)
    {
        return moduleRepository.findAll(pageable);
    }

    public Module findById(Long id)
    {
        return moduleRepository.findById(id).orElseThrow(() -> new ModuleNotFoundException(id));
    }

    public Module findByName(String name)
    {
        return moduleRepository.findByName(name).orElseThrow(() -> new ModuleNotFoundException(name));
    }

    public Module create(Module module)
    {
        return moduleRepository.save(module);
    }

    public Module update(Module module) throws IllegalAccessException
    {
        Module moduleFound = findById(module.getId());

        return moduleRepository.save(updateValues(moduleFound, module));
    }

    public void deleteById(Long id)
    {
        boolean exists = moduleRepository.existsById(id);

        if(exists)
        {
            moduleRepository.deleteById(id);
        }
        else
        {
            throw new ModuleNotFoundException(id);
        }
    }
}
