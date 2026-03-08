package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.services.CajeroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de cajeros automáticos.
 * Expone endpoints para consultar, crear y modificar el estado de los cajeros.
 */
@RestController
@RequestMapping("/api/cajeros")
public class CajeroController {
    private final CajeroService cajeroService;

    public CajeroController(CajeroService cajeroService) {
        this.cajeroService = cajeroService;
    }

    /**
     * Obtiene los detalles de un cajero por su ID.
     * @param id Identificador del cajero.
     * @return Respuesta con el cajero encontrado.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cajero> obtenerCajeroById (@PathVariable String id){
        Cajero cajero = cajeroService.obtenerDetalle(id);
        return ResponseEntity.ok(cajero);
    }

    /**
     * Obtiene la lista de todos los cajeros activos.
     * @return Respuesta con la lista de cajeros activos.
     */
    @GetMapping("/activos")
    public ResponseEntity<List<Cajero>> obtenerTodosActivos() {
        return ResponseEntity.ok(cajeroService.obtenerTodosActivos());
    }

    /**
     * Obtiene la lista de todos los cajeros inactivos.
     * @return Respuesta con la lista de cajeros inactivos.
     */
    @GetMapping("/inactivos")
    public ResponseEntity<List<Cajero>> obtenerTodosInactivos() {
        return ResponseEntity.ok(cajeroService.obtenerTodosInactivos());
    }

    /**
     * Obtiene la lista de todos los cajeros registrados.
     * @return Respuesta con la lista completa de cajeros.
     */
    @GetMapping
    public ResponseEntity<List<Cajero>> obtenerTodos(){
        return ResponseEntity.ok(cajeroService.obtenerTodos());
    }

    /**
     * Obtiene cajeros con incidencias críticas.
     * @param prioridad Prioridad mínima para considerar crítica la incidencia.
     * @return Respuesta con la lista de cajeros críticos.
     */
    @GetMapping("/criticos/{prioridad}")
    public ResponseEntity<List<Cajero>> obtenerCajerosCriticos(@PathVariable Integer prioridad) {
    return ResponseEntity.ok(cajeroService.obtenerCajerosCriticos(prioridad));
    }

    /**
     * Crea un nuevo cajero en el sistema.
     * @param cajero Datos del nuevo cajero.
     * @return Respuesta con el cajero creado y estado HTTP 201.
     */
    @PostMapping
    public ResponseEntity<Cajero> crearCajero(@RequestBody Cajero cajero){
        Cajero nuevoCajero = cajeroService.crearCajero(cajero);
        return new ResponseEntity<>(nuevoCajero, HttpStatus.CREATED);
    }

    /**
     * Desactiva un cajero existente.
     * @param id Identificador del cajero a desactivar.
     * @return Respuesta con el cajero desactivado.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Cajero> desactivarCajero(@PathVariable String id){
        Cajero cajeroDesactivado = cajeroService.desactivarCajero(id);
        return ResponseEntity.ok(cajeroDesactivado);
    }
    /**
     * Activa un cajero existente.
     * @param id Identificador del cajero a activar.
     * @return Respuesta con el cajero activado.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Cajero> activarCajero(@PathVariable String id){
        Cajero cajeroActivado = cajeroService.activarCajero(id);
        return ResponseEntity.ok(cajeroActivado);
    }


}
