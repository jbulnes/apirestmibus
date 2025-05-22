package com.ApiRestMiBus.controller.rest;

import com.ApiRestMiBus.common.adapters.GenericDataAdapter;
import com.ApiRestMiBus.common.domain.GenericResponse;
import com.ApiRestMiBus.controller.dto.FlotaDTO;
import com.ApiRestMiBus.model.entity.AEmpresaEntity;
import com.ApiRestMiBus.model.entity.FlotaEntity;
import com.ApiRestMiBus.model.service.EmpresaService;
import com.ApiRestMiBus.model.service.FlotaService;
import jakarta.validation.Valid;
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
@RequestMapping("/api/flotas")
public class FlotaController {
   /* @Autowired
    private FlotaService flotaService;

    @Autowired
    private EmpresaService empresaService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?>  getAll(@PathVariable Integer page){
        Page<FlotaDTO> pageFlotas;
        Pageable pageable = PageRequest.of(page, 5);
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            pageFlotas = flotaService.findAll(pageable);
            if (pageFlotas.getContent().isEmpty()) {
                String str = " No existen roles en la base de datos";
                genericResponse = genericDataAdapter.createError("1", str);
                return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.NOT_FOUND);
            }
            genericResponse = genericDataAdapter.createData(pageFlotas);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?>  getAll(){
        List<FlotaDTO> lstFlotas;
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            lstFlotas = flotaService.findAll();
            if (lstFlotas.isEmpty()) {
                String str = " No existen flotas en la base de datos";
                genericResponse = genericDataAdapter.createError("1", str);
                return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.NOT_FOUND);
            }
            genericResponse = genericDataAdapter.createData(lstFlotas);
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
            Optional FlotaEntity = flotaService.findById(id);
            genericResponse = genericDataAdapter.createData(FlotaEntity);
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
            List<FlotaEntity> FlotaEntity = flotaService.findByName(name);
            genericResponse = genericDataAdapter.createData(FlotaEntity);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


  /*  @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody FlotaDTO flotaDTO, BindingResult result){
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
            EmpresaEntity empresa = empresaService.findById(flotaDTO.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            FlotaEntity flota =  new FlotaEntity();
            flota.setNombre(flotaDTO.getNombre());
            flota.setDescripcion(flotaDTO.getDescripcion());
            flota.setEmpresa(empresa);
            FlotaEntity nuevaFlota = flotaService.create(flota);
            genericResponse = genericDataAdapter.createData(nuevaFlota);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String str = "Error al realizar el insert en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

   /* @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id,@RequestBody FlotaDTO flotaDTO, BindingResult result){
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
            AEmpresaEntity empresa = empresaService.findById(flotaDTO.getIdEmpresa())
                    .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
            FlotaEntity flota =  new FlotaEntity();
            FlotaEntity flotaActualizada =  new FlotaEntity();
            flota.setNombre(flotaDTO.getNombre());
            flota.setDescripcion(flotaDTO.getDescripcion());
            flota.setEmpresa(empresa);
            flota.setId(id);
            flotaActualizada = flotaService.update(id,flota);
            genericResponse = genericDataAdapter.createData(flotaActualizada);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.CREATED);
        } catch (DataAccessException e) {
            String str = "Error al realizar el update en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/

  /*  @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        flotaService.deleteById(id);
    }

    @GetMapping("/by-name")
    public ResponseEntity<List<FlotaEntity>> getFleetsByName(@RequestParam String name) {
        List<FlotaEntity> fleets = flotaService.getFleetsWithVehiclesByName(name);
        return ResponseEntity.ok(fleets);
    }*/
}
