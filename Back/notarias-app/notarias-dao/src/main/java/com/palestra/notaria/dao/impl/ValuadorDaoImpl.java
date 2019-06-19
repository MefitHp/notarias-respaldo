package com.palestra.notaria.dao.impl;

import java.util.List;

import com.palestra.notaria.dao.ValuadorDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Valuador;

public class ValuadorDaoImpl extends GenericDaoImpl<Valuador, Integer>
		implements ValuadorDao {

	public ValuadorDaoImpl(){
		super(Valuador.class);
	}
	
	@Override
	public List<Valuador> findAll() throws NotariaException {
		return executeQuery("SELECT v FROM Valuador v WHERE v.inestatus = true");
	}
}
