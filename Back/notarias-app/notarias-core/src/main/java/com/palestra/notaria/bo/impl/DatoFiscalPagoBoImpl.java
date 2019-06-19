package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.DatoFiscalPagoBo;
import com.palestra.notaria.dao.DatoFiscalPagoDao;
import com.palestra.notaria.dao.impl.DatoFiscalPagoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DatoFiscalPago;

public class DatoFiscalPagoBoImpl extends GenericBoImpl<DatoFiscalPago> implements DatoFiscalPagoBo{

	private DatoFiscalPagoDao datoFiscalPagoDao;
	
	public DatoFiscalPagoBoImpl() {
		this.datoFiscalPagoDao = new DatoFiscalPagoDaoImpl();
		super.dao = this.datoFiscalPagoDao;
	}

	@Override
	public boolean eliminarPagador(DatoFiscalPago dfp, String idusuario,
			String idexpediente) throws NotariaException {
		return this.datoFiscalPagoDao.eliminarPagador(dfp, idusuario, idexpediente);
	}

	@Override
	public Integer obtenerNumeroPagador(String idsolicitud) throws NotariaException {
		return this.datoFiscalPagoDao.obtenerNumeroPagador(idsolicitud);
	}

}
