package com.ApiRestMiBus.controller.rest;

import com.ApiRestMiBus.common.adapters.GenericDataAdapter;
import com.ApiRestMiBus.common.domain.GenericResponse;
import com.ApiRestMiBus.controller.dto.VehiculoDTO;
import com.ApiRestMiBus.model.entity.EmpresaEntity;
import com.ApiRestMiBus.model.entity.FlotaEntity;
import com.ApiRestMiBus.model.entity.VehiculoEntity;
import com.ApiRestMiBus.model.service.FlotaService;
import com.ApiRestMiBus.model.service.VehiculoService;
import jakarta.validation.Valid;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehiculos")
public class VehiculoController {
    @Autowired
    private VehiculoService vehiculoService;

    @Autowired
    private FlotaService flotaService;

    @GetMapping("/page/{page}")
    public ResponseEntity<?>  getAll(@PathVariable Integer page){
        Page<VehiculoDTO> pageVehiculos;
        Pageable pageable = PageRequest.of(page, 5);
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            pageVehiculos = vehiculoService.findAll(pageable);
            if (pageVehiculos.getContent().isEmpty()) {
                String str = " No existen vehiculos en la base de datos";
                genericResponse = genericDataAdapter.createError("1", str);
                return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.NOT_FOUND);
            }
            genericResponse = genericDataAdapter.createData(pageVehiculos);
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
        List<VehiculoDTO> lstVehiculos;
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            lstVehiculos = vehiculoService.findAll();
            if (lstVehiculos.isEmpty()) {
                String str = " No existen flotas en la base de datos";
                genericResponse = genericDataAdapter.createError("1", str);
                return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.NOT_FOUND);
            }
            genericResponse = genericDataAdapter.createData(lstVehiculos);
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
            Optional VehiculoEntity = vehiculoService.findById(id);
            genericResponse = genericDataAdapter.createData(VehiculoEntity);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/search")
    public ResponseEntity<?> getByPlaca(@RequestParam String placa){
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            List<VehiculoEntity> vehiculoEntity = vehiculoService.findByPlaca(placa);
            genericResponse = genericDataAdapter.createData(vehiculoEntity);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.OK);
        } catch (DataAccessException e) {
            String str = "Error al realizar la consulta en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody VehiculoDTO vehiculoDTO, BindingResult result){
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
            FlotaEntity flota = flotaService.findById(vehiculoDTO.getIdFlota())
                    .orElseThrow(() -> new RuntimeException("Flota no encontrada"));
            VehiculoEntity vehiculo =  new VehiculoEntity();
            vehiculo.setNumeroPlaca(vehiculoDTO.getNumeroPlaca());
            vehiculo.setDescripcion(vehiculoDTO.getDescripcion());
            vehiculo.setMarca(vehiculoDTO.getMarca());
            vehiculo.setModelo(vehiculoDTO.getModelo());
            vehiculo.setFlota(flota);
            VehiculoEntity nuevoVehiculo = vehiculoService.create(vehiculo);
            genericResponse = genericDataAdapter.createData(nuevoVehiculo);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.CREATED);
        }catch (DataIntegrityViolationException e) {
            genericResponse = genericDataAdapter.createError("3", "La placa ya se encuentra registrada");
            return new ResponseEntity<>(genericResponse, HttpStatus.CONFLICT);
        }catch (DataAccessException e) {
            String str = "Error al realizar el insert en la base de datos"
                    + e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage());
            genericResponse = genericDataAdapter.createError("1", str);
            return new ResponseEntity<GenericResponse>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable Long id,@RequestBody VehiculoDTO vehiculoDTO, BindingResult result){
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
            FlotaEntity flota = flotaService.findById(vehiculoDTO.getIdFlota())
                    .orElseThrow(() -> new RuntimeException("Flota no encontrada"));
            VehiculoEntity vehiculo =  new VehiculoEntity();
            VehiculoEntity vehiculoActualizado =  new VehiculoEntity();
            vehiculo.setNumeroPlaca(vehiculoDTO.getNumeroPlaca());
            vehiculo.setDescripcion(vehiculoDTO.getDescripcion());
            vehiculo.setMarca(vehiculoDTO.getMarca());
            vehiculo.setModelo(vehiculoDTO.getModelo());
            vehiculo.setFlota(flota);
            vehiculo.setId(id);
            vehiculoActualizado =vehiculoService.update(id,vehiculo);
            genericResponse = genericDataAdapter.createData(vehiculoActualizado);
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
        vehiculoService.deleteById(id);
    }

    @PostMapping("/{vehiculoId}/gps/{gpsId}")
    public ResponseEntity<String> asignarGps(@PathVariable Long vehiculoId, @PathVariable Long gpsId) {
        vehiculoService.asignarGpsAVehiculo(vehiculoId, gpsId);
        return ResponseEntity.ok("GPS asignado correctamente al vehículo.");
    }

    @DeleteMapping("/{vehiculoId}/gps/{gpsId}")
    public ResponseEntity<String> eliminarGps(@PathVariable Long vehiculoId, @PathVariable Long gpsId) {
        vehiculoService.eliminarGpsDeVehiculo(vehiculoId, gpsId);
        return ResponseEntity.ok("GPS eliminado correctamente del vehículo.");
    }

    @GetMapping("/gps/page/{page}")
    public ResponseEntity<GenericResponse> getVehiculosConGps(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();

        try {
            Page<Map<String, String>> pageVehiculos = vehiculoService.getVehiclesWithGps(pageable);

            if (pageVehiculos.isEmpty()) {
                String mensaje = "No existen vehículos con GPS en la base de datos";
                genericResponse = genericDataAdapter.createError("1", mensaje);
                return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
            }

            genericResponse = genericDataAdapter.createData(pageVehiculos);
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);

        } catch (DataAccessException e) {
            String errorMensaje = "Error al realizar la consulta en la base de datos: "
                    + e.getMostSpecificCause().getMessage();
            genericResponse = genericDataAdapter.createError("1", errorMensaje);
            return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/gps")
    public List<Map<String, String>> getVehiculosConGps() {
        return vehiculoService.getVehiclesWithGps();
    }

    @GetMapping("/gps/placa/{placa}")
    public ResponseEntity<GenericResponse> getVehicleByLicensePlate(@PathVariable String placa) {
        GenericResponse genericResponse = new GenericResponse();
        GenericDataAdapter genericDataAdapter = new GenericDataAdapter();
        System.out.println("Buscando vehículo con placa: " + placa);
        Optional<Map<String, String>> vehiculo = vehiculoService.findVehicleByLicensePlateInGps(placa);

        if (vehiculo.isPresent()) {
            System.out.println("Vehículo encontrado: " + vehiculo.get());
            genericResponse = genericDataAdapter.createData(vehiculo.get());
            return new ResponseEntity<>(genericResponse, HttpStatus.OK);
        } else {
            System.out.println("Vehículo NO encontrado.");
            genericResponse = genericDataAdapter.createError("1", "Vehículo no encontrado con placa: " + placa);
            return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
        }
    }
}
