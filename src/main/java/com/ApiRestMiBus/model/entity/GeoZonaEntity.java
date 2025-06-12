package com.ApiRestMiBus.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.locationtech.jts.geom.Polygon;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(name = "geozona")
public class GeoZonaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String tipo;

    @JsonIgnore
    @Column(columnDefinition = "geometry(Polygon,4326)")
    private Polygon area;

    @ManyToOne
    @JoinColumn(name = "id_ruta", nullable = false)
    @NotNull(message = "El idRuta no puede ser nulo.")
    @JsonBackReference
    private RutaEntity ruta;
}
