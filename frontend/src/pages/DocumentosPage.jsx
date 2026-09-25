import { useEffect, useState } from 'react';
import { useAuth } from '../context/AuthContext';
import {
  listarDocumentos,
  obtenerDocumento,
  crearDocumento,
  eliminarDocumento,
  aprobarDocumento,
} from '../api/documentosApi';
import { listarDepartamentos } from '../api/departamentosApi';
import { puede } from '../utils/permisosUI';
import { nivelLabel } from '../utils/niveles';

export default function DocumentosPage() {
  const { usuario } = useAuth();
  const [documentos, setDocumentos] = useState([]);
  const [error, setError] = useState('');
  const [mostrarForm, setMostrarForm] = useState(false);
  const [detalle, setDetalle] = useState(null); // resultado de "Ver"

  const cargarDocumentos = async () => {
    try {
      setDocumentos(await listarDocumentos());
    } catch {
      setError('No se pudo cargar la lista de documentos');
    }
  };

  useEffect(() => { cargarDocumentos(); }, []);

  const handleVer = async (id) => {
    setError('');
    try {
      const doc = await obtenerDocumento(id);
      setDetalle({ tipo: 'ok', doc });
    } catch (err) {
      setDetalle({ tipo: 'error', mensaje: err.response?.data?.error || 'No se pudo consultar el documento' });
    }
  };

  const handleEliminar = async (id) => {
    setError('');
    try {
      await eliminarDocumento(id);
      cargarDocumentos();
    } catch (err) {
      setError(err.response?.data?.error || 'No se pudo eliminar el documento');
    }
  };

  const handleAprobar = async (id) => {
    setError('');
    try {
      await aprobarDocumento(id);
      cargarDocumentos();
    } catch (err) {
      setError(err.response?.data?.error || 'No se pudo aprobar el documento');
    }
  };

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Documentos</h1>
        {puede(usuario.rol, 'crear') && (
          <button className="btn btn-primary" onClick={() => setMostrarForm(!mostrarForm)}>
            {mostrarForm ? 'Cancelar' : '+ Nuevo documento'}
          </button>
        )}
      </div>

      {error && <p className="error-text">{error}</p>}

      {/* Panel de resultado al consultar un documento */}
      {detalle && (
        <div
          className="panel"
          style={{
            marginBottom: 20,
            borderColor: detalle.tipo === 'error' ? 'var(--danger)' : 'var(--teal)',
          }}
        >
          <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center' }}>
            <strong style={{ color: detalle.tipo === 'error' ? 'var(--danger)' : 'var(--teal)' }}>
              {detalle.tipo === 'error' ? 'Acceso denegado' : 'Acceso permitido'}
            </strong>
            <button className="btn btn-ghost" onClick={() => setDetalle(null)}>Cerrar</button>
          </div>

          {detalle.tipo === 'error' ? (
            <p style={{ marginTop: 8, color: 'var(--muted)' }}>{detalle.mensaje}</p>
          ) : (
            <div style={{ marginTop: 10 }}>
              <p style={{ fontSize: '0.95rem', marginBottom: 4 }}>{detalle.doc.titulo}</p>
              <p style={{ color: 'var(--muted)', fontSize: '0.85rem', marginBottom: 8 }}>
                {detalle.doc.descripcion || 'Sin descripción'}
              </p>
              <div className="doc-meta">
                <span>{detalle.doc.departamento?.nombre}</span>
                <span>{nivelLabel(detalle.doc.nivelConfidencialidad)}</span>
                <span>{detalle.doc.estado}</span>
              </div>
            </div>
          )}
        </div>
      )}

      {mostrarForm && (
        <div className="panel" style={{ marginBottom: 20 }}>
          <FormularioDocumento onCreado={() => { setMostrarForm(false); cargarDocumentos(); }} />
        </div>
      )}

      <div className="panel">
        <div className="doc-list">
          {documentos.map((doc) => (
            <div className="doc-row" key={doc.id}>
              <div className={`level-bar level-${doc.nivelConfidencialidad}`} />
              <div className="doc-info">
                <div className="doc-title">{doc.titulo}</div>
                <div className="doc-meta">
                  <span>#{doc.id}</span>
                  <span>{doc.departamento?.nombre}</span>
                  <span>{nivelLabel(doc.nivelConfidencialidad)}</span>
                  <span className={`status-chip status-${doc.estado?.toLowerCase()}`}>{doc.estado}</span>
                </div>
              </div>
              <div className="doc-actions">
                <button className="btn" onClick={() => handleVer(doc.id)}>Ver</button>
                {puede(usuario.rol, 'aprobar') && doc.estado === 'PENDIENTE' && (
                  <button className="btn" onClick={() => handleAprobar(doc.id)}>Aprobar</button>
                )}
                {puede(usuario.rol, 'eliminar') && (
                  <button className="btn btn-danger" onClick={() => handleEliminar(doc.id)}>Eliminar</button>
                )}
              </div>
            </div>
          ))}
          {documentos.length === 0 && <p style={{ color: 'var(--muted)' }}>No hay documentos para mostrar.</p>}
        </div>
      </div>
    </div>
  );
}

