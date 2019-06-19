package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ComparecienteRepresentanteBo;
import com.palestra.notaria.dao.ComparecienteRepresentanteDao;
import com.palestra.notaria.dao.impl.ComparecienteRepresentanteDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteRepresentante;

public class ComparecienteRepresentanteBoImpl extends
		GenericBoImpl<ComparecienteRepresentante> implements
		ComparecienteRepresentanteBo {

	private ComparecienteRepresentanteDao comparecienteRepresentanteDao;

	public ComparecienteRepresentanteBoImpl() {
		this.comparecienteRepresentanteDao = new ComparecienteRepresentanteDaoImpl();
		super.dao = this.comparecienteRepresentanteDao;
	}
	
	@Override
	public List<Compareciente> findByRepresentadoId(String id) throws NotariaException {
		return this.comparecienteRepresentanteDao.findByRepresentadoId(id);
	}

	@Override
	public Compareciente calculaRepresentadoPorIdRepresentante(String id) throws NotariaException {
		return this.comparecienteRepresentanteDao.calculaRepresentadoPorIdRepresentante(id);
	}	
	
	@Override
	public ComparecienteRepresentante findBy(Compareciente representado, Compareciente representante) throws NotariaException{
		return this.comparecienteRepresentanteDao.findBy(representado, representante);
	}

}
