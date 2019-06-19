package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraUsuario;

public interface BitacoraUsuarioDao extends GenericDao<BitacoraUsuario, Integer> {

	
	List<BitacoraUsuario> listarByGrupo(String idgrupo) throws NotariaException;

	BitacoraUsuario buscarXtarea(String idtarea)throws NotariaException;
}
