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

    public Page<RoleEntity> getAllRoles(Pageable pageable){
        return roleRepository.findAllRoles(pageable);
    }

    public Optional<RoleEntity> getRoleById(Long id){
        return roleRepository.findById(id);
    }

    public List<RoleEntity> getByRoleName(String name){
        return roleRepository.findByRoleName(name);
    }

    public List<RoleEntity> getRoles(){
        return roleRepository.findAll();
    }

    public RoleEntity save(RoleEntity role){
        return roleRepository.save(role);
    }

    public void delete(Long id){
        roleRepository.deleteById(id);
    }

}
