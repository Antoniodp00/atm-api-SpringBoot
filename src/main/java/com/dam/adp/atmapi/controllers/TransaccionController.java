package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.models.Transaccion;
import com.dam.adp.atmapi.models.enums.TipoTransaccion;
import com.dam.adp.atmapi.services.TransaccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {
    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping("/cajero/{cajeroId}")
    public ResponseEntity<List<Transaccion>> obtenerHistorialTransacciones(@PathVariable String cajeroId) {
        return ResponseEntity.ok(transaccionService.obtenerHistorialCajero(cajeroId));
    }

    @PostMapping
    public ResponseEntity<Transaccion> realizarTransaccion(@RequestBody TransaccionRequest request) {
        Transaccion nueva = transaccionService.registrarTransaccion(
                request.cajeroId(),
                request.tipo(),
                request.monto()

        );
        return ResponseEntity.ok(nueva);
    }

    public record TransaccionRequest(String cajeroId, TipoTransaccion tipo, Double monto){}
}
