package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteRepresentante;

public interface ComparecienteRepresentanteDao extends GenericDao<ComparecienteRepresentante, Integer>{
	
	List<Compareciente> findByRepresentadoId(String id) throws NotariaException;

	ComparecienteRepresentante findBy(Compareciente representado,Compareciente representante) throws NotariaException;	

	Compareciente calculaRepresentadoPorIdRepresentante(String id)
			throws NotariaException;

}
