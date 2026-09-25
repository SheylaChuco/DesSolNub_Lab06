package com.techcorp.securedocs.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "departamento_id")
    private Departamento departamento;

    @Column(name = "nivel_seguridad", nullable = false)
    private Integer nivelSeguridad;

    @Column(nullable = false)
    private String pais;

    @Column(name = "tipo_contrato", nullable = false)
    private String tipoContrato; // INTERNO / EXTERNO

    @Column(nullable = false)
    private String estado; // ACTIVO / INACTIVO / SUSPENDIDO
}