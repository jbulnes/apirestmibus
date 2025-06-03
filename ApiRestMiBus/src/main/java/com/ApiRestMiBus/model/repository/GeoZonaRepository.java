package com.ApiRestMiBus.model.repository;

import com.ApiRestMiBus.model.entity.GeozonaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeozonaRepository extends JpaRepository<GeozonaEntity, Long> {

}
