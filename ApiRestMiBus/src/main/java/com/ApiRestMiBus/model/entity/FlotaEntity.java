package com.ApiRestMiBus.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "flota")
public class FlotaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Column(name="nombre", unique = true)
    @NotBlank(message = "El nombre no puede estar vacío")
    @NotNull(message = "El nombre es requerido")
    private String nombre;

    private String descripcion;

    private String color;

    private String estado;

    private String usureg;

    private Date fecreg;

    private String usumod;

    private Date fecmod;

    @ManyToOne
    @JoinColumn(name = "id_empresa", nullable = false)
    @NotNull(message = "El idEmpresa no puede ser nulo.")
    @Positive(message = "El idEmpresa debe ser positivo.")
    private EmpresaEntity empresa;

    @OneToMany(mappedBy = "flota", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehiculoEntity> vehiculos;
    
}