function FormularioDocumento({ onCreado }) {
  const { usuario } = useAuth();
  const [titulo, setTitulo] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [departamentoId, setDepartamentoId] = useState('');
  const [nivelConfidencialidad, setNivelConfidencialidad] = useState(1);
  const [departamentos, setDepartamentos] = useState([]);
  const [error, setError] = useState('');

  // Un EMPLEADO solo puede crear documentos en su propio departamento —
  // no tiene sentido ofrecerle otros en el selector si de todas formas
  // no podría consultarlos después.
  const soloSuPropioDepartamento = usuario.rol === 'EMPLEADO' && usuario.departamentoId;

  useEffect(() => {
    listarDepartamentos().then((data) => {
      const opciones = soloSuPropioDepartamento
        ? data.filter((d) => d.id === usuario.departamentoId)
        : data;
      setDepartamentos(opciones);
      if (opciones.length === 1) setDepartamentoId(String(opciones[0].id));
    });
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await crearDocumento({
        titulo,
        descripcion,
        departamentoId: Number(departamentoId),
        nivelConfidencialidad: Number(nivelConfidencialidad),
        pais: 'PERU',
      });
      onCreado();
    } catch (err) {
      setError(err.response?.data?.error || 'No se pudo crear el documento');
    }
  };

  const nivelSeleccionado = NIVELES_META.find((n) => n.valor === Number(nivelConfidencialidad));

  return (
    <form onSubmit={handleSubmit}>
      <div className="field">
        <label>Título</label>
        <input value={titulo} onChange={(e) => setTitulo(e.target.value)} required />
      </div>
      <div className="field">
        <label>Descripción</label>
        <input value={descripcion} onChange={(e) => setDescripcion(e.target.value)} />
      </div>

      <div className="field-row">
        <div className="field">
          <label>Departamento</label>
          <select
            value={departamentoId}
            onChange={(e) => setDepartamentoId(e.target.value)}
            required
            disabled={soloSuPropioDepartamento}
          >
            <option value="" disabled>Selecciona un departamento</option>
            {departamentos.map((d) => (
              <option key={d.id} value={d.id}>{d.nombre}</option>
            ))}
          </select>
        </div>

        <div className="field">
          <label>Nivel de confidencialidad</label>
          <select value={nivelConfidencialidad} onChange={(e) => setNivelConfidencialidad(e.target.value)} required>
            {NIVELES_META.map((n) => (
              <option key={n.valor} value={n.valor}>{n.label}</option>
            ))}
          </select>
        </div>
      </div>

      {nivelSeleccionado && (
        <p style={{ color: 'var(--muted)', fontSize: '0.8rem', marginTop: -8, marginBottom: 14 }}>
          {nivelSeleccionado.descripcion}
        </p>
      )}

      {error && <p className="error-text">{error}</p>}
      <button type="submit" className="btn btn-primary">Crear</button>
    </form>
  );
}

const NIVELES_META = [
  { valor: 1, label: 'Nivel 1 — Público', descripcion: 'Cualquiera puede consultarlo, incluidos invitados.' },
  { valor: 2, label: 'Nivel 2 — Interno', descripcion: 'Solo personal de la empresa con ese nivel o más.' },
  { valor: 3, label: 'Nivel 3 — Confidencial', descripcion: 'Requiere nivel de seguridad medio-alto.' },
  { valor: 4, label: 'Nivel 4 — Restringido', descripcion: 'Solo en horario laboral y desde dispositivo corporativo.' },
  { valor: 5, label: 'Nivel 5 — Alto secreto', descripcion: 'Máxima restricción, solo personal de más alto nivel.' },
];