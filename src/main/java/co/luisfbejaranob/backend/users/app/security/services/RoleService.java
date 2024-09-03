package co.luisfbejaranob.backend.users.app.security.services;

import co.luisfbejaranob.backend.users.app.security.entities.Role;
import co.luisfbejaranob.backend.users.app.security.exceptions.RoleErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.repositories.RoleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RoleService
{
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    public Page<Role> findAll(Pageable pageable)
    {
        return roleRepository.findAll(pageable);
    }

    public Role findById(Long id)
    {
        return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
    }

    public Role findByName(String name)
    {
        return roleRepository.findByName(name).orElseThrow(() -> new RoleNotFoundException(name));
    }

    public Role create(Role role)
    {
        return roleRepository.save(role);
    }

    public void deleteById(Long id)
    {
        boolean exists = roleRepository.existsById(id);

        if(exists)
        {
            roleRepository.deleteById(id);
        }
        else
        {
            throw new RoleNotFoundException(id);
        }
    }
}
