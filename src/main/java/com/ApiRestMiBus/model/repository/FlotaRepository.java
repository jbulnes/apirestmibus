package com.ApiRestMiBus.model.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
/*
@Repository
public interface FlotaRepository extends JpaRepository<> {
    List<FlotaEntity> findByNombre(String name);
    Page<FlotaEntity> findByNombre(String name,Pageable pageable);
    @Query("SELECT f FROM FlotaEntity f LEFT JOIN FETCH f.vehiculos WHERE f.nombre = :name")
    List<FlotaEntity> findByNombreWithVehiculos(@Param("name") String name);
}*/
