package com.ApiRestMiBus.model.dto;

import com.ApiRestMiBus.model.entity.EmpresaEntity;

public class EmpresaMapper {
    public static EmpresaEntity toEntity(EmpresaDTO dto) {
        if (dto == null) {
            return null;
        }

        return EmpresaEntity.builder()
                .nombre(dto.getNombre())
                .nombreCorto(dto.getNombreCorto())
                .tipoDocumento(dto.getTipoDocumento())
                .documento(dto.getDocumento())
                .logo(dto.getLogo())
                .descripcion(dto.getDescripcion())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .celular1(dto.getCelular1())
                .celular2(dto.getCelular2())
                .nombreRepresentante(dto.getNombreRepresentante())
                .documentoRepresentante(dto.getDocumentoRepresentante())
                .celularRepresentante(dto.getCelularRepresentante())
                .emailRepresentante(dto.getEmailRepresentante())
                .cargoRepresentante(dto.getCargoRepresentante())
                .envioAtu(dto.getEnvioAtu())
                .build();
    }
}
