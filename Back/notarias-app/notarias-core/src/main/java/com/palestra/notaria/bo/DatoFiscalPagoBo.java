package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DatoFiscalPago;

public interface DatoFiscalPagoBo extends GenericBo<DatoFiscalPago>{

	public boolean eliminarPagador(DatoFiscalPago dfp, String idusuario, String idexpediente)throws NotariaException;
	
	public Integer obtenerNumeroPagador(String idsolicitud)throws NotariaException;
	
}
