export const NIVELES_CONFIDENCIALIDAD = [
  { valor: 1, label: 'Nivel 1 — Público', descripcion: 'Cualquiera puede consultarlo' },
  { valor: 2, label: 'Nivel 2 — Interno', descripcion: 'Solo personal de la empresa' },
  { valor: 3, label: 'Nivel 3 — Confidencial', descripcion: 'Requiere nivel de seguridad medio' },
  { valor: 4, label: 'Nivel 4 — Restringido', descripcion: 'Solo en horario laboral y dispositivo corporativo' },
  { valor: 5, label: 'Nivel 5 — Alto secreto', descripcion: 'Máxima restricción' },
];

export function nivelLabel(valor) {
  return NIVELES_CONFIDENCIALIDAD.find((n) => n.valor === valor)?.label ?? `Nivel ${valor}`;
}
