package com.ApiRestMiBus.model.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="role_name", unique = true)
    @NotBlank(message = "El nombre del rol no puede estar vacío")
    @NotNull(message = "El nombre del rol es requerido")
    private String roleName;

    @ManyToMany (fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name = "role_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    @NotNull(message = "Los permisos no pueden ser nulas")  // Valida que la colección no sea nula
    @NotEmpty(message = "Los permisos no pueden estar vacías")
    private Set<PermissionEntity> permissions = new HashSet<>();
}
