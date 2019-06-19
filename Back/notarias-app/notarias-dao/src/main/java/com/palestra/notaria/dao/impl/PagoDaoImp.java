package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.PagoDao;
import com.palestra.notaria.modelo.Pago;

public class PagoDaoImp extends GenericDaoImpl<Pago, Integer> implements PagoDao {

	
	public PagoDaoImp() {
		super(Pago.class);
	}


}
