package com.palestra.notaria.bo.impl;

import java.util.Date;
import java.util.List;

import com.palestra.notaria.bo.BitacoraUsuarioBo;
import com.palestra.notaria.bo.ComentarioBo;
import com.palestra.notaria.dao.ComentarioDao;
import com.palestra.notaria.dao.impl.ComentarioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraUsuario;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.util.GeneradorId;

public class ComentarioBoImpl extends GenericBoImpl<Comentario> implements ComentarioBo{

	private ComentarioDao comentarioDao;
	
	public ComentarioBoImpl() {
		this.comentarioDao = new ComentarioDaoImpl();
		super.dao = this.comentarioDao;
	}
	
	@Override
	public List<Comentario> buscarPorObjeto(String id)throws NotariaException{
		return comentarioDao.buscarPorObjeto(id);
	}
	
	@Override
	public List<Comentario> getByDatesAndObject(String id, Date inicio, Date fin) throws NotariaException{
		return comentarioDao.getByDatesAndObject(id, inicio, fin);
	}

}
