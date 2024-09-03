package co.luisfbejaranob.backend.users.app.security.services;

import co.luisfbejaranob.backend.users.app.security.dto.GrantedPermissionDto;
import co.luisfbejaranob.backend.users.app.security.dto.ShowGrantedPermissionDto;
import co.luisfbejaranob.backend.users.app.security.entities.GrantedPermission;
import co.luisfbejaranob.backend.users.app.security.exceptions.GrantedPermissionErrorsExceptions.*;
import co.luisfbejaranob.backend.users.app.security.repositories.GrantedPermissionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class GrantedPermissionService
{
    private final GrantedPermissionRepository grantedPermissionRepository;

    private final RoleService roleService;

    private final OperationService operationService;

    public GrantedPermissionService(
            GrantedPermissionRepository grantedPermissionRepository,
            RoleService roleService,
            OperationService operationService
    )
    {
        this.grantedPermissionRepository = grantedPermissionRepository;
        this.roleService = roleService;
        this.operationService = operationService;
    }

    public Page<ShowGrantedPermissionDto> findAll(Pageable pageable)
    {
        return grantedPermissionRepository.findAll(pageable).map(this::toDto);
    }

    public ShowGrantedPermissionDto findById(Long id)
    {
        GrantedPermission grantedPermission = grantedPermissionRepository.findById(id)
                .orElseThrow(() -> new GrantedPermissionNotFoundException(id));

        return toDto(grantedPermission);
    }

    public ShowGrantedPermissionDto create(GrantedPermissionDto grantedPermissionDto)
    {
        GrantedPermission grantedPermission = new GrantedPermission();
        grantedPermission.setRole(roleService.findByName(grantedPermissionDto.getRoleName()));
        grantedPermission.setOperation(operationService.findByName(grantedPermissionDto.getOperationName()));

        return toDto(grantedPermissionRepository.save(grantedPermission));
    }

    public void deleteById(Long id)
    {
        boolean exists = grantedPermissionRepository.existsById(id);
        if(exists)
        {
            grantedPermissionRepository.deleteById(id);
        }
        else
        {
            throw new GrantedPermissionNotFoundException(id);
        }
    }

    private ShowGrantedPermissionDto toDto(GrantedPermission grantedPermission)
    {
        return ShowGrantedPermissionDto
                .builder()
                .id(grantedPermission.getId())
                .operation(grantedPermission.getOperation().getName())
                .method(grantedPermission.getOperation().getMethod())
                .module(grantedPermission.getOperation().getModule().getName())
                .role(grantedPermission.getRole().getName())
                .build();
    }
}
