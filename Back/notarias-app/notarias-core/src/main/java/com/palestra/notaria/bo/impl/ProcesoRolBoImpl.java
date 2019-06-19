package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ProcesoRolBo;
import com.palestra.notaria.dao.ProcesoRolDao;
import com.palestra.notaria.dao.impl.ProcesoRolDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ProcesoRol;
import com.palestra.notaria.modelo.Rol;

public class ProcesoRolBoImpl extends GenericBoImpl<ProcesoRol> implements ProcesoRolBo {
	
	private ProcesoRolDao procesoRolDao;

	public ProcesoRolBoImpl(){
		this.procesoRolDao = new ProcesoRolDaoImpl();
		super.dao = this.procesoRolDao;
		
	}
	@Override
	public List<ProcesoRol> listarProcesosPorRol(Rol rol) throws NotariaException {
		return this.procesoRolDao.listarProcesosPorRol(rol);
	}

}
