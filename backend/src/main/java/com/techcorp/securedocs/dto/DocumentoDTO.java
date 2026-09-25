package com.techcorp.securedocs.dto;

import lombok.Data;

@Data
public class DocumentoDTO {
    private String titulo;
    private String descripcion;
    private Long departamentoId;
    private Integer nivelConfidencialidad;
    private String pais;
}