package com.ApiRestMiBus.model.repository;

import com.ApiRestMiBus.model.entity.VehiculoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity,Long> {
    List<VehiculoEntity> findByNumeroPlaca(String placa);
}
