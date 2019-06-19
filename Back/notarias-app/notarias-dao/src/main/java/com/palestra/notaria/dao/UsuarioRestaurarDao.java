package com.palestra.notaria.dao;


import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioRestaurar;


public interface UsuarioRestaurarDao extends GenericDao<UsuarioRestaurar, String> {
	UsuarioRestaurar ultimaPeticionActiva(Usuario usuario)throws NotariaException;
}
