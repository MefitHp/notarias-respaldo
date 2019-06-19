package com.palestra.notaria.dao.impl;

import java.util.List;

import com.palestra.notaria.dao.GestorDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Gestor;

public class GestorDaoImpl extends GenericDaoImpl<Gestor, Integer> implements
		GestorDao {
	
	public GestorDaoImpl(){
		super(Gestor.class);
	}

	@Override
	public List<Gestor> findAll() throws NotariaException {
		return executeQuery("SELECT g FROM Gestor g WHERE g.inestatus = true");
	}
	
	@Override
	public List<Gestor> findByLocacion(ElementoCatalogo elemento)throws NotariaException{
		List<Gestor> gestores = executeQuery("FROM Gestor g WHERE g.locacion = ?1 AND g.inestatus = true", elemento);
		if(gestores!=null){
			return gestores;
		}else{
			return null;
		}
	}
}
