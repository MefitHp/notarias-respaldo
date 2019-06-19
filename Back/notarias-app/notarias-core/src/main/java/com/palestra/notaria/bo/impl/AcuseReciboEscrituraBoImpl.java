package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.AcuseReciboEscrituraBo;
import com.palestra.notaria.dao.AcuseReciboEscrituraDao;
import com.palestra.notaria.dao.impl.AcuseReciboEscrituraDaoImpl;
import com.palestra.notaria.modelo.AcuseReciboEscritura;

public class AcuseReciboEscrituraBoImpl extends
		GenericBoImpl<AcuseReciboEscritura> implements AcuseReciboEscrituraBo {

	private AcuseReciboEscrituraDao acuseDao;
	
	public AcuseReciboEscrituraBoImpl(){
		this.acuseDao = new AcuseReciboEscrituraDaoImpl();
		super.dao = this.acuseDao;
	}
}
