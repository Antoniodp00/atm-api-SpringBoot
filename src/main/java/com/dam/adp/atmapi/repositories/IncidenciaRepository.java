package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.Incidencia;
import com.dam.adp.atmapi.models.enums.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IncidenciaRepository extends JpaRepository<Incidencia, Long> {


    @Query("SELECT i FROM Incidencia i JOIN i.atm c WHERE c.ciudad = :ciudad")
    List<Incidencia>findByCiudad(@Param("ciudad") String ciudad);

    @Query("SELECT i FROM Incidencia i JOIN i.tecnico u WHERE u.username = :username AND u.rol = :rol")
    List<Incidencia> findByTecnicoUsernameAndRol(@Param("username") String username, @Param("rol") Rol rol);
}
