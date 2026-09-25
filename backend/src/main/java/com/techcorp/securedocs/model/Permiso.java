package com.techcorp.securedocs.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "permisos")
@Data
public class Permiso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre; // ej. CREAR_DOCUMENTO, APROBAR_DOCUMENTO
}