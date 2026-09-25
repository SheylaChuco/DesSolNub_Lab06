import { useEffect, useState } from 'react';
import { listarUsuarios, crearUsuario, actualizarUsuario } from '../api/usuariosApi';

export default function UsuariosPage() {
  const [usuarios, setUsuarios] = useState([]);
  const [error, setError] = useState('');
  const [mostrarForm, setMostrarForm] = useState(false);
  const [editando, setEditando] = useState(null);

  const cargar = async () => {
    try { setUsuarios(await listarUsuarios()); }
    catch { setError('No se pudo cargar la lista de usuarios'); }
  };

  useEffect(() => { cargar(); }, []);

  return (
    <div>
      <div className="page-header">
        <h1 className="page-title">Usuarios</h1>
        <button className="btn btn-primary" onClick={() => { setEditando(null); setMostrarForm(!mostrarForm); }}>
          {mostrarForm ? 'Cancelar' : '+ Nuevo usuario'}
        </button>
      </div>

      {error && <p className="error-text">{error}</p>}

      {mostrarForm && (
        <div className="panel" style={{ marginBottom: 20 }}>
          <FormularioUsuario usuario={editando} onGuardado={() => { setMostrarForm(false); cargar(); }} />
        </div>
      )}

      <div className="panel" style={{ padding: 0, overflow: 'hidden' }}>
        <table className="data-table">
          <thead>
            <tr><th>Nombre</th><th>Correo</th><th>Rol</th><th>Departamento</th><th>Nivel</th><th>Estado</th><th></th></tr>
          </thead>
          <tbody>
            {usuarios.map((u) => (
              <tr key={u.id}>
                <td>{u.nombre}</td>
                <td style={{ fontFamily: 'var(--font-mono)', fontSize: '0.82rem', color: 'var(--muted)' }}>{u.correo}</td>
                <td>{u.rol?.nombre}</td>
                <td>{u.departamento?.nombre ?? '—'}</td>
                <td>{u.nivelSeguridad}</td>
                <td>{u.estado}</td>
                <td>
                  <button className="btn btn-ghost" onClick={() => { setEditando(u); setMostrarForm(true); }}>Editar</button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
}

function FormularioUsuario({ usuario, onGuardado }) {
  const esEdicion = Boolean(usuario);
  const [nombre, setNombre] = useState(usuario?.nombre ?? '');
  const [correo, setCorreo] = useState(usuario?.correo ?? '');
  const [password, setPassword] = useState('');
  const [rolId, setRolId] = useState(usuario?.rol?.id ?? '');
  const [departamentoId, setDepartamentoId] = useState(usuario?.departamento?.id ?? '');
  const [nivelSeguridad, setNivelSeguridad] = useState(usuario?.nivelSeguridad ?? 1);
  const [pais, setPais] = useState(usuario?.pais ?? 'PERU');
  const [tipoContrato, setTipoContrato] = useState(usuario?.tipoContrato ?? 'INTERNO');
  const [estado, setEstado] = useState(usuario?.estado ?? 'ACTIVO');
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    const dto = { nombre, correo, password, rolId: Number(rolId),
      departamentoId: departamentoId ? Number(departamentoId) : null,
      nivelSeguridad: Number(nivelSeguridad), pais, tipoContrato, estado };
    try {
      if (esEdicion) await actualizarUsuario(usuario.id, dto);
      else await crearUsuario(dto);
      onGuardado();
    } catch (err) {
      setError(err.response?.data?.error || 'No se pudo guardar el usuario');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <div className="field-row">
        <div className="field"><label>Nombre</label><input value={nombre} onChange={(e) => setNombre(e.target.value)} required /></div>
        <div className="field"><label>Correo</label><input type="email" value={correo} onChange={(e) => setCorreo(e.target.value)} required disabled={esEdicion} /></div>
      </div>
      {!esEdicion && (
        <div className="field"><label>Contraseña</label><input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required /></div>
      )}
      <div className="field-row">
        <div className="field"><label>ID Rol</label><input type="number" value={rolId} onChange={(e) => setRolId(e.target.value)} required /></div>
        <div className="field"><label>ID Departamento</label><input type="number" value={departamentoId} onChange={(e) => setDepartamentoId(e.target.value)} /></div>
        <div className="field"><label>Nivel de seguridad</label><input type="number" min="1" max="5" value={nivelSeguridad} onChange={(e) => setNivelSeguridad(e.target.value)} required /></div>
      </div>
      <div className="field">
        <label>Estado</label>
        <select value={estado} onChange={(e) => setEstado(e.target.value)}>
          <option value="ACTIVO">ACTIVO</option>
          <option value="INACTIVO">INACTIVO</option>
          <option value="SUSPENDIDO">SUSPENDIDO</option>
        </select>
      </div>
      {error && <p className="error-text">{error}</p>}
      <button type="submit" className="btn btn-primary">Guardar</button>
    </form>
  );
}