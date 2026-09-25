const PERMISOS_POR_ROL = {
  ADMINISTRADOR: ['crear', 'modificar', 'eliminar', 'aprobar'],
  GERENTE: ['crear', 'modificar', 'eliminar', 'aprobar'],
  SUPERVISOR: ['crear', 'modificar', 'aprobar'],
  EMPLEADO: ['crear', 'modificar'],
  AUDITOR: [],
  INVITADO: [],
};

export function puede(rol, accion) {
  return PERMISOS_POR_ROL[rol]?.includes(accion) ?? false;
}