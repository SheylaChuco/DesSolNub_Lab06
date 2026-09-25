import axios from 'axios';

const axiosClient = axios.create({
  baseURL: 'http://localhost:8081',
});

// Interceptor: agrega el token a cada request automáticamente
axiosClient.interceptors.request.use((config) => {
  const token = localStorage.getItem('token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Interceptor de respuesta: si el token expiró o es inválido (401), cierra sesión
axiosClient.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      localStorage.removeItem('usuario');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export default axiosClient;