package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DatoFiscalPago;

public interface DatoFiscalPagoDao extends GenericDao<DatoFiscalPago, Integer>{
	
	public List<DatoFiscalPago> buscarPagadoresAnticipo(String idsolanticipo)throws NotariaException;
	
	public boolean eliminarPagador(DatoFiscalPago dfp, String idusuario, String idexpediente)throws NotariaException;
	
	public Integer obtenerNumeroPagador(String idsolicitud)throws NotariaException;
	
}
