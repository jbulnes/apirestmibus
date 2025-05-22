package com.ApiRestMiBus.model.entity;

import com.ApiRestMiBus.model.enums.Estado;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
public abstract class BaseEntity {

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private LocalDateTime fechaRegistro = LocalDateTime.now();

    @Column(name = "usuario_registro", nullable = false, updatable = false)
    private String usuarioRegistro;

    @Column(name = "fecha_modificacion")
    private LocalDateTime fechaModificacion;

    @Column(name = "usuario_modificacion")
    private String usuarioModificacion;

    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private Estado estado = Estado.ACTIVO;

    @PrePersist
    public void prePersist() {
        this.usuarioRegistro = SecurityContextHolder.getContext().getAuthentication().getName();
        this.fechaRegistro = LocalDateTime.now();
        this.estado = Estado.ACTIVO;
    }

    @PreUpdate
    public void preUpdate() {
        this.usuarioModificacion = SecurityContextHolder.getContext().getAuthentication().getName();;
        this.fechaModificacion = LocalDateTime.now();
    }

    public void desactivar() {
        this.setEstado(Estado.INACTIVO);
    }
}
