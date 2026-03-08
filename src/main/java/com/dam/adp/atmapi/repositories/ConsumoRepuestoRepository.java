package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.ConsumoRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repositorio JPA para la entidad ConsumoRepuesto.
 * Gestiona el registro de repuestos consumidos en incidencias.
 */
public interface ConsumoRepuestoRepository extends JpaRepository<ConsumoRepuesto, Long> {
    List<ConsumoRepuesto> findByIncidenciaId(Long incidenciaId);
}
