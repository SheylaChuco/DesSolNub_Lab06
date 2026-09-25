import axiosClient from './axiosClient';

export const listarAuditoria = async () => {
  const response = await axiosClient.get('/auditoria');
  return response.data;
};