package com.ApiRestMiBus.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthLoginRequest(
        @NotBlank(message = "El nombre de usuario es obligatorio") String username,
        @NotBlank(message = "La contraseña es obligatoria") String password
) {}
