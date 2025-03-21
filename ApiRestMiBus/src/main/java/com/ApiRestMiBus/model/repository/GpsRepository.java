package com.ApiRestMiBus.model.repository;

import com.ApiRestMiBus.model.entity.GpsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GpsRepository extends JpaRepository<GpsEntity,Long> {
    List<GpsEntity> findByImei(String imei);
}
