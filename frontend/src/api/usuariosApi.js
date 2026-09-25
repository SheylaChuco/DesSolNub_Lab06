import axiosClient from './axiosClient';

export const listarUsuarios = async () => {
  const response = await axiosClient.get('/usuarios');
  return response.data;
};

export const crearUsuario = async (dto) => {
  const response = await axiosClient.post('/usuarios', dto);
  return response.data;
};

export const actualizarUsuario = async (id, dto) => {
  const response = await axiosClient.put(`/usuarios/${id}`, dto);
  return response.data;
};