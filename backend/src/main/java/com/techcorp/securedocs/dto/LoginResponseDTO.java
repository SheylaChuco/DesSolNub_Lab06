package com.techcorp.securedocs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponseDTO {
    private String token;
    private String nombre;
    private String rol;
    private Long departamentoId;
    private String departamentoNombre;
}