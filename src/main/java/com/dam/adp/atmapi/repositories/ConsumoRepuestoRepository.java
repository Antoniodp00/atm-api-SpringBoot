package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.ConsumoRepuesto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumoRepuestoRepository extends JpaRepository<ConsumoRepuesto, Long> {
    List<ConsumoRepuesto> findbyIncidenciaId(Long incidenciaId);
}
