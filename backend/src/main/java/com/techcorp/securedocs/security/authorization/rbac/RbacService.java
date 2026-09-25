package com.techcorp.securedocs.security.authorization.rbac;

import com.techcorp.securedocs.model.Permiso;
import com.techcorp.securedocs.model.Usuario;
import org.springframework.stereotype.Service;

@Service
public class RbacService {

    public boolean tienePermiso(Usuario usuario, String nombrePermiso) {
        return usuario.getRol().getPermisos().stream()
                .map(Permiso::getNombre)
                .anyMatch(p -> p.equals(nombrePermiso));
    }
}