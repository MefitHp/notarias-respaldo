package com.palestra.notaria.bo.impl;

import java.util.Date;
import java.util.List;

import com.palestra.notaria.bo.ReportesBo;
import com.palestra.notaria.dao.ComentarioDao;
import com.palestra.notaria.dao.impl.ComentarioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Comentario;

public class ReportesBoImpl extends GenericBoImpl implements ReportesBo {
	
	ComentarioDao comentarioDao;
	
	@Override
	public List<Comentario> getComentariosPorFechas(String id, Date inicio, Date fin) throws NotariaException {
		comentarioDao = new ComentarioDaoImpl();
		return comentarioDao.getByDatesAndObject(id, inicio, fin);
	}

}
