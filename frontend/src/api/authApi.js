import axiosClient from './axiosClient';

export const login = async (correo, password) => {
  const response = await axiosClient.post('/auth/login', { correo, password });
  return response.data; // { token, nombre, rol }
};