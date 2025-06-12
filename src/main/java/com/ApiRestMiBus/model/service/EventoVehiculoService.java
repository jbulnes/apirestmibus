package com.ApiRestMiBus.model.service;

import com.ApiRestMiBus.controller.rest.VehiculoController;
import com.ApiRestMiBus.model.entity.EventoVehiculoEntity;
import com.ApiRestMiBus.model.repository.EventoVehiculoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventoVehiculoService {
    private static final Logger log = LoggerFactory.getLogger(EventoVehiculoService.class);
    @Autowired
    EventoVehiculoRepository eventoVehiculoRepository;

    public void saveOrUpdateEvento(EventoVehiculoEntity nuevoEvento) {
        eventoVehiculoRepository.save(nuevoEvento);
    }

    public Optional<EventoVehiculoEntity> obtenerUltimaUbicacion(String imei) {
        try {
            log.info("Buscando última ubicación para IMEI: {}", imei);
            return eventoVehiculoRepository.findByImei(imei);
        } catch (Exception e) {
            log.error("Error al obtener la última ubicación para IMEI: {}", imei, e);
            return Optional.empty();
        }
    }
}
