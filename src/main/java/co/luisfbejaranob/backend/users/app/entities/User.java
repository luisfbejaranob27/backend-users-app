package co.luisfbejaranob.backend.users.app.entities;

import co.luisfbejaranob.backend.users.app.security.entities.Role;
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

    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    @ManyToOne
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
        if(role.getPermissions() == null) return null;

        List<SimpleGrantedAuthority> authorities = role.getPermissions().stream()
                .map(authority -> authority.getOperation().getName())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_%s".formatted(role.name)));

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
