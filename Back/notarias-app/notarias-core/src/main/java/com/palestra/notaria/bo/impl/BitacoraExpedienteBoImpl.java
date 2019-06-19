package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.BitacoraExpedienteBo;
import com.palestra.notaria.dao.BitacoraExpedienteDao;
import com.palestra.notaria.dao.impl.BitacoraExpedienteDaoImpl;
import com.palestra.notaria.modelo.BitacoraExpediente;

public class BitacoraExpedienteBoImpl extends GenericBoImpl<BitacoraExpediente> implements BitacoraExpedienteBo{

	private BitacoraExpedienteDao bitacoraExpedienteDao;
	public BitacoraExpedienteBoImpl() {
		this.bitacoraExpedienteDao = new BitacoraExpedienteDaoImpl();
		super.dao = this.bitacoraExpedienteDao;
	}
}
