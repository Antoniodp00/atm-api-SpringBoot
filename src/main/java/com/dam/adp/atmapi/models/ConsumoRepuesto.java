package com.dam.adp.atmapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "consumo_repuesto", schema = "sistema_cajeros")
public class ConsumoRepuesto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "incidencia_id")
    private Incidencia incidencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repuesto_id")
    private Repuesto repuesto;

    @NotNull
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

}