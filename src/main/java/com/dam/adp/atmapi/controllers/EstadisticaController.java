package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.DTOs.EstadisticaResponse;
import com.dam.adp.atmapi.repositories.TransaccionRepository;
import com.dam.adp.atmapi.services.TransaccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para la consulta de estadísticas.
 * Proporciona métricas sobre el uso y rendimiento de los cajeros.
 */
@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticaController {
    private final TransaccionService transaccionService;

    public  EstadisticaController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    /**
     * Obtiene las estadísticas de operación de un cajero.
     * @param cajeroId Identificador del cajero.
     * @return Respuesta con los totales de operaciones y montos.
     */
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

}
