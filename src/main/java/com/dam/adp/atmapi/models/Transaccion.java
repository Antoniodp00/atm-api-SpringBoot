package com.dam.adp.atmapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "transaccion", schema = "sistema_cajeros")
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atm_id")
    private Cajero atm;

    @NotNull
    @Column(name = "fecha_hora", nullable = false)
    private Instant fechaHora;

    @Size(max = 20)
    @Column(name = "tipo", length = 20)
    private String tipo;

    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal monto;

    @Size(max = 64)
    @NotNull
    @Column(name = "tarjeta_hash", nullable = false, length = 64)
    private String tarjetaHash;

    @Size(max = 30)
    @Column(name = "resultado", length = 30)
    private String resultado;

}