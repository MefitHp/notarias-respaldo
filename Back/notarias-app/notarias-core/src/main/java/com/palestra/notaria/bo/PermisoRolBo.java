package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.PermisoRol;

public interface PermisoRolBo extends GenericBo<PermisoRol>{
	PermisoRol findById(String identificador) throws NotariaException;
	List<PermisoRol> findAll(ConFormularioPK idConFormulario) throws NotariaException;
	int eliminaTodos(ConFormularioPK idConFormulario) throws NotariaException;
	int elimina(String identificador) throws NotariaException;
}
