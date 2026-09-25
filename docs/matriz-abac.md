# Matriz de Políticas ABAC

| # | Política | Condición | Efecto si falla |
|---|---|---|---|
| 1 | Departamento | `usuario.departamento == documento.departamento` (solo rol EMPLEADO) | Denegado |
| 2 | Nivel de seguridad | `usuario.nivel_seguridad >= documento.nivel_confidencialidad` | Denegado |
| 3 | Propiedad | `usuario.id == documento.propietario` al modificar (excepto GERENTE y ADMINISTRADOR) | Denegado |
| 4 | Horario | Documentos nivel ≥4 solo consultables 08:00–18:00 | Denegado |
| 5 | País | Documentos con país=PERU solo accesibles desde Perú | Denegado |
| 6 | Dispositivo | Documentos nivel ≥4 solo desde dispositivo CORPORATIVO | Denegado |
| 7 | Estado del usuario | `usuario.estado == "ACTIVO"` | Denegado |
| 8 | Invitados | `tipo_contrato == EXTERNO` AND `nivel ≤ 1` AND `estado == PUBLICADO` | Denegado |