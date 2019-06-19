package com.connect.bo;

import java.util.List;

import com.connect.bo.exception.ConectividadBoException;
import com.connect.modelo.VistaExpediente;

public interface ConsultaBo {
	
	public List<VistaExpediente> buscarExpedientes(VistaExpediente exp, Integer resultadosPorPagina) throws ConectividadBoException;

	public VistaExpediente buscarComparecientesPorExpediente(String expediente)throws ConectividadBoException;

	public Long obtenerTotalRegistrosBusqueda(VistaExpediente exp) throws ConectividadBoException;
}
