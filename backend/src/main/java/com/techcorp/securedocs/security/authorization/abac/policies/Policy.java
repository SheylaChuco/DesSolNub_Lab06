package com.techcorp.securedocs.security.authorization.abac.policies;

import com.techcorp.securedocs.security.authorization.abac.AbacContext;
import com.techcorp.securedocs.security.authorization.abac.PolicyResult;

public interface Policy {

    boolean esAplicable(AbacContext context);

    PolicyResult evaluar(AbacContext context);
}