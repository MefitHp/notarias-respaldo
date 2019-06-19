package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.TareaPendiente;
import com.palestra.notaria.modelo.Usuario;

public interface TareaPendienteDao extends GenericDao<TareaPendiente, Integer> {
	
	
	public List<TareaPendiente> obtenerListaCompleta(Usuario usuario) throws NotariaException;
	
	public TareaPendiente buscarPorIdCompleto(TareaPendiente tareaPendiente) throws NotariaException;

}
