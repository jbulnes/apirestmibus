package com.ApiRestMiBus.model.repository;

import com.ApiRestMiBus.model.entity.GeoZonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoZonaRepository extends JpaRepository<GeoZonaEntity, Long> {

}
