package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteHijo;
import com.palestra.notaria.modelo.ComparecienteRepresentante;

public interface ComparecienteHijoBo extends GenericBo<ComparecienteHijo>{
	
	List<Compareciente> findByComparecienteId(String id) throws NotariaException;

	ComparecienteHijo findBy(Compareciente representado,Compareciente representante) throws NotariaException;

	List<Compareciente> findByEsposaId(String id) throws NotariaException;

	Compareciente calculaComparecientePorIdHijo(String id)
			throws NotariaException;

}
