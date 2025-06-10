package com.ApiRestMiBus.model.repository;


import com.ApiRestMiBus.model.entity.EmpresaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpresaRepository extends JpaRepository<EmpresaEntity,Long> {
    List<EmpresaEntity> findByNombre(String name);
    Page<EmpresaEntity> findByNombre(String name, Pageable pageable);
}
