package com.techcorp.securedocs.security.authorization.abac;

import com.techcorp.securedocs.security.authorization.abac.policies.Policy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbacPolicyEngine {

    private final List<Policy> politicas;

    // Spring inyecta automáticamente TODAS las clases que implementan Policy
    public AbacPolicyEngine(List<Policy> politicas) {
        this.politicas = politicas;
    }

    public PolicyResult evaluar(AbacContext context) {
        for (Policy politica : politicas) {
            if (politica.esAplicable(context)) {
                PolicyResult resultado = politica.evaluar(context);
                if (!resultado.isPermitido()) {
                    return resultado; // corta en la primera política que deniegue
                }
            }
        }
        return new PolicyResult(true, null); // pasó todas las políticas aplicables
    }
}