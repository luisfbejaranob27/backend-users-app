package co.luisfbejaranob.backend.users.app.security.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "operations")
public class Operation
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String path;

    @NotBlank
    private String method;

    private Boolean permitAll;

    @ManyToOne
    private Module module;
}
