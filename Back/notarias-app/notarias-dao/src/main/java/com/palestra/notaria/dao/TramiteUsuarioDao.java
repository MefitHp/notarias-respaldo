package com.palestra.notaria.dao;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.TramiteUsuario;
import com.palestra.notaria.modelo.Usuario;

public interface TramiteUsuarioDao extends GenericDao<TramiteUsuario, Integer>{
	
	ArrayList<Tramite> findByUsuario(String idusuario) throws NotariaException;
	List<Usuario> findUsuariosByTramite(String idtramite) throws NotariaException;
	TramiteUsuario findByUsuarioTramite(String idusuario, String idTramite)
			throws NotariaException;
	
}
