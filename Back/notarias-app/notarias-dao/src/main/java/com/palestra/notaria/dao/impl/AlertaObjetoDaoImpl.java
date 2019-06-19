package com.palestra.notaria.dao.impl;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.AlertaObjetoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AlertaObjeto;

public class AlertaObjetoDaoImpl extends GenericDaoImpl<AlertaObjeto,Integer> implements AlertaObjetoDao {

	public AlertaObjetoDaoImpl() {
		super(AlertaObjeto.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public AlertaObjeto findByAlerta(String idalerta) throws NotariaException {
		AlertaObjeto respuesta;
		EntityManager em = factory.createEntityManager();
		TypedQuery<AlertaObjeto> query= em.createNamedQuery("findByAlerta",AlertaObjeto.class);
		query.setParameter("idalerta", idalerta);
		List<AlertaObjeto> objetos = query.getResultList();
		respuesta=(objetos.size()>0)?objetos.get(0):null;
		em.close();
		return respuesta;
	}

	@Override
	public AlertaObjeto findByObjeto(String idalertaobjeto) {
		AlertaObjeto respuesta;
		EntityManager em = factory.createEntityManager();
		TypedQuery<AlertaObjeto> query= em.createNamedQuery("findByObjeto",AlertaObjeto.class);
		query.setParameter("idobjeto", idalertaobjeto);
		List<AlertaObjeto> objetos = query.getResultList();
		respuesta=(objetos.size()>0)?objetos.get(0):null;
		em.close();
		return respuesta;
	}
	
	

	

}
