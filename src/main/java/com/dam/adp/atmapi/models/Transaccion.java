package com.dam.adp.atmapi.models;

import com.dam.adp.atmapi.models.enums.TipoTransaccion;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

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
    private LocalDateTime fechaHora;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "tipo", length = 20)
    private TipoTransaccion tipo;

    @Column(name = "monto")
    private Double monto;

    @Size(max = 64)
    @NotNull
    @Column(name = "tarjeta_hash", nullable = false, length = 64)
    private String tarjetaHash;

    @Size(max = 30)
    @Column(name = "resultado", length = 30)
    private String resultado;

}