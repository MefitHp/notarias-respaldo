package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.PermisoRolBo;
import com.palestra.notaria.dao.PermisoRolDao;
import com.palestra.notaria.dao.impl.PermisoRolDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.PermisoRol;

public class PermisoRolBoImpl extends GenericBoImpl<PermisoRol> implements PermisoRolBo{

	private PermisoRolDao permisoRolDao;
	
	public PermisoRolBoImpl() {
		this.permisoRolDao = new PermisoRolDaoImpl();
		super.dao = this.permisoRolDao;
	}

	@Override
	public PermisoRol update(PermisoRol permisoRol) throws NotariaException {
		PermisoRol actual = findById(permisoRol.getIdpermisorol());
		if(actual!=null){			
			actual.setInpermiso(permisoRol.getInpermiso());
			actual.setRol(permisoRol.getRol());
			actual = update(actual);
			return actual;
		}
		return null;
	}
	
	@Override
	public List<PermisoRol> findAll(ConFormularioPK idConFormulario)
			throws NotariaException {
		return permisoRolDao.findAll(idConFormulario);
	}

	@Override
	public int eliminaTodos(ConFormularioPK idConFormulario)
			throws NotariaException {
		return permisoRolDao.removeAll(idConFormulario);				
	}

	@Override
	public int elimina(String identificador) throws NotariaException {
		return permisoRolDao.removeById(identificador);
	}
	
}
