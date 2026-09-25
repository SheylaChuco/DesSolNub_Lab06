package com.techcorp.securedocs.security.authorization.abac.policies;

import com.techcorp.securedocs.security.authorization.abac.AbacContext;
import com.techcorp.securedocs.security.authorization.abac.PolicyResult;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class PropiedadPolicy implements Policy {

    private static final Set<String> EXENTOS = Set.of("GERENTE", "ADMINISTRADOR");

    @Override
    public boolean esAplicable(AbacContext context) {
        return "MODIFICAR".equals(context.getAccion())
                && !EXENTOS.contains(context.getUsuario().getRol().getNombre());
    }

    @Override
    public PolicyResult evaluar(AbacContext context) {
        boolean esPropietario = context.getUsuario().getId().equals(context.getDocumento().getPropietario().getId());

        if (!esPropietario) {
            return new PolicyResult(false, "Solo el propietario puede modificar este documento");
        }
        return new PolicyResult(true, null);
    }
}