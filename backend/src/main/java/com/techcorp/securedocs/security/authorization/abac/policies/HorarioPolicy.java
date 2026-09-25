package com.techcorp.securedocs.security.authorization.abac.policies;

import com.techcorp.securedocs.security.authorization.abac.AbacContext;
import com.techcorp.securedocs.security.authorization.abac.PolicyResult;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class HorarioPolicy implements Policy {

    private static final LocalTime INICIO = LocalTime.of(8, 0);
    private static final LocalTime FIN = LocalTime.of(18, 0);

    @Override
    public boolean esAplicable(AbacContext context) {
        return "CONSULTAR".equals(context.getAccion())
                && context.getDocumento().getNivelConfidencialidad() >= 4;
    }

    @Override
    public PolicyResult evaluar(AbacContext context) {
        LocalTime hora = context.getHora();

        if (hora.isBefore(INICIO) || hora.isAfter(FIN)) {
            return new PolicyResult(false, "Fuera del horario autorizado para documentos altamente confidenciales");
        }
        return new PolicyResult(true, null);
    }
}