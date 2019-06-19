package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.PermisoRol;

public interface PermisoRolDao extends GenericDao<PermisoRol, Integer>{
	PermisoRol findById(String identificador) throws NotariaException;
	List<PermisoRol> findAll(ConFormularioPK idConFormulario) throws NotariaException;
	int removeById(String identificador) throws NotariaException;
	int removeAll(ConFormularioPK idConFormulario) throws NotariaException;
}
