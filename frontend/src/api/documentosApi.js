import axiosClient from './axiosClient';

export const listarDocumentos = async () => {
  const response = await axiosClient.get('/documentos');
  return response.data;
};

export const obtenerDocumento = async (id) => {
  const response = await axiosClient.get(`/documentos/${id}`);
  return response.data;
};

export const crearDocumento = async (dto) => {
  const response = await axiosClient.post('/documentos', dto);
  return response.data;
};

export const actualizarDocumento = async (id, dto) => {
  const response = await axiosClient.put(`/documentos/${id}`, dto);
  return response.data;
};

export const eliminarDocumento = async (id) => {
  await axiosClient.delete(`/documentos/${id}`);
};

export const aprobarDocumento = async (id) => {
  const response = await axiosClient.post(`/documentos/${id}/aprobar`);
  return response.data;
};