package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.Repuesto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepuestoRepository extends JpaRepository<Repuesto, Long> {

    boolean existsByNombre(String nombre);
}
