package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ManejoSesion;

public interface ManejoSesionDao extends GenericDao<ManejoSesion, Integer> {
	
	public ManejoSesion findBySesionAndUser(String idsesion, String... idusuario) throws NotariaException;

	int totalSesiones() throws NotariaException;

	List<ManejoSesion> findSesionByUser(String idusuario)
			throws NotariaException;

	boolean borrar(ManejoSesion ms) throws NotariaException;

}
