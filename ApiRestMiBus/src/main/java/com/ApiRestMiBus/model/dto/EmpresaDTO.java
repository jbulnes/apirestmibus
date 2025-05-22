package com.ApiRestMiBus.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmpresaDTO {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    private String nombreCorto;
    private String tipoDocumento;
    private String documento;
    private String logo;
    private String descripcion;
    private String email;
    private String telefono;
    private String celular1;
    private String celular2;
    private String nombreRepresentante;
    private String documentoRepresentante;
    private String celularRepresentante;
    private String emailRepresentante;
    private String cargoRepresentante;
    private String envioAtu;
}
