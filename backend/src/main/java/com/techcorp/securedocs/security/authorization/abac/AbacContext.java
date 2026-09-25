package com.techcorp.securedocs.security.authorization.abac;

import com.techcorp.securedocs.model.Documento;
import com.techcorp.securedocs.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;

@Data
@AllArgsConstructor
public class AbacContext {
    private Usuario usuario;
    private Documento documento;
    private String accion;       // CONSULTAR, MODIFICAR, APROBAR, ELIMINAR...

    // atributos de entorno
    private LocalTime hora;
    private String ip;
    private String ubicacion;
    private String dispositivo;
}