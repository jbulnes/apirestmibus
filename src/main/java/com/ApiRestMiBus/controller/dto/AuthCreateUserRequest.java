package com.ApiRestMiBus.controller.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

public record AuthCreateUserRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio")
        @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres")
        @Pattern(
                regexp = "^(?![_.])[a-zA-Z0-9._]+(?<![_.])$",
                message = "El nombre de usuario solo puede contener letras, números, puntos o guiones bajos. No puede comenzar ni terminar con punto o guión bajo."
        )
        String username,

        @NotBlank(message = "La contraseña es obligatoria")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
        @Pattern(
                regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#\\$%\\^&\\*]).+$",
                message = "La contraseña debe contener al menos una letra minúscula, una letra mayúscula, un número y un carácter especial."
        )
        String password,

        @NotNull(message = "La información de los roles no puede ser nula")
        @Valid AuthCreateRoleRequest roleRequest
) {}