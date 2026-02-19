package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.models.Repuesto;
import com.dam.adp.atmapi.services.RepuestoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/repuestos")
public class RepuestoController {

    private final RepuestoService repuestoService;

    public RepuestoController(RepuestoService repuestoService) {
        this.repuestoService = repuestoService;
    }

    @PostMapping
    public ResponseEntity<Repuesto> crearRepuesto(@RequestBody Repuesto repuesto){
        return new ResponseEntity<>(repuestoService.crearRepuesto(repuesto), HttpStatus.CREATED);
    }}
