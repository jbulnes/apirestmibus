package com.ApiRestMiBus.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "vehiculo")
public class VehiculoEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="numero_placa", unique = true)
    @NotBlank(message = "El número de placa no puede estar vacío")
    @NotNull(message = "El número de placa es requerido")
    private String numeroPlaca;

    private String padron;

    private String despacho;

    @NotBlank(message = "La marca no puede estar vacío")
    @NotNull(message = "La marca de placa es requerido")
    private String marca;

    @NotBlank(message = "El modelo de placa no puede estar vacío")
    @NotNull(message = "El modelo de placa es requerido")
    private String modelo;

    private String imei;

    private String sim;

    @Column(name = "vencimiento_revision_tecnica")
    private String vencimientoRevisionTecnica;

    @ManyToOne
    @JoinColumn(name = "id_ruta", nullable = false)
    @NotNull(message = "El idRuta no puede ser nulo.")
    @JsonBackReference
    private RutaEntity ruta;

    private String diesel;

    @Column(name="numero_soat")
    private String numeroSoat;

    @Column(name = "vencimiento_soat")
    private String vencimientoSoat;

    @Column(name="numero_poliza_seguro")
    private String numeroPolizaSeguro;

    @Column(name = "vencimiento_poliza")
    private String  vencimientoPoliza;

    private String extintor;

    private String nota;

}
