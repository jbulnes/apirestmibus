package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.model.entity.RoleEntity;
import com.ApiRestMiBus.model.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Page<RoleEntity> findAll(Pageable pageable){
        return roleRepository.findAll(pageable);
    }

    public Optional<RoleEntity> findById(Long id){
        return roleRepository.findById(id);
    }

    public List<RoleEntity> findByRoleName(String name){
        return roleRepository.findByRoleName(name);
    }

    public List<RoleEntity> findAll(){
        return roleRepository.findAll();
    }

    public RoleEntity create(RoleEntity role){
        return roleRepository.save(role);
    }

    public RoleEntity update(Long id, RoleEntity roleUpdate){
        return roleRepository.save(roleUpdate);
    }

    public void deleteById(Long id){
        roleRepository.deleteById(id);
    }

}
