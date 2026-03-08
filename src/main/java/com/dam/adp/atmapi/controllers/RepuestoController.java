package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.models.Repuesto;
import com.dam.adp.atmapi.services.RepuestoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de repuestos.
 * Permite consultar el inventario y registrar nuevos repuestos.
 */
@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {

    private final RepuestoService repuestoService;

    public RepuestoController(RepuestoService repuestoService) {
        this.repuestoService = repuestoService;
    }

    /**
     * Obtiene la lista de todos los repuestos disponibles.
     * @return Respuesta con la lista de repuestos.
     */
    @GetMapping
    public ResponseEntity<List<Repuesto>> obtenerTodos(){
        return ResponseEntity.ok(repuestoService.obtenerTodos());
    }

    /**
     * Obtiene los repuestos utilizados en una ciudad específica.
     * @param ciudad Nombre de la ciudad.
     * @return Respuesta con la lista de repuestos utilizados.
     */
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Repuesto>> findRepuestosUtilizadosEnCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(repuestoService.findRepuestosUtilizadosEnCiudad(ciudad));
    }



    /**
     * Crea un nuevo repuesto en el inventario.
     * @param repuesto Datos del nuevo repuesto.
     * @return Respuesta con el repuesto creado y estado HTTP 201.
     */
    @PostMapping
    public ResponseEntity<Repuesto> crearRepuesto(@RequestBody Repuesto repuesto){
        return new ResponseEntity<>(repuestoService.crearRepuesto(repuesto), HttpStatus.CREATED);
    }}
