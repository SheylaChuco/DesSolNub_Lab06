package com.techcorp.securedocs.security.authorization.abac.policies;

import com.techcorp.securedocs.security.authorization.abac.AbacContext;
import com.techcorp.securedocs.security.authorization.abac.PolicyResult;
import org.springframework.stereotype.Component;

@Component
public class InvitadoPolicy implements Policy {

    @Override
    public boolean esAplicable(AbacContext context) {
        return "INVITADO".equals(context.getUsuario().getRol().getNombre());
    }

    @Override
    public PolicyResult evaluar(AbacContext context) {
        boolean esExterno = "EXTERNO".equals(context.getUsuario().getTipoContrato());
        boolean nivelBajo = context.getDocumento().getNivelConfidencialidad() <= 1;
        boolean publicado = "PUBLICADO".equals(context.getDocumento().getEstado());

        if (esExterno && nivelBajo && publicado) {
            return new PolicyResult(true, null);
        }
        return new PolicyResult(false, "Los invitados solo pueden acceder a documentos públicos de bajo nivel");
    }
}