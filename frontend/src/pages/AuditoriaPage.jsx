import { useEffect, useState } from 'react';
import { listarAuditoria } from '../api/auditoriaApi';

export default function AuditoriaPage() {
  const [registros, setRegistros] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    listarAuditoria().then(setRegistros).catch(() => setError('No se pudo cargar la auditoría'));
  }, []);

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Auditoría</h1>
      </div>

      {error && <p className="error-text">{error}</p>}

      <div className="panel" style={{ padding: 0, overflow: 'hidden' }}>
        <table className="data-table">
          <thead>
            <tr><th>Fecha</th><th>Usuario</th><th>Recurso</th><th>Acción</th><th>Resultado</th><th>Motivo</th></tr>
          </thead>
          <tbody>
            {registros.map((r) => (
              <tr key={r.id} className={r.resultado === 'DENEGADO' ? 'row-denegado' : 'row-permitido'}>
                <td style={{ fontFamily: 'var(--font-mono)', fontSize: '0.78rem', color: 'var(--muted)' }}>{r.fecha}</td>
                <td>{r.usuario}</td>
                <td>{r.recurso}</td>
                <td>{r.accion}</td>
                <td>{r.resultado}</td>
                <td style={{ color: 'var(--muted)' }}>{r.motivo ?? '—'}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}