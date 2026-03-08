package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.Repuesto;
import com.dam.adp.atmapi.repositories.IncidenciaRepository;
import com.dam.adp.atmapi.repositories.RepuestoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de repuestos e inventario.
 * Permite crear repuestos, actualizar stock y consultar disponibilidad.
 */
@Service
public class RepuestoService {
    private final RepuestoRepository repuestoRepository;

    public RepuestoService(RepuestoRepository repuestoRepository) {
        this.repuestoRepository = repuestoRepository;
    }


    /**
     * Crea un nuevo repuesto en el inventario.
     * Valida que no exista previamente y que el stock sea válido.
     * @param repuesto Datos del repuesto a crear.
     * @return El repuesto creado.
     */
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

    /**
     * Actualiza el stock de un repuesto existente.
     * @param id Identificador del repuesto.
     * @param cantidad Nueva cantidad de stock.
     * @return El repuesto actualizado.
     */
    public Repuesto actualizarStock(Long id, Integer cantidad){
        Repuesto repuesto = repuestoRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Repuesto no encontrado", id));

        if (cantidad<0){
            throw new IllegalArgumentException("El stock actual no puede ser negativo");
        }

       repuesto.setStockActual(cantidad);
        return repuestoRepository.save(repuesto);
    }

    /**
     * Obtiene todos los repuestos registrados.
     * @return Lista de todos los repuestos.
     */
    public List<Repuesto> obtenerTodos(){
        return repuestoRepository.findAll();
    }

    /**
     * Encuentra repuestos utilizados en una ciudad específica.
     * @param ciudad Nombre de la ciudad.
     * @return Lista de repuestos consumidos en esa ubicación.
     */
    public List<Repuesto> findRepuestosUtilizadosEnCiudad(String ciudad) {
        return repuestoRepository.findRepuestosUtilizadosEnCiudad(ciudad);
    }
    }
