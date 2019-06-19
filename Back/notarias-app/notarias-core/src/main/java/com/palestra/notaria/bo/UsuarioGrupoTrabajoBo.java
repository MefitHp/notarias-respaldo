package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioGrupoTrabajo;

public interface UsuarioGrupoTrabajoBo extends GenericBo<UsuarioGrupoTrabajo>{

	boolean borrarUsuarioGrupo(String idUsuarioGrupoTrabajo)throws NotariaException;

	List<UsuarioGrupoTrabajo> buscarXgrupo(String string)throws NotariaException;

	List<UsuarioGrupoTrabajo> buscarXresponsable(Usuario responsable)
			throws NotariaException;

	UsuarioGrupoTrabajo buscarXusuario(String idusuario) throws NotariaException;

}
