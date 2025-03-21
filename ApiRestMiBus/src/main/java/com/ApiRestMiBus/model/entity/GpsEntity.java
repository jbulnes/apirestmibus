package com.ApiRestMiBus.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "gps")
public class GpsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="imei", unique = true)
    @NotBlank(message = "El imei no puede estar vacío")
    @NotNull(message = "El imei es requerido")
    private String imei;

    @Column(name="numero_sim", unique = true)
    @NotBlank(message = "El numero SIM no puede estar vacío")
    @NotNull(message = "El numero SIM es requerido")
    private String numeroSim;

    private String estado;

    private String usureg;

    private Date fecreg;

    private String usumod;

    private Date fecmod;
}
