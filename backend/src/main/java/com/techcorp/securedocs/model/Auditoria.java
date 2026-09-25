package com.techcorp.securedocs.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria")
@Data
public class Auditoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String recurso;

    @Column(nullable = false)
    private String accion;

    @Column(nullable = false)
    private LocalDateTime fecha;

    @Column(nullable = false)
    private String resultado; // PERMITIDO / DENEGADO

    private String motivo;
}