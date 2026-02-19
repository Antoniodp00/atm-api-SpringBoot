package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.models.Incidencia;
import com.dam.adp.atmapi.services.IncidenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@RequestMapping("/api/incidencias")
public class IncidenciaController {

    private final IncidenciaService incidenciaService;

    public IncidenciaController(IncidenciaService incidenciaService) {
        this.incidenciaService = incidenciaService;
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Incidencia>> obtenerIncidenciasPorCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(incidenciaService.obtenerIncidenciasPorCiudad(ciudad));
    }

    @PostMapping
    public ResponseEntity<Incidencia> crearIncidencia(@RequestBody CrearIncidenciaRequest request) {
        Incidencia nueva = incidenciaService.crearIncidencia(
                request.cajeroId(),
                request.descripcion(),
                request.prioridad()
        );
        return new ResponseEntity<>(nueva, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/repuestos")
    public ResponseEntity<String> procesarConsumo(
            @PathVariable Long id,
            @RequestBody ConsumoRequest request){
        incidenciaService.procesarConsumo(id, request.repuestoId,request.cantidad);
        return ResponseEntity.ok("Consumo registrado y stock actualizado correctamente");
    }


public record CrearIncidenciaRequest(String cajeroId, String descripcion, Integer prioridad){}
    public record ConsumoRequest(Long repuestoId, Integer cantidad){}
}
