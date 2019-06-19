package com.connect.dao;

import java.util.List;

import com.connect.dao.exception.ConectividadDaoException;
import com.connect.modelo.VistaExpediente;

public interface ConsultaDao {

	public List<VistaExpediente> listarVistaExpediente() throws ConectividadDaoException;

	public List<VistaExpediente> buscarExpedientes(VistaExpediente exp, Integer resultadosPorPagina) throws ConectividadDaoException;

	public List<VistaExpediente> buscarComparecientesPorExpediente(String expediente)throws ConectividadDaoException;

	public Long obtenerTotalRegistrosBusqueda(VistaExpediente exp) throws ConectividadDaoException;

	int totalPrestados() throws ConectividadDaoException;

}
