package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ComparecienteHijoBo;
import com.palestra.notaria.dao.ComparecienteHijoDao;
import com.palestra.notaria.dao.impl.ComparecienteHijoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteHijo;

public class ComparecienteHijoBoImpl extends
		GenericBoImpl<ComparecienteHijo> implements
		ComparecienteHijoBo {

	private ComparecienteHijoDao ComparecienteHijoDao;

	public ComparecienteHijoBoImpl() {
		this.ComparecienteHijoDao = new ComparecienteHijoDaoImpl();
		super.dao = this.ComparecienteHijoDao;
	}
	
	@Override
	public List<Compareciente> findByEsposaId(String id) throws NotariaException {
		return this.ComparecienteHijoDao.findByEsposaId(id);
	}

	@Override
	public Compareciente calculaComparecientePorIdHijo(String id) throws NotariaException {
		return this.ComparecienteHijoDao.calculaComparecientePorIdHijo(id);
	}	
	
	@Override
	public ComparecienteHijo findBy(Compareciente compareciente, Compareciente hijo) throws NotariaException{
		return this.ComparecienteHijoDao.findBy(compareciente, hijo);
	}

	@Override
	public List<Compareciente> findByComparecienteId(String id)
			throws NotariaException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
