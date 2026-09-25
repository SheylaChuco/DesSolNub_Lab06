package com.techcorp.securedocs.security.authorization.abac.policies;

import com.techcorp.securedocs.security.authorization.abac.AbacContext;
import com.techcorp.securedocs.security.authorization.abac.PolicyResult;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class DepartamentoPolicy implements Policy {

    // Administrador y Auditor pueden consultar fuera de su departamento por naturaleza del rol
    private static final Set<String> EXENTOS = Set.of("ADMINISTRADOR", "AUDITOR");

    @Override
    public boolean esAplicable(AbacContext context) {
        return "CONSULTAR".equals(context.getAccion())
                && "EMPLEADO".equals(context.getUsuario().getRol().getNombre());
    }

    @Override
    public PolicyResult evaluar(AbacContext context) {
        boolean mismoDepartamento = context.getUsuario().getDepartamento() != null
                && context.getUsuario().getDepartamento().equals(context.getDocumento().getDepartamento());

        if (!mismoDepartamento) {
            return new PolicyResult(false, "El documento no pertenece a tu departamento");
        }
        return new PolicyResult(true, null);
    }
}