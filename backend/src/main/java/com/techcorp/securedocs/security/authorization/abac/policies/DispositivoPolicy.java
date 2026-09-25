package com.techcorp.securedocs.security.authorization.abac.policies;

import com.techcorp.securedocs.security.authorization.abac.AbacContext;
import com.techcorp.securedocs.security.authorization.abac.PolicyResult;
import org.springframework.stereotype.Component;

@Component
public class DispositivoPolicy implements Policy {

    @Override
    public boolean esAplicable(AbacContext context) {
        return context.getDocumento().getNivelConfidencialidad() >= 4;
    }

    @Override
    public PolicyResult evaluar(AbacContext context) {
        if (!"CORPORATIVO".equals(context.getDispositivo())) {
            return new PolicyResult(false, "Este documento solo puede consultarse desde un dispositivo corporativo");
        }
        return new PolicyResult(true, null);
    }
}