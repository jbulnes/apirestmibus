package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.controller.dto.GeozonaResponseDTO;
import com.ApiRestMiBus.model.entity.GeoZonaEntity;
import com.ApiRestMiBus.model.repository.GeoZonaRepository;
import jakarta.persistence.EntityManager;
import org.locationtech.jts.geom.*;
import org.locationtech.jts.io.geojson.GeoJsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class GeoZonaService {
    private static final Logger logger = LoggerFactory.getLogger(GeoZonaService.class);

    @Autowired
    private GeoZonaRepository geozonaRepository;

    @Autowired
    private EntityManager entityManager;

    private final GeometryFactory geometryFactory;

    public GeoZonaService(GeoZonaRepository geozonaRepository) {
        this.geozonaRepository = geozonaRepository;
        this.geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    }

    public Page<GeozonaResponseDTO> findAll(Pageable pageable) {
        Pageable orderedPageable = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id")
        );
        return geozonaRepository.findAll(orderedPageable)
                .map(this::toResponseDTO);
    }

    public List<GeoZonaEntity> findAllGeozonas() {
        return geozonaRepository.findAll();
    }


    public GeoZonaEntity guardar(GeoZonaEntity entity) {
        return geozonaRepository.save(entity);
    }

    public List<GeoZonaEntity> listarTodas() {
        return geozonaRepository.findAll();
    }

    @Transactional
    public GeoZonaEntity actualizar(Long id, GeoZonaEntity entity) {
        return geozonaRepository.save(entity);
    }

    public Polygon convertirAPolygon(List<List<Double>> puntos) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        Coordinate[] coordinates = puntos.stream()
                .map(p -> new Coordinate(p.get(0), p.get(1))) // [lon, lat]
                .toArray(Coordinate[]::new);

        // Verificar que el polígono esté cerrado (el primer punto igual al último)
        if (!coordinates[0].equals2D(coordinates[coordinates.length - 1])) {
            coordinates = Arrays.copyOf(coordinates, coordinates.length + 1);
            coordinates[coordinates.length - 1] = coordinates[0]; // cerrar el polígono
        }
        LinearRing shell = geometryFactory.createLinearRing(coordinates);
        return geometryFactory.createPolygon(shell);
    }

    public Optional<GeoZonaEntity> findById(Long id) {
        return geozonaRepository.findById(id);
    }

    private GeozonaResponseDTO toResponseDTO(GeoZonaEntity entity) {
        String geoJsonString = "";
        try {
            geoJsonString = new GeoJsonWriter().write(entity.getArea());
        } catch (Exception e) {

        }
        return new GeozonaResponseDTO(
                entity.getId(),
                entity.getNombre(),
                entity.getEstado().name(),
                entity.getTipo(),
                geoJsonString,
                entity.getRuta().getId(),
                entity.getRuta().getNombre()
        );
    }

    public Optional<GeoZonaEntity> findGeozonaByPoint(double lat, double lon) {
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        List<GeoZonaEntity> geozonas = geozonaRepository.findAll();

        return geozonas.stream()
                .filter(g -> g.getArea().contains(point))
                .findFirst();
    }
}
