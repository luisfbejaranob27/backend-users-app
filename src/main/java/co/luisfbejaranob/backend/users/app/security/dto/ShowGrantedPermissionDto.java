package co.luisfbejaranob.backend.users.app.security.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class ShowGrantedPermissionDto implements Serializable
{
    private Long id;

    private String operation;

    private String method;

    private String module;

    private String role;
}
