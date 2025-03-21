package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.model.entity.VehiculoEntity;
import com.ApiRestMiBus.model.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    public Page<VehiculoEntity> findAll(Pageable pageable){
        return vehiculoRepository.findAll(pageable);
   }

    public Optional<VehiculoEntity> findById(Long id){
        return vehiculoRepository.findById(id);
    }

    public List<VehiculoEntity> findByPlaca(String placa){
        return vehiculoRepository.findByNumeroPlaca(placa);
    }

    public List<VehiculoEntity> findAll(){
        return vehiculoRepository.findAll();
    }

    public VehiculoEntity create(VehiculoEntity vehiculo){
        return vehiculoRepository.save(vehiculo);
    }

    public VehiculoEntity update(Long id, VehiculoEntity vehiculoUpdate){
        return vehiculoRepository.save(vehiculoUpdate);
    }

    public void deleteById(Long id){
        vehiculoRepository.deleteById(id);
    }

}
