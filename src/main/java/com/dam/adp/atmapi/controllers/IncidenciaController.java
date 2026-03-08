package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.DTOs.AsignarTecnicoRequest;
import com.dam.adp.atmapi.DTOs.ConsumoRequest;
import com.dam.adp.atmapi.DTOs.CrearIncidenciaRequest;
import com.dam.adp.atmapi.models.Incidencia;
import com.dam.adp.atmapi.models.enums.Turno;
import com.dam.adp.atmapi.services.IncidenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

/**
 * Controlador REST para la gestión de incidencias.
 * Permite reportar problemas, asignar técnicos y registrar soluciones.
 */
@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    /**
     * Obtiene las incidencias reportadas en una ciudad.
     * @param ciudad Nombre de la ciudad.
     * @return Respuesta con la lista de incidencias.
     */
    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Incidencia>> obtenerIncidenciasPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(incidenciaService.obtenerIncidenciasPorCiudad(ciudad));
    }
    /**
     * Obtiene las incidencias asignadas a un técnico.
     * @param username Nombre de usuario del técnico.
     * @return Respuesta con la lista de incidencias asignadas.
     */
    @GetMapping("/tecnico/{username}")
    public ResponseEntity<List<Incidencia>> obtenerIncidenciasPorTecnico(@PathVariable String username) {
        return ResponseEntity.ok(incidenciaService.obtenerIncidenciasPorTecnico(username));
    }


    /**
     * Crea una nueva incidencia en el sistema.
     * @param request Datos de la incidencia a crear.
     * @return Respuesta con la incidencia creada y estado HTTP 201.
     */
    @PostMapping
    public ResponseEntity<Incidencia> crearIncidencia(@RequestBody CrearIncidenciaRequest request) {
        Incidencia nueva = incidenciaService.crearIncidencia(
                request.cajeroId(),
                request.descripcion(),
                request.prioridad()
        );
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    /**
     * Registra el consumo de repuestos en una incidencia.
     * @param id Identificador de la incidencia.
     * @param request Datos del consumo (repuesto y cantidad).
     * @return Respuesta con mensaje de confirmación.
     */
    @PostMapping("/{id}/repuestos")
    public ResponseEntity<String> procesarConsumo(
            @PathVariable Long id,
            @RequestBody ConsumoRequest request){
        incidenciaService.procesarConsumo(id, request.repuestoId(), request.cantidad());
        return ResponseEntity.ok("Consumo registrado y stock actualizado correctamente");
    }

    /**
     * Asigna un técnico a una incidencia existente.
     * @param id Identificador de la incidencia.
     * @param request Datos de la asignación (técnico y turno).
     * @return Respuesta con la incidencia actualizada.
     */
    @PutMapping("/{id}/asignar-tecnico")
    public ResponseEntity<Incidencia> asignarTecnico(
            @PathVariable Long id,
            @RequestBody AsignarTecnicoRequest request) {

        Incidencia actualizada = incidenciaService.asignarTecnicoAIncidencia(
                id,
                request.id(),
                request.turno()
        );
        return ResponseEntity.ok(actualizada);
    }

    /**
     * Marca una incidencia como resuelta.
     * @param id Identificador de la incidencia.
     * @return Respuesta con la incidencia resuelta.
     */
    @PutMapping("/{id}/resolver")
    public ResponseEntity<Incidencia> resolverIncidencia(@PathVariable Long id){
        return ResponseEntity.ok(incidenciaService.resolverIncidencia(id));
    }


}
