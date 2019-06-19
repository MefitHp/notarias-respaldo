package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioGrupoTrabajo;


public interface UsuarioGrupoTrabajoDao extends GenericDao<UsuarioGrupoTrabajo,Integer> {

	boolean borrarUsuarioGrupo(String idUsuarioGrupoTrabajo)throws NotariaException;

	List<UsuarioGrupoTrabajo> buscarXgrupo(String idgrupo)throws NotariaException;

	UsuarioGrupoTrabajo buscarXusuario(String idusuario)throws NotariaException;	
	
}
