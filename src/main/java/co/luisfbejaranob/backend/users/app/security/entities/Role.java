package co.luisfbejaranob.backend.users.app.security.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "roles")
public class Role
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    public String name;

    @JsonIgnore
    @OneToMany(mappedBy = "role" , fetch = FetchType.EAGER)
    List<GrantedPermission> permissions;
}
