package com.techcorp.securedocs.security.authorization.abac.policies;

import com.techcorp.securedocs.security.authorization.abac.AbacContext;
import com.techcorp.securedocs.security.authorization.abac.PolicyResult;
import org.springframework.stereotype.Component;

@Component
public class PaisPolicy implements Policy {

    @Override
    public boolean esAplicable(AbacContext context) {
        return "PERU".equals(context.getDocumento().getPais());
    }

    @Override
    public PolicyResult evaluar(AbacContext context) {
        boolean mismoPais = context.getUsuario().getPais().equals(context.getDocumento().getPais());

        if (!mismoPais) {
            return new PolicyResult(false, "Documento restringido a operaciones desde Perú");
        }
        return new PolicyResult(true, null);
    }
}