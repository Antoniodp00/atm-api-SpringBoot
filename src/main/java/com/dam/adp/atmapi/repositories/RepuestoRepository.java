package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {

    boolean existsByNombre(String nombre);

    @Query("SELECT DISTINCT r FROM Repuesto r " +
            "JOIN ConsumoRepuesto cr ON cr.repuesto.id = r.id " +
            "JOIN cr.incidencia i " +
            "JOIN i.atm c " +
            "WHERE c.ciudad = :ciudad")
    List<Repuesto> findRepuestosUtilizadosEnCiudad(@Param("ciudad") String ciudad);
}
