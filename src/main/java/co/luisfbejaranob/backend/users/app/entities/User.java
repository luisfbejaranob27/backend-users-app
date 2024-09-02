package co.luisfbejaranob.backend.users.app.entities;

import co.luisfbejaranob.backend.users.app.entities.enumerations.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode

@Entity
@Table(name = "users")
public class User implements UserDetails
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

//    @NotBlank
    @Enumerated(EnumType.STRING)
    private Role role;

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        if(role == null) return null;
        if(role.getOperations() == null) return null;

        List<SimpleGrantedAuthority> authorities = role.getOperations().stream()
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_%s".formatted(this.role.name())));

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
