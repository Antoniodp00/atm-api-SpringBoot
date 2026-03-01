package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.models.Repuesto;
import com.dam.adp.atmapi.services.RepuestoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {

    private final RepuestoService repuestoService;

    public RepuestoController(RepuestoService repuestoService) {
        this.repuestoService = repuestoService;
    }

    @GetMapping
    public ResponseEntity<List<Repuesto>> obtenerTodos(){
        return ResponseEntity.ok(repuestoService.obtenerTodos());
    }

    @GetMapping("/ciudad/{ciudad}")
    public ResponseEntity<List<Repuesto>> findRepuestosUtilizadosEnCiudad(@PathVariable String ciudad) {
        return ResponseEntity.ok(repuestoService.findRepuestosUtilizadosEnCiudad(ciudad));
    }



    @PostMapping
    public ResponseEntity<Repuesto> crearRepuesto(@RequestBody Repuesto repuesto){
        return new ResponseEntity<>(repuestoService.crearRepuesto(repuesto), HttpStatus.CREATED);
    }}

