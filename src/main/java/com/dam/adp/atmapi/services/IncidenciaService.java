package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.ConsumoRepuesto;
import com.dam.adp.atmapi.models.Incidencia;
import com.dam.adp.atmapi.models.Repuesto;
import com.dam.adp.atmapi.models.enums.EstadoIncidencia;
import com.dam.adp.atmapi.repositories.ConsumoRepuestoRepository;
import com.dam.adp.atmapi.repositories.IncidenciaRepository;
import com.dam.adp.atmapi.repositories.RepuestoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final RepuestoRepository repuestoRepository;
    private final ConsumoRepuestoRepository consumoRepuestoRepository;
    private final CajeroService cajeroService;

    @Autowired
    public IncidenciaService(IncidenciaRepository incidenciaRepository, RepuestoRepository repuestoRepository, ConsumoRepuestoRepository consumoRepuestoRepository, CajeroService cajeroService) {
        this.incidenciaRepository = incidenciaRepository;
        this.repuestoRepository = repuestoRepository;
        this.consumoRepuestoRepository = consumoRepuestoRepository;
        this.cajeroService = cajeroService;
    }

    public Incidencia crearIncidencia(String idCajero, String descripcion, Integer prioridad) {
        Cajero cajero = cajeroService.obtenerDetalle(idCajero);

        if (!cajero.getActivo()) {
            throw new IllegalArgumentException("El cajero no está activo");
        }

        Incidencia incidencia = new Incidencia();
        incidencia.setAtm(cajero);
        incidencia.setDescripcion(descripcion);
        incidencia.setPrioridad(prioridad);
        incidencia.setEstado(EstadoIncidencia.ABIERTA);
        incidencia.setFechaApertura(LocalDateTime.now());


        return incidenciaRepository.save(incidencia);
    }

    @Transactional
    public void procesarConsumo(Long incidenciaId, Long repuestoId, Integer cantidad) {
        Incidencia incidencia = incidenciaRepository.findById(incidenciaId)
                .orElseThrow(() -> new RecordNotFoundException("Incidencia no encontrada con ID: ", incidenciaId));

        if (incidencia.getEstado() == EstadoIncidencia.RESUELTA) {
            throw new IllegalStateException("La incidencia ya está resuelta, no se puede modificar");
        }

        Repuesto repuesto = repuestoRepository.findById(repuestoId)
                .orElseThrow(() -> new RecordNotFoundException("Repuesto no encontrado con ID: ", repuestoId));

        if (repuesto.getStockActual() < cantidad) {
            throw new IllegalArgumentException("Stock insuficiente para el repuesto: " + repuesto.getNombre());
        }

        repuesto.setStockActual(repuesto.getStockActual() - cantidad);
        repuestoRepository.save(repuesto);

        ConsumoRepuesto consumo = new ConsumoRepuesto();
        consumo.setRepuesto(repuesto);
        consumo.setIncidencia(incidencia);
        consumo.setCantidad(cantidad);

        consumoRepuestoRepository.save(consumo);
    }

    public List<Incidencia> obtenerIncidenciasPorCiudad(String ciudad) {
        if (ciudad == null || ciudad.trim().isEmpty()){
            throw new IllegalArgumentException("La ciudad no puede estar vacía");
        }
        return incidenciaRepository.findByCiudad(ciudad);
    }

}

