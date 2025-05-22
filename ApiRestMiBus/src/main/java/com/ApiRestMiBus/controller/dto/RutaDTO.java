package com.ApiRestMiBus.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RutaDTO {
    private Long id;

    @NotBlank(message = "El código no puede estar vacío")
    private String codigo;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @NotBlank(message = "El código de ruta no puede estar vacío")
    private String codigoRuta;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    @NotNull(message = "Debe seleccionar una empresa")
    private Long idEmpresa;

    private String nombreEmpresa;
}
