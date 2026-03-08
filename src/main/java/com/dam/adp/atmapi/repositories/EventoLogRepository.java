package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.EventoLog;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad EventoLog.
 * Permite almacenar y consultar logs de eventos del sistema.
 */
public interface EventoLogRepository extends JpaRepository<EventoLog, Long> {
}
