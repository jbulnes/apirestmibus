package com.ApiRestMiBus.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehiculo")
public class VehiculoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String codigo;

    @Column(name="numero_placa", unique = true)
    @NotBlank(message = "El número de placa no puede estar vacío")
    @NotNull(message = "El número de placa es requerido")
    private String numeroPlaca;

    @NotBlank(message = "La marca no puede estar vacío")
    @NotNull(message = "La marca de placa es requerido")
    private String marca;

    @NotBlank(message = "El modelo de placa no puede estar vacío")
    @NotNull(message = "El modelo de placa es requerido")
    private String modelo;

    private String color;

    private String descripcion;

    private String direccion;

    private String estado;

    private String usureg;

    private Date fecreg;

    private String usumod;

    private Date fecmod;

    @ManyToOne
    @JoinColumn(name = "id_flota", nullable = false)
    @NotNull(message = "El idFlota no puede ser nulo.")
    @Positive(message = "El idFlota debe ser positivo.")
    private FlotaEntity flota;

    @ManyToMany (fetch = FetchType.EAGER,cascade = CascadeType.MERGE)
    @JoinTable(name = "vehiculo_gps", joinColumns = @JoinColumn(name = "vehiculo_id"), inverseJoinColumns = @JoinColumn(name = "gps_id"))
    private Set<GpsEntity> vehiculoGps = new HashSet<>();
}
