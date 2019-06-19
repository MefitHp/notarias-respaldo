package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.AcuseReciboPersonasBo;
import com.palestra.notaria.dao.AcuseReciboPersonasDao;
import com.palestra.notaria.dao.impl.AcuseReciboPeronasDaoImpl;
import com.palestra.notaria.modelo.AcuseReciboPersonas;

public class AcuseReciboPersonasBoImpl extends
		GenericBoImpl<AcuseReciboPersonas> implements AcuseReciboPersonasBo {

	private AcuseReciboPersonasDao acusePersonasDao;
	
	public AcuseReciboPersonasBoImpl(){
		this.acusePersonasDao = new AcuseReciboPeronasDaoImpl();
		super.dao = this.acusePersonasDao;
	}
}
