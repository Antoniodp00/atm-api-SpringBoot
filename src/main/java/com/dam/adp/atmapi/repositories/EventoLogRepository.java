package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.EventoLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoLogRepository extends JpaRepository<EventoLog, Long> {
}
