import { NavLink } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';

export default function Sidebar() {
  const { usuario, cerrarSesion } = useAuth();
  if (!usuario) return null;

  const puedeVerUsuarios = usuario.rol === 'ADMINISTRADOR';
  const puedeVerAuditoria = ['ADMINISTRADOR', 'GERENTE', 'AUDITOR'].includes(usuario.rol);

  return (
    <aside className="sidebar">
      <div className="sidebar-brand">
        <span className="mark" />
        SecureDocs
      </div>

      <nav className="sidebar-nav">
        <NavLink to="/documentos" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
          Documentos
        </NavLink>
        {puedeVerUsuarios && (
          <NavLink to="/usuarios" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
            Usuarios
          </NavLink>
        )}
        {puedeVerAuditoria && (
          <NavLink to="/auditoria" className={({ isActive }) => `sidebar-link${isActive ? ' active' : ''}`}>
            Auditoría
          </NavLink>
        )}
      </nav>

      <div className="sidebar-footer">
        <div className="role-badge">{usuario.rol}</div>
        <div className="user-name">{usuario.nombre}</div>
        <button className="btn btn-ghost" onClick={cerrarSesion} style={{ width: '100%' }}>
          Cerrar sesión
        </button>
      </div>
    </aside>
  );
}