package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.model.entity.GeoZonaEntity;
import com.ApiRestMiBus.model.repository.GeoZonaRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GeoZonaService {
    @Autowired
    private GeoZonaRepository geoZonaRepository;

    public GeoZonaEntity crearGeoZona(GeoZonaEntity geozona) {
        return geoZonaRepository.save(geozona);
    }

    public List<GeoZonaEntity> listarGeozonas() {
        return geoZonaRepository.findAll();
    }

    public GeoZonaEntity obtenerGeozonaPorId(Long id) {
        return geoZonaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Geozona no encontrada con id: " + id));
    }

    public GeoZonaEntity actualizarGeozona(Long id, GeoZonaEntity nuevaGeoZona) {
        GeoZonaEntity existente = obtenerGeozonaPorId(id);
        existente.setNombre(nuevaGeoZona.getNombre());
        existente.setDescripcion(nuevaGeoZona.getDescripcion());
        existente.setTipoZona(nuevaGeoZona.getTipoZona());
        existente.setColor(nuevaGeoZona.getColor());
        existente.setCoordenadas(nuevaGeoZona.getCoordenadas());

        return geoZonaRepository.save(existente);
    }

    public void eliminarGeozona(Long id) {
        geoZonaRepository.deleteById(id);
    }
}
