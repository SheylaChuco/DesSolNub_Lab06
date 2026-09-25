import { Routes, Route, Navigate } from 'react-router-dom';
import LoginPage from '../pages/LoginPage';
import DocumentosPage from '../pages/DocumentosPage';
import UsuariosPage from '../pages/UsuariosPage';
import AuditoriaPage from '../pages/AuditoriaPage';
import ProtectedRoute from './ProtectedRoute';

export default function AppRoutes() {
  return (
    <Routes>
      <Route path="/login" element={<LoginPage />} />

      <Route
        path="/documentos"
        element={
          <ProtectedRoute>
            <DocumentosPage />
          </ProtectedRoute>
        }
      />

      <Route
        path="/usuarios"
        element={
          <ProtectedRoute rolesPermitidos={['ADMINISTRADOR']}>
            <UsuariosPage />
          </ProtectedRoute>
        }
      />

      <Route
        path="/auditoria"
        element={
          <ProtectedRoute rolesPermitidos={['ADMINISTRADOR', 'GERENTE', 'AUDITOR']}>
            <AuditoriaPage />
          </ProtectedRoute>
        }
      />

      <Route path="*" element={<Navigate to="/documentos" replace />} />
    </Routes>
  );
}