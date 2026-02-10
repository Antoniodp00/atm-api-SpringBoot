package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, Long> {
}
