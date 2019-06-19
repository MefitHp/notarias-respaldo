package com.palestra.notaria.bo;

import java.util.Date;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.Expediente;

public interface ComentarioBo extends GenericBo<Comentario>{

	List<Comentario> buscarPorObjeto(String id) throws NotariaException;

	List<Comentario> getByDatesAndObject(String id, Date inicio, Date fin) throws NotariaException;
	
	
}
