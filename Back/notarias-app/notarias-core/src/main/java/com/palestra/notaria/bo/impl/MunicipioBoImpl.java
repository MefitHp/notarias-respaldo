package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.MunicipioBo;
import com.palestra.notaria.dao.MunicipioDao;
import com.palestra.notaria.dao.impl.MunicipioDaoImpl;
import com.palestra.notaria.modelo.Municipio;

public class MunicipioBoImpl extends GenericBoImpl<Municipio> implements MunicipioBo{
	
	private MunicipioDao municipioDao;
	
	public MunicipioBoImpl(){
		this.municipioDao = new MunicipioDaoImpl();
		super.dao = this.municipioDao;
	}

}