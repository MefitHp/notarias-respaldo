package com.palestra.notaria.bo;

import java.util.Date;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Comentario;

public interface ReportesBo extends GenericBo {

	public List<Comentario> getComentariosPorFechas(String id, Date inicio, Date fin) throws NotariaException;
}
