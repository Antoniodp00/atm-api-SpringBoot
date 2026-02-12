package com.dam.adp.atmapi.models;

import com.dam.adp.atmapi.models.enums.Nivel;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "evento_log", schema = "sistema_cajeros")
public class EventoLog {
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

    @Size(max = 20)
    @Column(name = "codigo_error", length = 20)
    private String codigoError;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "nivel", length = 20)
    private Nivel nivel;

    @Lob
    @Column(name = "mensaje_tecnico")
    private String mensajeTecnico;

}