package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.model.entity.EmpresaEntity;
import com.ApiRestMiBus.model.repository.EmpresaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmpresaService {
    @Autowired
    private EmpresaRepository empresaRepository;

    public Page<EmpresaEntity> findAll(Pageable pageable){
        return empresaRepository.findAll(pageable);
   }

    public Optional<EmpresaEntity> findById(Long id){
        return empresaRepository.findById(id);
    }

    public List<EmpresaEntity> findByName(String name){
        return empresaRepository.findByNombre(name);
    }

    public List<EmpresaEntity> findAll(){
        return empresaRepository.findAll();
    }

    public EmpresaEntity create(EmpresaEntity empresa){
        empresa.prePersist();
        return empresaRepository.save(empresa);
    }

    public EmpresaEntity update(Long id, EmpresaEntity empresaUpdate){
        empresaUpdate.preUpdate();
        return empresaRepository.save(empresaUpdate);
    }

    public void deleteById(Long id){
        empresaRepository.deleteById(id);
    }

}
