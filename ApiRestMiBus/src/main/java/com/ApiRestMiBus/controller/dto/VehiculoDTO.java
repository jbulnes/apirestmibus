package com.ApiRestMiBus.controller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiculoDTO {
    private Long id;
    private String codigo;
    private String numeroPlaca;
    private String marca;
    private String modelo;
    private String descripcion;
    private String color;
    private String estado;
    private Long idFlota;
    private String nombreFlota;
}
