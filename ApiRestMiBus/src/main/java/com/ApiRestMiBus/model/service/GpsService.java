package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.model.entity.GpsEntity;
import com.ApiRestMiBus.model.repository.GpsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GpsService {
    @Autowired
    private GpsRepository gpsRepository;

    public Page<GpsEntity> findAll(Pageable pageable){
        return gpsRepository.findAll(pageable);
   }

    public Optional<GpsEntity> findById(Long id){
        return gpsRepository.findById(id);
    }

    public List<GpsEntity> findByImei(String imei){
        return gpsRepository.findByImei(imei);
    }

    public List<GpsEntity> findAll(){
        return gpsRepository.findAll();
    }

    public GpsEntity create(GpsEntity gps){
        return gpsRepository.save(gps);
    }

    public GpsEntity update(Long id, GpsEntity gpsUpdate){
        return gpsRepository.save(gpsUpdate);
    }

    public void deleteById(Long id){
        gpsRepository.deleteById(id);
    }

}
