package com.dam.adp.atmapi.repositories;

import com.dam.adp.atmapi.models.Cajero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CajeroRepository  extends JpaRepository<Cajero, String> {

    List<Cajero> findByActivoTrue();

    List<Cajero> findByActivoFalse();
}
