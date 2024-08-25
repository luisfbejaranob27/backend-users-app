package co.luisfbejaranob.backend.users.app.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode

@Entity
@Table(name = "users")
public class User
{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;

    @Column(unique = true)
    @NotBlank
    @Size(min = 5, max = 50)
    private String username;

    @NotBlank
    @Size(min = 8, message = "Must be at least 8 characters long")
    @Pattern(regexp = ".*[A-Z].*", message = "Must contain at least one uppercase letter")
    @Pattern(regexp = ".*[a-z].*", message = "Must contain at least one lowercase letter")
    @Pattern(regexp = ".*\\d.*", message = "Must contain at least one digit")
    @Pattern(regexp = ".*[@$!%*?&].*", message = "Must contain at least one special character (@$!%*?&)")
    private String password;

    @Column(unique = true)
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", message = "The email must have a valid format")
    private String email;

    @NotBlank
    private String role;

    @Column(name = "create_at")
    private Date createAt;

    @Column(name = "update_at")
    private Date updateAt;

    @PrePersist
    public void prePersist()
    {
        this.createAt = new Date();
    }

    @PreUpdate
    public void preUpdate()
    {
        this.updateAt = new Date();
    }

    @JsonInclude(Include.NON_NULL)
    public Date getUpdateAt()
    {
        return updateAt;
    }
}
