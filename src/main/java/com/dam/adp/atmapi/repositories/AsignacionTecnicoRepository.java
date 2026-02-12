package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.AsignacionTecnico;
import com.dam.adp.atmapi.models.Usuario;
import com.dam.adp.atmapi.models.enums.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AsignacionTecnicoRepository extends JpaRepository<AsignacionTecnico, Long> {
    boolean existsByFechaAsignacionAndTurno();

    boolean existsByFechaAsignacionAndTurno(Usuario tecnico, LocalDate fecha, Turno turno);
}
