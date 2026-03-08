package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.AsignacionTecnico;
import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.models.enums.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

/**
 * Repositorio JPA para la entidad AsignacionTecnico.
 * Permite gestionar las asignaciones de técnicos a cajeros.
 */
public interface AsignacionTecnicoRepository extends JpaRepository<AsignacionTecnico, Long> {


    boolean existsByUsuarioAndFechaAsignacionAndTurno(Usuario usuario, LocalDate fechaAsignacion, Turno turno);
}
