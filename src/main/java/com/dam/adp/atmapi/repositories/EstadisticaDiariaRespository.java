package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.EstadisticaDiaria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para la entidad EstadisticaDiaria.
 * Permite almacenar y consultar estadísticas diarias de los cajeros.
 */
public interface EstadisticaDiariaRespository extends JpaRepository<EstadisticaDiaria, Long> {
}
