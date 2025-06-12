package com.ApiRestMiBus.model.repository;

import com.ApiRestMiBus.model.entity.RutaEntity;
import com.ApiRestMiBus.model.entity.VehiculoEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<VehiculoEntity,Long> {
    List<VehiculoEntity> findByNumeroPlaca(String placa);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO vehiculo_gps (vehiculo_id, gps_id) VALUES (?1, ?2)", nativeQuery = true)
    void asignarGpsAVehiculo(Long vehiculoId, Long gpsId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM vehiculo_gps WHERE vehiculo_id = ?1 AND gps_id = ?2", nativeQuery = true)
    void eliminarGpsDeVehiculo(Long vehiculoId, Long gpsId);

    @Query(value = """
        SELECT v.numero_placa, g.imei 
        FROM vehiculo v
        INNER JOIN vehiculo_gps vg ON v.id = vg.vehiculo_id
        INNER JOIN gps g ON vg.gps_id = g.id
        """, nativeQuery = true)
    List<Object[]> obtenerVehiculosConGps();


    @Query(value = """
        SELECT v.numero_placa, g.imei 
        FROM vehiculo v
        INNER JOIN vehiculo_gps vg ON v.id = vg.vehiculo_id
        INNER JOIN gps g ON vg.gps_id = g.id
        """,
            countQuery = "SELECT COUNT(*) FROM vehiculo_gps",
            nativeQuery = true)
    Page<Object[]> obtenerVehiculosConGps(Pageable pageable);

    @Query(value = """
        SELECT v.numero_placa, g.imei 
            FROM vehiculo v
            INNER JOIN vehiculo_gps vg ON v.id = vg.vehiculo_id
            INNER JOIN gps g ON vg.gps_id = g.id
            WHERE v.numero_placa = :placa
            """, nativeQuery = true)
    List<Object[]> findByPlacaInVehiculoGps(@Param("placa") String placa);
    boolean existsByNumeroPlaca(String placa);

    @Query("SELECT v FROM VehiculoEntity v WHERE v.estado = 'ACTIVO' ORDER BY v.id DESC")
    List<VehiculoEntity> findAllActivos();
}
