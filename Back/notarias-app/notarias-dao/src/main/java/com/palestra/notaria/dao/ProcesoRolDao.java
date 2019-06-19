package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ProcesoRol;
import com.palestra.notaria.modelo.Rol;

public interface ProcesoRolDao extends GenericDao<ProcesoRol, Integer> {
	
	public List<ProcesoRol> listarProcesosPorRol(Rol rol) throws NotariaException;
}
