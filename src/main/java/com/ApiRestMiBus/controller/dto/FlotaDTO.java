package com.ApiRestMiBus.controller.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FlotaDTO {
    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private String color;
    private String estado;
    private Long idEmpresa;
    private String nombreEmpresa;
}
