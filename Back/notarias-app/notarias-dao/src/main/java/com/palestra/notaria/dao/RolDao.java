package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;

public interface RolDao extends GenericDao<Rol, Integer> {

	Rol rolByPrefijo(String prefijo) throws NotariaException;
	

}
