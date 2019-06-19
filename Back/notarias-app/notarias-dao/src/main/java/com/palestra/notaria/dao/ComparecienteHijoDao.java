package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteHijo;

public interface ComparecienteHijoDao extends GenericDao<ComparecienteHijo, Integer>{
	
	List<Compareciente> findByEsposaId(String id) throws NotariaException;

	ComparecienteHijo findBy(Compareciente compareciente,Compareciente hijo) throws NotariaException;	

	Compareciente calculaComparecientePorIdHijo(String id)
			throws NotariaException;

}
