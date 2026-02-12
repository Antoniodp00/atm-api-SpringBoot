package com.dam.adp.atmapi.models;

import com.dam.adp.atmapi.models.enums.Turno;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "asignacion_tecnico", schema = "sistema_cajeros")
public class AsignacionTecnico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cajero_id", nullable = false)
    private Cajero cajero;

    @Enumerated(EnumType.STRING)
    @Column(name = "turno", length = 20)
    private Turno turno;

    @Column(name = "fecha_asignacion")
    private LocalDate fechaAsignacion;

}