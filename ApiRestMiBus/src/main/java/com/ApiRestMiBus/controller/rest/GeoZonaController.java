package com.ApiRestMiBus.controller.rest;

import com.ApiRestMiBus.model.entity.GeoZonaEntity;
import com.ApiRestMiBus.model.service.GeoZonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class GeoZonaController {
    @Autowired
    private GeoZonaService geoZonaService;

    @PostMapping
    public GeoZonaEntity crearGeoZona(@RequestBody GeoZonaEntity geozona) {
        return geoZonaService.crearGeoZona(geozona);
    }

    @GetMapping
    public List<GeoZonaEntity> listar() {
        return geoZonaService.listarGeozonas();
    }

    @GetMapping("/{id}")
    public GeoZonaEntity obtenerPorId(@PathVariable Long id) {
        return geoZonaService.obtenerGeozonaPorId(id);
    }

    @PutMapping("/{id}")
    public GeoZonaEntity actualizar(@PathVariable Long id, @RequestBody GeoZonaEntity geoZona) {
        return geoZonaService.actualizarGeozona(id, geoZona);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        geoZonaService.eliminarGeozona(id);
    }
}
