package co.luisfbejaranob.backend.users.app.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Builder
@Getter
@Setter
public class UserDto implements Serializable
{
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    private String name;

    private String username;

    private String email;

    private String role;

    private Date createAt;

    private Date updateAt;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Date getUpdateAt()
    {
        return updateAt;
    }
}
