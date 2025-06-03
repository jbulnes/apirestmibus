package com.ApiRestMiBus.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
public class GeozonaDTO {
    private String nombre;
    public String tipo;
    public List<List<Double>> puntos;
    private Long idRuta;
    private String nombreRuta;
    private String estado;

    public GeozonaDTO(String nombre, String tipo, List<List<Double>> puntos, Long idRuta, String nombreRuta, String estado) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.puntos = puntos;
        this.idRuta = idRuta;
        this.nombreRuta = nombreRuta;
        this.estado = estado;
    }
}
