import axiosClient from './axiosClient';

export const listarDepartamentos = async () => {
  const response = await axiosClient.get('/departamentos');
  return response.data;
};

export const obtenerDocumento = async (id) => {
  const response = await axiosClient.get(`/documentos/${id}`);
  return response.data;
};