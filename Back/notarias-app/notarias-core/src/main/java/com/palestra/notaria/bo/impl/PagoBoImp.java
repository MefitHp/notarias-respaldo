package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.PagoBo;
import com.palestra.notaria.dao.PagoDao;
import com.palestra.notaria.dao.impl.PagoDaoImp;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Pago;

public class PagoBoImp extends GenericBoImpl<Pago> implements PagoBo {


	private PagoDao pagoDao;

	public PagoBoImp(){
		this.pagoDao = new PagoDaoImp();
		super.dao = this.pagoDao;
	}

	public Pago update(Pago pago) throws NotariaException{
		pago = super.update(pago);
		
		
		return pago;
	}
	
	
	
	
}
