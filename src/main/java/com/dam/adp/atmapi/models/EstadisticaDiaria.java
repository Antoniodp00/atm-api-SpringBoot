package com.dam.adp.atmapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "estadistica_diaria", schema = "sistema_cajeros")
public class EstadisticaDiaria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atm_id")
    private Cajero atm;

    @NotNull
    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @ColumnDefault("0")
    @Column(name = "total_operaciones")
    private Integer totalOperaciones;

    @ColumnDefault("0.00")
    @Column(name = "volumen_dinero", precision = 15, scale = 2)
    private BigDecimal volumenDinero;

    @ColumnDefault("0.00")
    @Column(name = "tasa_fallos", precision = 5, scale = 2)
    private BigDecimal tasaFallos;

    @ColumnDefault("0")
    @Column(name = "usuarios_unicos")
    private Integer usuariosUnicos;

}