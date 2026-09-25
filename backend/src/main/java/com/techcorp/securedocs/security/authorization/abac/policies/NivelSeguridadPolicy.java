package com.techcorp.securedocs.security.authorization.abac.policies;

import com.techcorp.securedocs.security.authorization.abac.AbacContext;
import com.techcorp.securedocs.security.authorization.abac.PolicyResult;
import org.springframework.stereotype.Component;

@Component
public class NivelSeguridadPolicy implements Policy {

    @Override
    public boolean esAplicable(AbacContext context) {
        return true; // aplica siempre, sobre cualquier acción
    }

    @Override
    public PolicyResult evaluar(AbacContext context) {
        int nivelUsuario = context.getUsuario().getNivelSeguridad();
        int nivelDocumento = context.getDocumento().getNivelConfidencialidad();

        if (nivelUsuario < nivelDocumento) {
            return new PolicyResult(false, "Nivel de seguridad insuficiente");
        }
        return new PolicyResult(true, null);
    }
}