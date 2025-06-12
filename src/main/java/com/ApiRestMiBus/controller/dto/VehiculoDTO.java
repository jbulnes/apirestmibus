package com.ApiRestMiBus.controller.dto;

import com.ApiRestMiBus.model.entity.RutaEntity;
import com.ApiRestMiBus.model.enums.Estado;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
public class VehiculoDTO {
    private Long id;
    private String numeroPlaca;
    private String padron;
    private String despacho;
    private String marca;
    private String modelo;
    private String imei;
    private String sim;
    private String vencimientoRevisionTecnica;
    private Long idRuta;
    private String nombreRuta;
    private String diesel;
    private String numeroSoat;
    private String vencimientoSoat;
    private String numeroPolizaSeguro;
    private String vencimientoPoliza;
    private String extintor;
    private String nota;
    private String estado;

    public VehiculoDTO(Long id, String numeroPlaca, String padron, String despacho, String marca, String modelo, String imei, String sim,
                       String vencimientoRevisionTecnica, Long idRuta, String nombreRuta, String diesel, String numeroSoat,
                       String vencimientoSoat, String numeroPolizaSeguro, String vencimientoPoliza, String extintor,
                       String nota, String estado) {
        this.id = id;
        this.numeroPlaca = numeroPlaca;
        this.padron = padron;
        this.despacho = despacho;
        this.marca = marca;
        this.modelo = modelo;
        this.imei = imei;
        this.sim = sim;
        this.vencimientoRevisionTecnica = vencimientoRevisionTecnica;
        this.idRuta = idRuta;
        this.nombreRuta = nombreRuta;
        this.diesel = diesel;
        this.numeroSoat = numeroSoat;
        this.vencimientoSoat = vencimientoSoat;
        this.numeroPolizaSeguro = numeroPolizaSeguro;
        this.vencimientoPoliza = vencimientoPoliza;
        this.extintor = extintor;
        this.nota = nota;
        this.estado = estado;
    }
}
