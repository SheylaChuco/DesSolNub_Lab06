package com.techcorp.securedocs.controller;

import com.techcorp.securedocs.exception.AccesoDenegadoException;
import com.techcorp.securedocs.model.Auditoria;
import com.techcorp.securedocs.model.Usuario;
import com.techcorp.securedocs.security.AuthenticatedUserProvider;
import com.techcorp.securedocs.security.authorization.rbac.RbacService;
import com.techcorp.securedocs.service.AuditoriaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/auditoria")
public class AuditoriaController {

    private final AuditoriaService auditoriaService;
    private final RbacService rbacService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public AuditoriaController(AuditoriaService auditoriaService, RbacService rbacService,
                               AuthenticatedUserProvider authenticatedUserProvider) {
        this.auditoriaService = auditoriaService;
        this.rbacService = rbacService;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @GetMapping
    public List<Auditoria> listar() {
        Usuario usuario = authenticatedUserProvider.obtenerUsuarioActual();

        if (!rbacService.tienePermiso(usuario, "VER_AUDITORIA")) {
            throw new AccesoDenegadoException("Tu rol no tiene permiso para ver la auditoría");
        }

        return auditoriaService.listarTodo();
    }
}