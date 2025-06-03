package com.ApiRestMiBus.model.repository;

import com.ApiRestMiBus.model.entity.EventoVehiculoEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoVehiculoRepository extends CrudRepository<EventoVehiculoEntity, String> {
    Optional<EventoVehiculoEntity> findByImei(String imei);
}
