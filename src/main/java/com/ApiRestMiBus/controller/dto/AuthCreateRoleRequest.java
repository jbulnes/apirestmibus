package com.ApiRestMiBus.controller.dto;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Validated
public record AuthCreateRoleRequest(
        @NotNull(message = "La lista de roles no puede ser nula")
        @NotEmpty(message = "Debe enviar al menos un rol")
        @Size(max = 3, message = "El usuario no puede tener más de 3 roles")
        List<@NotBlank(message = "El nombre del rol no puede estar vacío") String> roleListName
) {}