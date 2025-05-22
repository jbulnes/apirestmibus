package com.ApiRestMiBus.model.repository;

import com.ApiRestMiBus.controller.dto.RutaDTO;
import com.ApiRestMiBus.model.entity.RutaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutaRepository extends JpaRepository<RutaEntity,Long> {
    List<RutaEntity> findByNombre(String name);
    Page<RutaEntity> findByNombre(String name,Pageable pageable);
    @Query("SELECT f FROM RutaEntity f LEFT JOIN FETCH f.vehiculos WHERE f.nombre = :name")
    List<RutaEntity> findByNombreWithVehiculos(@Param("name") String name);
    @Query("SELECT r FROM RutaEntity r WHERE r.estado = 'ACTIVO' ORDER BY r.id DESC")
    Page<RutaEntity> findAllActivos(Pageable pageable);
    @Query("SELECT r FROM RutaEntity r WHERE r.estado = 'ACTIVO' ORDER BY r.id DESC")
    List<RutaEntity> findAllActivos();
}
