package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.controller.dto.RutaDTO;
import com.ApiRestMiBus.exception.EntityNotFoundException;

import com.ApiRestMiBus.model.entity.RutaEntity;
import com.ApiRestMiBus.model.repository.RutaRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RutaService {
    @Autowired
    private RutaRepository rutaRepository;

    public Page<RutaDTO> findAll(Pageable pageable){
        return rutaRepository.findAllActivos(pageable)
                .map(ruta -> new RutaDTO(
                        ruta.getId(),
                        ruta.getCodigo(),
                        ruta.getNombre(),
                        ruta.getCodigoRuta(),
                        ruta.getColor(),
                        ruta.getEmpresa() != null ? ruta.getEmpresa().getId() : null,
                        ruta.getEmpresa() != null ? ruta.getEmpresa().getNombre() : null
                ));
    }

    public List<RutaDTO> findAll(){
        return rutaRepository.findAllActivos().stream()
                .map(ruta -> new RutaDTO(
                        ruta.getId(),
                        ruta.getCodigo(),
                        ruta.getNombre(),
                        ruta.getCodigoRuta(),
                        ruta.getColor(),
                        ruta.getEmpresa() != null ? ruta.getEmpresa().getId() : null,
                        ruta.getEmpresa() != null ? ruta.getEmpresa().getNombre() : null
                ))
                .collect(Collectors.toList());
    }

    public Optional<RutaEntity> findById(Long id){
        return rutaRepository.findById(id);
    }

    public RutaEntity create(RutaEntity ruta){
        return rutaRepository.save(ruta);
    }

    public RutaEntity update(Long id, RutaEntity rutaRequest){
        try {
            RutaEntity ruta = rutaRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Ruta no encontrada", id));
            ruta.preUpdate();
            return rutaRepository.save(rutaRequest);
        } catch (EntityNotFoundException e) {
            log.error("Error al actualizar la ruta: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error inesperado al actualizar la ruta con ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Error inesperado al actualizar la ruta", e);
        }
    }

    public void deleteById(Long id){
        RutaEntity ruta = rutaRepository.findById(id).orElseThrow(() -> new RuntimeException("Ruta no encontrada"));
        ruta.desactivar();
        rutaRepository.save(ruta);
    }

    public List<RutaEntity> findByName(String name){
        return rutaRepository.findByNombre(name);
    }

    public List<RutaEntity> getRoutesWithVehiclesByName(String nombre) {
        return rutaRepository.findByNombreWithVehiculos(nombre);
    }

}
