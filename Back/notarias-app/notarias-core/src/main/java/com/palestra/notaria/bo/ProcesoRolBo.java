package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ProcesoRol;
import com.palestra.notaria.modelo.Rol;

public interface ProcesoRolBo extends GenericBo<ProcesoRol> {
	
	public List<ProcesoRol> listarProcesosPorRol(Rol rol) throws NotariaException;

}
