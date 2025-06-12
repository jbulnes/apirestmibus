package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.controller.dto.FlotaDTO;
import com.ApiRestMiBus.controller.dto.VehiculoDTO;
import com.ApiRestMiBus.model.entity.VehiculoEntity;
import com.ApiRestMiBus.model.repository.VehiculoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Page<VehiculoDTO> findAll(Pageable pageable){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return vehiculoRepository.findAll(pageable)
                .map(vehiculo -> new VehiculoDTO(
                        vehiculo.getId(),
                        vehiculo.getNumeroPlaca(),
                        vehiculo.getPadron(),
                        vehiculo.getDespacho(),
                        vehiculo.getMarca(),
                        vehiculo.getModelo(),
                        vehiculo.getImei(),
                        vehiculo.getSim(),
                        vehiculo.getVencimientoRevisionTecnica() ,
                        vehiculo.getRuta() != null ? vehiculo.getRuta().getId() : null,
                        vehiculo.getRuta() != null ? vehiculo.getRuta().getNombre() : null,
                        vehiculo.getDiesel(),
                        vehiculo.getNumeroSoat(),
                        vehiculo.getVencimientoSoat(),
                        vehiculo.getNumeroPolizaSeguro(),
                        vehiculo.getVencimientoPoliza(),
                        vehiculo.getExtintor(),
                        vehiculo.getNota(),
                        vehiculo.getEstado() != null ? vehiculo.getEstado().name() : null // Convertir enum a String

                ));
   }

    public Optional<VehiculoEntity> findById(Long id){
        return vehiculoRepository.findById(id);
    }

    public List<VehiculoEntity> findByPlaca(String placa){
        return vehiculoRepository.findByNumeroPlaca(placa);
    }

    public List<VehiculoDTO> findAll(){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        return vehiculoRepository.findAllActivos().stream()
                .map(vehiculo -> new VehiculoDTO(
                        vehiculo.getId(),
                        vehiculo.getNumeroPlaca(),
                        vehiculo.getPadron(),
                        vehiculo.getDespacho(),
                        vehiculo.getMarca(),
                        vehiculo.getModelo(),
                        vehiculo.getImei(),
                        vehiculo.getSim(),
                        vehiculo.getVencimientoRevisionTecnica() ,
                        vehiculo.getRuta() != null ? vehiculo.getRuta().getId() : null,
                        vehiculo.getRuta() != null ? vehiculo.getRuta().getNombre() : null,
                        vehiculo.getDiesel(),
                        vehiculo.getNumeroSoat(),
                        vehiculo.getVencimientoSoat() ,
                        vehiculo.getNumeroPolizaSeguro(),
                        vehiculo.getVencimientoPoliza() ,
                        vehiculo.getExtintor(),
                        vehiculo.getNota(),
                        vehiculo.getEstado() != null ? vehiculo.getEstado().name() : null // Convertir enum a String

                ))
                .collect(Collectors.toList());

    }

    public VehiculoEntity create(VehiculoEntity vehiculo){
        if (vehiculoRepository.existsByNumeroPlaca(vehiculo.getNumeroPlaca())) {
            throw new DataIntegrityViolationException("La placa ya se encuentra registrada");
        }

        return vehiculoRepository.save(vehiculo);
    }

    public VehiculoEntity update(Long id, VehiculoEntity vehiculoUpdate){
        return vehiculoRepository.save(vehiculoUpdate);
    }

    public void deleteById(Long id){
        vehiculoRepository.deleteById(id);
    }

    @Transactional
    public void asignarGpsAVehiculo(Long vehiculoId, Long gpsId) {
        if (!vehiculoRepository.existsById(vehiculoId)) {
            throw new EntityNotFoundException("Vehículo no encontrado con ID: " + vehiculoId);
        }
        vehiculoRepository.asignarGpsAVehiculo(vehiculoId, gpsId);
    }

    @Transactional
    public void eliminarGpsDeVehiculo(Long vehiculoId, Long gpsId) {
        if (!vehiculoRepository.existsById(vehiculoId)) {
            throw new EntityNotFoundException("Vehículo no encontrado con ID: " + vehiculoId);
        }
        vehiculoRepository.eliminarGpsDeVehiculo(vehiculoId, gpsId);
    }

    public List<Map<String, String>> getVehiclesWithGps() {
        List<Object[]> resultados = vehiculoRepository.obtenerVehiculosConGps();

        return resultados.stream()
                .map(obj -> Map.of("placa", (String) obj[0], "imei", (String) obj[1]))
                .collect(Collectors.toList());
    }

    public Page<Map<String, String>> getVehiclesWithGps(Pageable pageable) {
        return vehiculoRepository.obtenerVehiculosConGps(pageable)
                .map(obj -> Map.of(
                        "placa", (String) obj[0],
                        "imei", (String) obj[1]
                ));
    }

    public Optional<Map<String, String>> findVehicleByLicensePlateInGps(String placa) {
        List<Object[]> results = vehiculoRepository.findByPlacaInVehiculoGps(placa);

        if (results.isEmpty()) {
            return Optional.empty();
        }

        Object[] data = results.get(0);

        if (data.length < 2) {
            return Optional.empty();
        }

        String placaResult = data[0] != null ? data[0].toString() : "No existe la placa";
        String imeiResult = data[1] != null ? data[1].toString() : "No existe la imei";

        return Optional.of(Map.of(
                "placa", placaResult,
                "imei", imeiResult
        ));
    }

}
