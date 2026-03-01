package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.enums.EstadoIncidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CajeroRepository  extends JpaRepository<Cajero, String> {

    List<Cajero> findByActivoTrue();

    List<Cajero> findByActivoFalse();

    //Busca cajeros con incidencias por encima de una prioridad
    @Query("SELECT DISTINCT c FROM Cajero c JOIN c.incidencias i WHERE i.prioridad >= :prioridad AND i.estado != :estadoResuelta")
    List<Cajero> findCajerosConIncidenciasGraves(@Param("prioridad") Integer prioridad, @Param("estadoResuelta") EstadoIncidencia estadoResuelta);

}
