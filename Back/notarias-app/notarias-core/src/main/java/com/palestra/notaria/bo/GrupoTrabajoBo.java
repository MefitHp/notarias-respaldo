package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Usuario;

public interface GrupoTrabajoBo extends GenericBo<GrupoTrabajo>{

	boolean delete(String idgrupotrabajo) throws NotariaException;

	List<GrupoTrabajo> buscarXresponsable(Usuario responsable)
			throws NotariaException;

	Usuario obtenerResponsable(Usuario miembro) throws NotariaException;

}
