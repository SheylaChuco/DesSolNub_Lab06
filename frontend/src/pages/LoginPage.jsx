import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { login } from '../api/authApi';
import { useAuth } from '../context/AuthContext';

export default function LoginPage() {
  const [correo, setCorreo] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [cargando, setCargando] = useState(false);

  const { iniciarSesion } = useAuth();
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setCargando(true);
    try {
      const data = await login(correo, password);
      iniciarSesion(data.token, {
       nombre: data.nombre,
       rol: data.rol,
       departamentoId: data.departamentoId,
       departamentoNombre: data.departamentoNombre,
      });
      navigate('/documentos');
    } catch (err) {
      setError(err.response?.data?.error || 'Credenciales inválidas');
    } finally {
      setCargando(false);
    }
  };

  return (
    <div className="login-screen">
      <div className="login-panel">
        <div className="login-brand">
          <span className="mark" />
          SecureDocs
        </div>
        <p className="login-sub">Control de acceso RBAC + ABAC</p>

        <form onSubmit={handleSubmit}>
          <div className="field">
            <label>Correo</label>
            <input type="email" value={correo} onChange={(e) => setCorreo(e.target.value)} required />
          </div>
          <div className="field">
            <label>Contraseña</label>
            <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
          </div>

          {error && <p className="error-text">{error}</p>}

          <button type="submit" className="btn btn-primary" disabled={cargando} style={{ width: '100%' }}>
            {cargando ? 'Ingresando...' : 'Ingresar'}
          </button>
        </form>
      </div>
    </div>
  );
}