package com.techcorp.securedocs.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private String nombre;
    private String correo;
    private String password;       // solo se usa al crear
    private Long rolId;
    private Long departamentoId;   // puede ser null (ej. invitados)
    private Integer nivelSeguridad;
    private String pais;
    private String tipoContrato;
    private String estado;
}