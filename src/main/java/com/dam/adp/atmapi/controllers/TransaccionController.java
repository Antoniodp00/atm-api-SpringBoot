package com.dam.adp.atmapi.controllers;

import com.dam.adp.atmapi.DTOs.TransaccionRequest;
import com.dam.adp.atmapi.models.Transaccion;
import com.dam.adp.atmapi.models.enums.TipoTransaccion;
import com.dam.adp.atmapi.services.TransaccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de transacciones.
 * Permite registrar operaciones y consultar el historial de movimientos.
 */
@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {
    private final TransaccionService transaccionService;

    public TransaccionController(TransaccionService transaccionService) {
        this.transaccionService = transaccionService;
    }

    /**
     * Obtiene el historial de transacciones de un cajero.
     * @param cajeroId Identificador del cajero.
     * @return Respuesta con la lista de transacciones.
     */
    @GetMapping("/cajero/{cajeroId}")
    public ResponseEntity<List<Transaccion>> obtenerHistorialTransacciones(@PathVariable String cajeroId) {
        return ResponseEntity.ok(transaccionService.obtenerHistorialCajero(cajeroId));
    }

    /**
     * Registra una nueva transacción en el sistema.
     * @param request Datos de la transacción (cajero, tipo y monto).
     * @return Respuesta con la transacción registrada.
     */
    @PostMapping
    public ResponseEntity<Transaccion> realizarTransaccion(@RequestBody TransaccionRequest request) {
        Transaccion nueva = transaccionService.registrarTransaccion(
                request.cajeroId(),
                request.tipo(),
                request.monto()

        );
        return ResponseEntity.ok(nueva);
    }
}
