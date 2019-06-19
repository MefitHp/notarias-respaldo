package com.connect.bo.impl;

import java.util.List;

import com.connect.bo.ConsultaBo;
import com.connect.bo.exception.ConectividadBoException;
import com.connect.dao.ConsultaDao;
import com.connect.dao.exception.ConectividadDaoException;
import com.connect.dao.impl.ConsultaDaoImpl;
import com.connect.modelo.VistaComparecientes;
import com.connect.modelo.VistaExpediente;

public class ConsultaBoImpl implements ConsultaBo {

	@Override
	public List<VistaExpediente> buscarExpedientes(VistaExpediente exp, Integer resultadosPorPagina) throws ConectividadBoException {
		ConsultaDao consultaDao = new ConsultaDaoImpl();
		try {
			List<VistaExpediente> expedientes = consultaDao.buscarExpedientes(exp,resultadosPorPagina);
			for(VistaExpediente expediente:expedientes){
				expediente.setComparecientes(null);
			}
			return expedientes;
		} catch (ConectividadDaoException e) {
			e.printStackTrace(System.out);
			throw new ConectividadBoException("Error en DAO al listar causado por: "+e.getMessage(),e.getCause());
		}
	}
	
	@Override
	public VistaExpediente buscarComparecientesPorExpediente(String expediente) throws ConectividadBoException{
		ConsultaDao consultaDao = new ConsultaDaoImpl();
		try{
			List<VistaExpediente> result = consultaDao.buscarComparecientesPorExpediente(expediente);
			if(result.size()==1){
				VistaExpediente vistaExp = result.get(0);
				for(VistaComparecientes compareciente:vistaExp.getComparecientes()){
					compareciente.setVistaExpediente(null);
				}
				String rutaExt = vistaExp.getRuta();
				
				int lastChar = rutaExt.lastIndexOf("/");
				rutaExt = rutaExt.substring(lastChar+1, rutaExt.length()).replace("+", "_");
				vistaExp.setRutaExt(rutaExt);
				return vistaExp;
			}else if(result.size()>1){
				throw new ConectividadBoException("La búsqueda retornó más de 1 resultado");
			}else{
				throw new ConectividadBoException("No se encontraron resultados");
			}
		}catch (ConectividadDaoException e) {
			e.printStackTrace(System.out);
			throw new ConectividadBoException("Error en DAO al listar causado por: "+e.getMessage(),e.getCause());
		}
	}
	
	@Override
	public Long obtenerTotalRegistrosBusqueda(VistaExpediente exp) throws ConectividadBoException{
		ConsultaDao consultaDao = new ConsultaDaoImpl();
		try {
			return consultaDao.obtenerTotalRegistrosBusqueda(exp);
		} catch (ConectividadDaoException e) {
			e.printStackTrace(System.out);
			throw new ConectividadBoException("Error en DAO al recuperar causado por: "+e.getMessage(),e.getCause());
		}
	}

}
