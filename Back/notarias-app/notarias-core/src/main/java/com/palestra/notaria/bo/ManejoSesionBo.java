package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ManejoSesion;

public interface ManejoSesionBo extends GenericBo<ManejoSesion> {
	
	public ManejoSesion findBySesionAndUser(String idsesion, String... idusuario) throws NotariaException;

	int totalSesiones() throws NotariaException;

	public List<ManejoSesion> findSesionByUser(String idusuario)throws NotariaException;

	boolean borrar(ManejoSesion t) throws NotariaException;

	

}
