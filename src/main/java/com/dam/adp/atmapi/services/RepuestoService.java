package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.Repuesto;
import com.dam.adp.atmapi.repositories.IncidenciaRepository;
import com.dam.adp.atmapi.repositories.RepuestoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RepuestoService {
    private final RepuestoRepository repuestoRepository;

    public RepuestoService(RepuestoRepository repuestoRepository) {
        this.repuestoRepository = repuestoRepository;
    }


    public Repuesto crearRepuesto(Repuesto repuesto){
        if (repuesto.getId() != null && repuestoRepository.existsById(repuesto.getId())) {
            throw new IllegalArgumentException("El repuesto ya existe con el ID: " + repuesto.getId());
        }
        if (repuestoRepository.existsByNombre(repuesto.getNombre())){
            throw new IllegalArgumentException("Ya existe un repuesto con el nombre: " + repuesto.getNombre());
        }
        if (repuesto.getStockActual()<0){
            throw new IllegalArgumentException("El stock actual no puede ser negativo");
        }
        return repuestoRepository.save(repuesto);
    }

    public Repuesto actualizarStock(Long id, Integer cantidad){
        Repuesto repuesto = repuestoRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Repuesto no encontrado", id));

        if (cantidad<0){
            throw new IllegalArgumentException("El stock actual no puede ser negativo");
        }

       repuesto.setStockActual(cantidad);
        return repuestoRepository.save(repuesto);
    }

    public List<Repuesto> obtenerTodos(){
        return repuestoRepository.findAll();
    }
}
