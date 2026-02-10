package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {

    //Obtener todas las incidencias de una ciudad específica.
    @Query("SELECT i FROM Incidencia i JOIN i.atm c WHERE c.ciudad = :ciudad")
    List<Incidencia>findByCiudad(@Param("ciudad") String ciudad);

    //Incidencia de un tecnico
    @Query("SELECT i FROM Incidencia i JOIN i.tecnico t WHERE t.username = :username")
    List<Incidencia>findByTecnico(@Param("username") String username);


}
