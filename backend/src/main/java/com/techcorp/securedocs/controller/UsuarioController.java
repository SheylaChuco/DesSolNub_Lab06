package com.techcorp.securedocs.controller;

import com.techcorp.securedocs.dto.UsuarioDTO;
import com.techcorp.securedocs.exception.AccesoDenegadoException;
import com.techcorp.securedocs.model.Usuario;
import com.techcorp.securedocs.security.AuthenticatedUserProvider;
import com.techcorp.securedocs.security.authorization.rbac.RbacService;
import com.techcorp.securedocs.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final RbacService rbacService;
    private final AuthenticatedUserProvider authenticatedUserProvider;

    public UsuarioController(UsuarioService usuarioService, RbacService rbacService,
                             AuthenticatedUserProvider authenticatedUserProvider) {
        this.usuarioService = usuarioService;
        this.rbacService = rbacService;
        this.authenticatedUserProvider = authenticatedUserProvider;
    }

    @GetMapping
    public List<Usuario> listar() {
        exigirPermiso("GESTIONAR_USUARIOS");
        return usuarioService.listar();
    }

    @PostMapping
    public Usuario crear(@RequestBody UsuarioDTO dto) {
        exigirPermiso("GESTIONAR_USUARIOS");
        return usuarioService.crear(dto);
    }

    @PutMapping("/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        exigirPermiso("GESTIONAR_USUARIOS");
        return usuarioService.actualizar(id, dto);
    }

    private void exigirPermiso(String permiso) {
        Usuario actual = authenticatedUserProvider.obtenerUsuarioActual();
        if (!rbacService.tienePermiso(actual, permiso)) {
            throw new AccesoDenegadoException("Tu rol no tiene permiso para gestionar usuarios");
        }
    }
}