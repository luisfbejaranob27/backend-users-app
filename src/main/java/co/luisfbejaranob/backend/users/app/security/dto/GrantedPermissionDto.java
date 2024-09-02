package co.luisfbejaranob.backend.users.app.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
public class GrantedPermissionDto implements Serializable
{
    private String roleName;

    private String operationName;


}
