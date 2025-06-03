package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.controller.dto.CoordenadaDTO;
import com.ApiRestMiBus.controller.dto.GeozonaResponseDTO;
import com.ApiRestMiBus.controller.dto.RutaDTO;
import com.ApiRestMiBus.controller.rest.RutaController;
import com.ApiRestMiBus.model.dto.GeozonaDTO;
import com.ApiRestMiBus.model.entity.GeozonaEntity;
import com.ApiRestMiBus.model.repository.GeozonaRepository;
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
import java.util.stream.Collectors;

@Service
public class GeozonaService {
    private static final Logger logger = LoggerFactory.getLogger(GeozonaService.class);

    @Autowired
    private GeozonaRepository geozonaRepository;

    @Autowired
    private EntityManager entityManager;

    private final GeometryFactory geometryFactory;

    public GeozonaService(GeozonaRepository geozonaRepository) {
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

    public List<GeozonaEntity> findAllGeozonas() {
        return geozonaRepository.findAll();
    }


    public GeozonaEntity guardar(GeozonaEntity entity) {
        return geozonaRepository.save(entity);
    }

    public List<GeozonaEntity> listarTodas() {
        return geozonaRepository.findAll();
    }

    @Transactional
    public GeozonaEntity actualizar(Long id, GeozonaEntity entity) {
        /*GeozonaEntity geo = geozonaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe geozona con id: " + id));

        Polygon polygon = convertirAPolygon(dto.getPuntos());
        geo.setTipo(dto.getTipo());
        geo.setArea(polygon);*/

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

    public Optional<GeozonaEntity> findById(Long id) {
        return geozonaRepository.findById(id);
    }

    private GeozonaResponseDTO toResponseDTO(GeozonaEntity entity) {
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

    public Optional<GeozonaEntity> findGeozonaByPoint(double lat, double lon) {
        Point point = geometryFactory.createPoint(new Coordinate(lon, lat));
        List<GeozonaEntity> geozonas = geozonaRepository.findAll();

        return geozonas.stream()
                .filter(g -> g.getArea().contains(point))
                .findFirst();
    }
}
