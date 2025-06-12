package com.ApiRestMiBus.controller.rest;

import com.ApiRestMiBus.common.adapters.GenericDataAdapter;
import com.ApiRestMiBus.common.domain.GenericResponse;
import com.ApiRestMiBus.controller.dto.GeozonaResponseDTO;
import com.ApiRestMiBus.model.dto.GeozonaDTO;
import com.ApiRestMiBus.model.entity.EventoVehiculoEntity;
import com.ApiRestMiBus.model.entity.GeoZonaEntity;
import com.ApiRestMiBus.model.entity.RutaEntity;
import com.ApiRestMiBus.model.service.EventoVehiculoService;
import com.ApiRestMiBus.model.service.GeoZonaService;
import com.ApiRestMiBus.model.service.RutaService;
import com.ApiRestMiBus.util.Utils;
import jakarta.validation.Valid;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.geojson.GeoJsonWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/geozona")
public class GeoZonaController {
    private static final Logger logger = LoggerFactory.getLogger(GeoZonaController.class);

    @Autowired
    private GeoZonaService geozonaService;

    @Autowired
    private EventoVehiculoService eventoVehiculoService;

    @Autowired
    private RutaService rutaService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAll(@PathVariable Integer page){
        logger.info("Inicio método getAll");
        Page<GeozonaResponseDTO> pageRutas;
        Pageable pageable = PageRequest.of(page, 10);
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            pageRutas = geozonaService.findAll(pageable);
            if (pageRutas.getContent().isEmpty()) {
                String str = " No existen geozonas en la base de datos";
                genericResponse = genericDataAdapter.createError("1", str);
                return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.NOT_FOUND);
            }
            genericResponse = genericDataAdapter.createData(pageRutas);
            logger.info("Fin método getAll");
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            logger.info("Fin método getAll - INTERNAL_SERVER_ERROR");
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarGeozona(@Valid  @RequestBody GeozonaDTO dto, BindingResult result) {
        logger.info("Inicio método create - Datos recibidos: {}", dto.toString());
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();
        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append(". "));

            String str = errors.toString();
            genericResponse = genericDataAdapter.createError("2", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.BAD_REQUEST);
        }
        try {
            if (dto.getPuntos() == null || dto.getPuntos().size() < 3) {
                genericResponse = genericDataAdapter.createError("5", "Debe enviar al menos 3 puntos para formar un polígono.");
                return new ResponseEntity<>(genericResponse, HttpStatus.CONFLICT);
            }
            RutaEntity ruta = rutaService.findById(dto.getIdRuta())
                    .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
            GeoZonaEntity geozonaEntity = new GeoZonaEntity();
            geozonaEntity.setNombre(dto.getNombre());
            geozonaEntity.setTipo(dto.getTipo());
            geozonaEntity.setRuta(ruta);
            Polygon polygon = Utils.convertirAPolygon(dto.getPuntos());
            geozonaEntity.setArea(polygon);

            GeoZonaEntity nuevaGeozona = geozonaService.guardar(geozonaEntity);
            genericResponse = genericDataAdapter.createData(nuevaGeozona);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.CREATED);
    }catch (
    DataIntegrityViolationException e) {
        genericResponse = genericDataAdapter.createError("3", "La geozona ya se encuentra registrada");
        return new ResponseEntity<>(genericResponse, HttpStatus.CONFLICT);
    }catch (DataAccessException e) {
        String str = "Error al realizar el insert en la base de datos"
                + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
        genericResponse = genericDataAdapter.createError("1", str);
        return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@Valid @PathVariable Long id, @RequestBody GeozonaDTO dto, BindingResult result) {
        logger.info("Inicio método update - Datos recibidos: {}", dto.toString());
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        if (result.hasErrors()) {
            StringBuilder errors = new StringBuilder();
            result.getAllErrors().forEach(error -> errors.append(error.getDefaultMessage()).append(". "));

            String str = errors.toString();
            genericResponse = genericDataAdapter.createError("2", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.BAD_REQUEST);
        }

        try{
            if (dto.getPuntos() == null || dto.getPuntos().size() < 3) {
                genericResponse = genericDataAdapter.createError("5", "Debe enviar al menos 3 puntos para formar un polígono.");
                return new ResponseEntity<>(genericResponse, HttpStatus.CONFLICT);
            }
            RutaEntity ruta = rutaService.findById(dto.getIdRuta())
                    .orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
            GeoZonaEntity geozonaEntity = new GeoZonaEntity();
            geozonaEntity.setNombre(dto.getNombre());
            geozonaEntity.setTipo(dto.getTipo());
            geozonaEntity.setRuta(ruta);
            Polygon polygon = Utils.convertirAPolygon(dto.getPuntos());
            geozonaEntity.setArea(polygon);
            geozonaEntity.setId(id);
            GeoZonaEntity geozonaActualizada = geozonaService.actualizar(id, geozonaEntity);
            genericResponse = genericDataAdapter.createData(geozonaActualizada);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.CREATED);
        }catch (DataAccessException e) {
            String str = "Error al realizar el update en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public List<Map<String, Object>> listarGeozonas() {
        return geozonaService.listarTodas().stream().map(geozona -> {
            Map<String, Object> geoJson = new HashMap<>();
            geoJson.put("id", geozona.getId());
            geoJson.put("tipo", geozona.getTipo());

            geoJson.put("geometry", new GeoJsonWriter().write(geozona.getArea()));

            return geoJson;
        }).toList();
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllGeozonas() {
        List<GeozonaResponseDTO> dtos = geozonaService.findAllGeozonas().stream()
                .map(GeozonaResponseDTO::new)
                .toList();
        return ResponseEntity.ok().body(
                Map.of("success", true, "data", dtos)
        );
    }

    @GetMapping("/check-position")
    public ResponseEntity<?> checkPosition(@RequestParam double lat, @RequestParam double lon,  @RequestParam String imei) {
        System.out.println("Verificando posición recibida:");
        System.out.println("   Latitud: " + lat);
        System.out.println("   Longitud: " + lon);

        try {
            Optional<GeoZonaEntity> geozonaOpt = geozonaService.findGeozonaByPoint(lat, lon);
            if (geozonaOpt.isPresent()) {
                GeoZonaEntity geo = geozonaOpt.get();

                Map<String, Object> response = new HashMap<>();
                response.put("inside", true);
                response.put("zona", geo.getTipo());
                response.put("color", "blue");
                EventoVehiculoEntity eventoVehiculo = eventoVehiculoService.obtenerUltimaUbicacion(imei)
                        .orElseGet(() -> {
                            EventoVehiculoEntity newEvento = new EventoVehiculoEntity();
                            newEvento.setImei(imei);
                            return newEvento;
                        });

                eventoVehiculo.setGeozona(geo.getId().toString());
                eventoVehiculoService.saveOrUpdateEvento(eventoVehiculo);

                return ResponseEntity.ok(response);
            } else {
                Map<String, Object> response = new HashMap<>();
                response.put("inside", false);
                response.put("color", "green");

                return ResponseEntity.ok(response);
            }
        } catch (Exception e) {
            System.err.println("Error interno al verificar la geozona:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error interno: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGeozonaById(@PathVariable Long id) {
        return geozonaService.findById(id)
                .map(entity -> {
                    GeozonaResponseDTO dto = new GeozonaResponseDTO(entity);
                    return ResponseEntity.ok(Map.of("success", true, "data", dto));
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("success", false, "message", "No encontrado")));
    }

}
