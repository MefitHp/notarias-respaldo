package com.palestra.notaria.dao;

import java.util.Date;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Comentario;

public interface ComentarioDao extends GenericDao<Comentario, Integer> {
	
	List<Comentario> buscarPorObjeto(String id)throws NotariaException;

	List<Comentario> getByDatesAndObject(String id, Date inicio, Date fin) throws NotariaException;

}
