package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.ConsumoRepuesto;
import com.dam.adp.atmapi.models.Incidencia;
import com.dam.adp.atmapi.models.Repuesto;
import com.dam.adp.atmapi.repositories.ConsumoRepuestoRepository;
import com.dam.adp.atmapi.repositories.IncidenciaRepository;
import com.dam.adp.atmapi.repositories.RepuestoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class IncidenciaService {

    private final IncidenciaRepository incidenciaRepository;
    private final RepuestoRepository repuestoRepository;
    private final ConsumoRepuestoRepository consumoRepuestoRepository;

    @Autowired
    public IncidenciaService(IncidenciaRepository incidenciaRepository, RepuestoRepository repuestoRepository, ConsumoRepuestoRepository consumoRepuestoRepository) {
        this.incidenciaRepository = incidenciaRepository;
        this.repuestoRepository = repuestoRepository;
        this.consumoRepuestoRepository = consumoRepuestoRepository;
    }

    @Transactional
    public void procesarConsumo(Long incidenciaId, Long repuestoId,Integer cantidad) {
        Incidencia incidencia = incidenciaRepository.findById(incidenciaId)
                .orElseThrow(() -> new RecordNotFoundException("Incidencia no encontrada con ID: ", repuestoId));

Repuesto repuesto = repuestoRepository.findById(repuestoId)
        .orElseThrow(() -> new RecordNotFoundException("Repuesto no encontrado con ID: ", repuestoId) );

if (repuesto.getStockActual()<cantidad){
    throw new IllegalArgumentException("Stock insuficiente para el repuesto: "+repuesto.getNombre());
}

repuesto.setStockActual(repuesto.getStockActual()-cantidad);
repuestoRepository.save(repuesto);

        ConsumoRepuesto consumo = new ConsumoRepuesto();
        consumo.setRepuesto(repuesto);
        consumo.setCantidad(cantidad);
       consumo.setIncidencia(incidencia);

       consumoRepuestoRepository.save(consumo);


    }

}

