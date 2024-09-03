package co.luisfbejaranob.backend.users.app.security.repositories;

import co.luisfbejaranob.backend.users.app.security.entities.GrantedPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GrantedPermissionRepository extends JpaRepository<GrantedPermission, Long>
{}
