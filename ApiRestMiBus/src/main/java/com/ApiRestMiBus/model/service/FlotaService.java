package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.controller.dto.FlotaDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FlotaService {
   /* @Autowired
    private FlotaRepository flotaRepository;

    public Page<FlotaDTO> findAll(Pageable pageable){
        return flotaRepository.findAll(pageable)
                .map(flota -> new FlotaDTO(
                        flota.getId(),
                        flota.getCodigo(),
                        flota.getNombre(),
                        flota.getDescripcion(),
                        flota.getColor(),
                        flota.getEstado(),
                        flota.getEmpresa() != null ? flota.getEmpresa().getId() : null,
                        flota.getEmpresa().getNombre()
                ));
   }

    public Optional<FlotaEntity> findById(Long id){
        return flotaRepository.findById(id);
    }

    public Page<FlotaEntity> findByName(String name,Pageable pageable){
        return flotaRepository.findByNombre(name, pageable);
    }

    public List<FlotaEntity> findByName(String name){
        return flotaRepository.findByNombre(name);
    }

    public List<FlotaDTO> findAll(){
        return flotaRepository.findAll().stream()
                .map(flota -> new FlotaDTO(
                        flota.getId(),
                        flota.getCodigo(),
                        flota.getNombre(),
                        flota.getDescripcion(),
                        flota.getColor(),
                        flota.getEstado(),
                        flota.getEmpresa() != null ? flota.getEmpresa().getId() : null,
                        flota.getEmpresa().getNombre()
                ))
                .collect(Collectors.toList());
    }

    public FlotaEntity create(FlotaEntity flota){
        return flotaRepository.save(flota);
    }

    public FlotaEntity update(Long id, FlotaEntity flotaUpdate){
        return flotaRepository.save(flotaUpdate);
    }

    public void deleteById(Long id){
        flotaRepository.deleteById(id);
    }

    public List<FlotaEntity> getFleetsWithVehiclesByName(String nombre) {
        return flotaRepository.findByNombreWithVehiculos(nombre);
    }*/
}
