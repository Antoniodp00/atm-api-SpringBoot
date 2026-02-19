package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.models.Cajero;
import com.dam.adp.atmapi.services.CajeroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cajeros")
public class CajeroController {
    private final CajeroService cajeroService;

    public CajeroController(CajeroService cajeroService) {
        this.cajeroService = cajeroService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cajero> obtenerCajeroById (@PathVariable String id){
        Cajero cajero = cajeroService.obtenerDetalle(id);
        return ResponseEntity.ok(cajero);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Cajero>> obtenerTodosActivos() {
        return ResponseEntity.ok(cajeroService.obtenerTodosActivos());
    }

    @GetMapping("/inactivos")
    public ResponseEntity<List<Cajero>> obtenerTodosInactivos() {
        return ResponseEntity.ok(cajeroService.obtenerTodosInactivos());
    }

    @GetMapping
    public ResponseEntity<List<Cajero>> obtenerTodos(){
        return ResponseEntity.ok(cajeroService.obtenerTodos());
    }

    @PostMapping
    public ResponseEntity<Cajero> crearCajero(@RequestBody Cajero cajero){
        Cajero nuevoCajero = cajeroService.crearCajero(cajero);
        return new ResponseEntity<>(nuevoCajero, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cajero> desactivarCajero(@PathVariable String id){
        Cajero cajeroDesactivado = cajeroService.desactivarCajero(id);
        return ResponseEntity.ok(cajeroDesactivado);
    }
}
