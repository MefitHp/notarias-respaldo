package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Operacion;

public interface ActoDao extends GenericDao<Acto, Integer> {
	
	List<Acto> filterActoByIdExpediente(String idExpediente)throws NotariaException;
	
	String getExpedienteIdByActoId(String idActo) throws NotariaException;
	
	Acto buscarPorIdCompleto(String id) throws NotariaException;

	public Acto desactivarActo(String idacto)throws NotariaException;

	Operacion getOperacionPorActo(String acto) throws NotariaException;

}
