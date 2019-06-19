package com.palestra.notaria.dao.impl;

import java.util.List;

import com.palestra.notaria.dao.ProcesoRolDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ProcesoRol;
import com.palestra.notaria.modelo.Rol;

public class ProcesoRolDaoImpl extends GenericDaoImpl<ProcesoRol, Integer>
		implements ProcesoRolDao {

	public ProcesoRolDaoImpl() {
		super(ProcesoRol.class);
	}
	
	@Override
	public List<ProcesoRol> listarProcesosPorRol(Rol rol) throws NotariaException{
		return executeQuery("SELECT r FROM ProcesoRol r WHERE r.rol = ?1",rol);

	}

}
