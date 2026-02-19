package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.repositories.TransaccionRepository;
import com.dam.adp.atmapi.services.TransaccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticaController {
    private final TransaccionService transaccionService;

    public  EstadisticaController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    @GetMapping("/cajero/{cajeroId}")
    public ResponseEntity<EstadisticaResponse> obtenerEstadisticasCajero(@PathVariable String cajeroId){
        long totalOperaciones = transaccionService.contarOperaciones(cajeroId);
        double totalIngresado = transaccionService.sumarIngresos(cajeroId);
        double totalRetirado = transaccionService.sumarRetiros(cajeroId);
        double totalTransferido = transaccionService.sumarTransferencias(cajeroId);

        EstadisticaResponse response = new EstadisticaResponse(
                cajeroId,
                totalOperaciones,
                totalIngresado,
                totalRetirado,
                totalTransferido
        );

        return ResponseEntity.ok(response);
    }

    public record EstadisticaResponse(
            String cajeroId,
            long totalOperaciones,
            double totalIngresado,
            double totalRetirado,
            double totalTransferido
    ) {}
}
