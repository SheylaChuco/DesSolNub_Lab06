package com.techcorp.securedocs.security.authorization;

import com.techcorp.securedocs.exception.AccesoDenegadoException;
import com.techcorp.securedocs.model.Documento;
import com.techcorp.securedocs.model.Usuario;
import com.techcorp.securedocs.security.authorization.abac.AbacContext;
import com.techcorp.securedocs.security.authorization.abac.AbacPolicyEngine;
import com.techcorp.securedocs.security.authorization.abac.PolicyResult;
import com.techcorp.securedocs.security.authorization.rbac.RbacService;
import com.techcorp.securedocs.service.AuditoriaService;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
public class AuthorizationService {

    private final RbacService rbacService;
    private final AbacPolicyEngine abacPolicyEngine;
    private final AuditoriaService auditoriaService;

    public AuthorizationService(RbacService rbacService, AbacPolicyEngine abacPolicyEngine,
                                AuditoriaService auditoriaService) {
        this.rbacService = rbacService;
        this.abacPolicyEngine = abacPolicyEngine;
        this.auditoriaService = auditoriaService;
    }

    /**
     * @param usuario        usuario autenticado
     * @param documento      recurso sobre el que se actúa (puede ser null si aún no existe, ej. al crear)
     * @param permisoRbac    nombre del permiso a validar en RBAC, ej. "CONSULTAR_DOCUMENTO"
     * @param accionAbac     acción para el contexto ABAC, ej. "CONSULTAR"
     * @param ip, ubicacion, dispositivo  atributos de entorno
     */
    public void autorizar(Usuario usuario, Documento documento, String permisoRbac, String accionAbac,
                          String ip, String ubicacion, String dispositivo) {

        String recurso = documento != null ? "Documento#" + documento.getId() : "Documento#nuevo";

        // 1. RBAC primero
        if (!rbacService.tienePermiso(usuario, permisoRbac)) {
            String motivo = "RBAC: tu rol no tiene el permiso " + permisoRbac;
            auditoriaService.registrar(usuario.getCorreo(), recurso, accionAbac, "DENEGADO", motivo);
            throw new AccesoDenegadoException(motivo);
        }

        // 2. Si RBAC aprueba, y hay un documento concreto sobre el que evaluar contexto, pasa por ABAC
        if (documento != null) {
            AbacContext context = new AbacContext(
                    usuario, documento, accionAbac,
                    LocalTime.now(), ip, ubicacion, dispositivo
            );

            PolicyResult resultado = abacPolicyEngine.evaluar(context);

            if (!resultado.isPermitido()) {
                String motivo = "ABAC: " + resultado.getMotivo();
                auditoriaService.registrar(usuario.getCorreo(), recurso, accionAbac, "DENEGADO", motivo);
                throw new AccesoDenegadoException(motivo);
            }
        }

        // 3. Pasó ambas capas
        auditoriaService.registrar(usuario.getCorreo(), recurso, accionAbac, "PERMITIDO", null);
    }
}