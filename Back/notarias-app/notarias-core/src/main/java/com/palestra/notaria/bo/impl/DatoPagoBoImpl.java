package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.DatoPagoBo;
import com.palestra.notaria.dao.DatoPagoDao;
import com.palestra.notaria.dao.impl.DatoPagoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DatoPago;
import com.palestra.notaria.modelo.ElementoCatalogo;

public class DatoPagoBoImpl extends GenericBoImpl<DatoPago> implements DatoPagoBo{

	private DatoPagoDao datoPagoDao;
	
	public DatoPagoBoImpl() {
		this.datoPagoDao = new DatoPagoDaoImpl();
		super.dao = this.datoPagoDao;
	}
	
	@Override
	public Boolean guardarDatoPago(DatoPago datoPago, String idDatoFisca,
			ElementoCatalogo status) throws NotariaException{
		return this.datoPagoDao.guardarDatoPago(datoPago, idDatoFisca, status);
	}
	
	@Override
	public Double sumaImportePagoPorSoliciutd(String idSolPago)throws NotariaException {
		return this.datoPagoDao.sumaImportePagoPorSoliciutd(idSolPago);
	}

}
