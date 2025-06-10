package com.ApiRestMiBus.controller.dto;

import com.ApiRestMiBus.model.entity.GeoZonaEntity;
import lombok.*;
import org.locationtech.jts.io.geojson.GeoJsonWriter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class GeozonaResponseDTO {
    private Long id;
    private String nombre;
    private String tipo;
    private String geoJson;
    private Long idRuta;
    private String nombreRuta;
    private String estado;

    public GeozonaResponseDTO(GeoZonaEntity entity) {
        this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.idRuta = entity.getRuta().getId();
        this.nombreRuta= entity.getRuta().getNombre();
        this.tipo = entity.getTipo();
        // convertir el área (Geometry) a GeoJSON string
        this.geoJson = new GeoJsonWriter().write(entity.getArea());
    }

    public GeozonaResponseDTO(
            Long id,
            String nombre,
            String estado,
            String tipo,
            String geoJson,
            Long idRuta,
            String nombreRuta
    ) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.tipo = tipo;
        this.geoJson = geoJson;
        this.idRuta = idRuta;
        this.nombreRuta = nombreRuta;
    }
}
