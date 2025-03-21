package com.ApiRestMiBus.model.repository;

import com.ApiRestMiBus.model.entity.FlotaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlotaRepository extends JpaRepository<FlotaEntity,Long> {
    List<FlotaEntity> findByNombre(String name);
    Page<FlotaEntity> findByNombre(String name,Pageable pageable);
}
