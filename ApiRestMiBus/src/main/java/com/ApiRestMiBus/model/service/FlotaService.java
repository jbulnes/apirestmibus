package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.model.entity.FlotaEntity;
import com.ApiRestMiBus.model.repository.FlotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlotaService {
    @Autowired
    private FlotaRepository flotaRepository;

    public Page<FlotaEntity> findAll(Pageable pageable){
        return flotaRepository.findAll(pageable);
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

    public List<FlotaEntity> findAll(){
        return flotaRepository.findAll();
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

}
