package com.ApiRestMiBus.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "Evento_vehiculo")
public class EventoVehiculoEntity {

    @Id
    private String imei;

    private double latitud;
    private double longitud;
    private double speed;
    private Long kilometrajeAcumulado;
    private String fechaEncendido;
    private String horaEncendido;
    private String fechaApagado;
    private String horaApagado;
    private int velocidad;
    private int curso;
    private String estado;
    private int mcc;
    private int mnc;
    private int lac;
    private int cellId;
    private int serialNumber;
    private String checksum;
    private String stopBit;
    private String nivelSenalGSM;
    private String nivelBateria;
    private boolean energiaPrincipalConectada;
    private String geozona;
}