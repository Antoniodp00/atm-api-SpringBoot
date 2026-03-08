package com.dam.adp.atmapi.services;

import com.dam.adp.atmapi.exceptions.RecordNotFoundException;
import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.models.enums.EstadoIncidencia;
import com.dam.adp.atmapi.repositories.CajeroRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Servicio para la gestión de cajeros automáticos.
 * Proporciona métodos para crear, consultar, activar y desactivar cajeros.
 */
@Service
public class CajeroService {
    private final CajeroRepository cajeroRepository;

    private static final String PATRON_ID_CAJERO = "^ATM-[A-Z]{3}-\\d{3}$";

    public CajeroService(CajeroRepository cajeroRepository) {
        this.cajeroRepository = cajeroRepository;
    }

    /**
     * Obtiene todos los cajeros registrados en el sistema.
     * @return Lista de todos los cajeros.
     */
    public List<Cajero>obtenerTodos(){
        return cajeroRepository.findAll();
    }

    /**
     * Obtiene todos los cajeros activos.
     * @return Lista de cajeros activos.
     */
    public List<Cajero> obtenerTodosActivos(){
        return cajeroRepository.findByActivoTrue();
    }

    /**
     * Obtiene todos los cajeros inactivos.
     * @return Lista de cajeros inactivos.
     */
    public List<Cajero> obtenerTodosInactivos(){
        return cajeroRepository.findByActivoFalse();
    }

    /**
     * Obtiene los detalles de un cajero específico por su ID.
     * @param id Identificador del cajero.
     * @return El cajero encontrado.
     * @throws RecordNotFoundException Si no se encuentra el cajero.
     */
    public Cajero obtenerDetalle(String id){
        return cajeroRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Cajero no encontrado",id));

    }

    /**
     * Crea un nuevo cajero en el sistema.
     * Valida el formato del ID y que no exista previamente.
     * @param cajero Objeto cajero a crear.
     * @return El cajero creado.
     * @throws IllegalArgumentException Si el ID no cumple el formato o ya existe.
     */
    public Cajero crearCajero(Cajero cajero){

        if (!cajero.getId().matches(PATRON_ID_CAJERO)){
            throw new IllegalArgumentException("El ID debe tener el formato ATM-XXX-000 (Ej: ATM-COR-001)");
        }


        if (cajeroRepository.existsById(cajero.getId())){
            throw new IllegalArgumentException("El cajero ya existe con el ID: " + cajero.getId());

        }
        cajero.setActivo(true);

        return cajeroRepository.save(cajero);
    }

    /**
     * Desactiva un cajero existente.
     * @param id Identificador del cajero a desactivar.
     * @return El cajero actualizado con estado inactivo.
     */
    public Cajero desactivarCajero(String id){
        Cajero cajero = obtenerDetalle(id);
        cajero.setActivo(false);
        return cajeroRepository.save(cajero);
    }

    /**
     * Activa un cajero existente.
     * @param id Identificador del cajero a activar.
     * @return El cajero actualizado con estado activo.
     */
    public Cajero activarCajero(String id){
        Cajero cajero = obtenerDetalle(id);
        cajero.setActivo(true);
        return cajeroRepository.save(cajero);
    }

    /**
     * Obtiene cajeros con incidencias críticas pendientes.
     * @param prioridadMinima Prioridad mínima para considerar la incidencia crítica.
     * @return Lista de cajeros con incidencias graves no resueltas.
     */
    public List<Cajero> obtenerCajerosCriticos(Integer prioridadMinima) {
        // Buscamos cajeros con prioridad >= a la que nos pidan, y que su estado NO sea RESUELTA
        return cajeroRepository.findCajerosConIncidenciasGraves(prioridadMinima, EstadoIncidencia.RESUELTA);
    }


}
