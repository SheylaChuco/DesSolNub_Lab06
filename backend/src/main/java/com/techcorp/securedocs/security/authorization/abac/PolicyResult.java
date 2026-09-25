package com.techcorp.securedocs.security.authorization.abac;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PolicyResult {
    private boolean permitido;
    private String motivo; // solo se llena cuando permitido = false
}