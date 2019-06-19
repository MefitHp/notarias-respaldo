package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.SuboperacionBo;
import com.palestra.notaria.dao.SuboperacionDao;
import com.palestra.notaria.dao.impl.SuboperacionDaoImpl;
import com.palestra.notaria.modelo.Suboperacion;

public class SuboperacionBoImpl extends GenericBoImpl<Suboperacion> implements
		SuboperacionBo {
	
	private SuboperacionDao suboperacionDao;
	
	public SuboperacionBoImpl(){
		this.suboperacionDao = new SuboperacionDaoImpl();
		super.dao = this.suboperacionDao;
	}

}
