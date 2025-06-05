package com.ApiRestMiBus.controller.rest;

import com.ApiRestMiBus.common.adapters.GenericDataAdapter;
import com.ApiRestMiBus.common.domain.GenericResponse;
import com.ApiRestMiBus.controller.dto.RutaDTO;
import com.ApiRestMiBus.model.entity.EmpresaEntity;
import com.ApiRestMiBus.model.entity.RutaEntity;
import com.ApiRestMiBus.model.service.EmpresaService;
import com.ApiRestMiBus.model.service.RutaService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/rutas")
public class RutaController {
    private static final Logger logger = LoggerFactory.getLogger(RutaController.class);

    @Autowired
    private RutaService rutaService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?> getAll(@PathVariable Integer page){
        logger.info("Inicio método getAll");
        Page<RutaDTO> pageRutas;
        Pageable pageable = PageRequest.of(page, 5);
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            pageRutas = rutaService.findAll(pageable);
            if (pageRutas.getContent().isEmpty()) {
                String str = " No existen rutas en la base de datos";
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

    @GetMapping
    public ResponseEntity<?>  getAll(){
        List<RutaDTO> lstRutas;
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            lstRutas = rutaService.findAll();
            if (lstRutas.isEmpty()) {
                String str = " No existen rutas en la base de datos";
                genericResponse = genericDataAdapter.createError("1", str);
                return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.NOT_FOUND);
            }
            genericResponse = genericDataAdapter.createData(lstRutas);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            Optional rutaEntity = rutaService.findById(id);
            genericResponse = genericDataAdapter.createData(rutaEntity);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/search")
    public ResponseEntity<?> getByName(@RequestParam String name){
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            List<RutaEntity> FlotaEntity = rutaService.findByName(name);
            genericResponse = genericDataAdapter.createData(FlotaEntity);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


  @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody RutaDTO rutaDTO, BindingResult result){
      logger.info("Inicio método create - Datos recibidos: {}", rutaDTO.toString());

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
            EmpresaEntity empresaEntity = empresaService.findById(rutaDTO.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            RutaEntity ruta =  new RutaEntity();
            ruta.setCodigo(rutaDTO.getCodigo());
            ruta.setNombre(rutaDTO.getNombre());
            ruta.setCodigoRuta(rutaDTO.getCodigoRuta());
            ruta.setColor(rutaDTO.getColor());
            ruta.setEmpresa(empresaEntity);
            RutaEntity nuevaRuta = rutaService.create(ruta);
            genericResponse = genericDataAdapter.createData(nuevaRuta);
            logger.info("Ruta creada correctamente con ID: {}", nuevaRuta.getId());
            logger.info("Fin método create - CREATED");
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String str = "Error al realizar el insert en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            logger.error("Excepción en método create: {}", str);
            genericResponse = genericDataAdapter.createError("1", str);
            logger.info("Fin método create - INTERNAL_SERVER_ERROR");
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id,@RequestBody RutaDTO rutaDTO, BindingResult result){
       logger.info("Inicio método update - Datos recibidos: {}", rutaDTO.toString());
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
            EmpresaEntity empresaEntity = empresaService.findById(rutaDTO.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            RutaEntity ruta =  new RutaEntity();
            RutaEntity rutaActualizada =  new RutaEntity();

            ruta.setCodigo(rutaDTO.getCodigo());
            ruta.setNombre(rutaDTO.getNombre());
            ruta.setCodigoRuta(rutaDTO.getCodigoRuta());
            ruta.setColor(rutaDTO.getColor());
            ruta.setEmpresa(empresaEntity);
            ruta.setId(id);
            rutaActualizada = rutaService.update(id,ruta);
            genericResponse = genericDataAdapter.createData(rutaActualizada);
            logger.info("Fin método update - Datos recibidos: {}", rutaDTO.toString());
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String str = "Error al realizar el update en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
      logger.info("Inicio método delete - id: {}", id);
        rutaService.deleteById(id);
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<RutaEntity>> getRoutesByName(@RequestParam String name) {
        List<RutaEntity> fleets = rutaService.getRoutesWithVehiclesByName(name);
        return ResponseEntity.ok(fleets);
    }
}
