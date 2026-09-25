import { BrowserRouter } from 'react-router-dom';
import Sidebar from './components/layout/Sidebar';
import AppRoutes from './routes/AppRoutes';
import { useAuth } from './context/AuthContext';

function AppShell() {
  const { usuario } = useAuth();

  if (!usuario) {
    return <AppRoutes />; // pantalla de login, sin sidebar
  }

  return (
    <div className="app-shell">
      <Sidebar />
      <main className="main">
        <AppRoutes />
      </main>
    </div>
  );
}

function App() {
  return (
    <BrowserRouter>
      <AppShell />
    </BrowserRouter>
  );
}

export default App;