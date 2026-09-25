package com.techcorp.securedocs.security.authorization.abac.policies;

import com.techcorp.securedocs.security.authorization.abac.AbacContext;
import com.techcorp.securedocs.security.authorization.abac.PolicyResult;
import org.springframework.stereotype.Component;

@Component
public class EstadoUsuarioPolicy implements Policy {

    @Override
    public boolean esAplicable(AbacContext context) {
        return true;
    }

    @Override
    public PolicyResult evaluar(AbacContext context) {
        if (!"ACTIVO".equals(context.getUsuario().getEstado())) {
            return new PolicyResult(false, "Usuario suspendido o inactivo");
        }
        return new PolicyResult(true, null);
    }
}