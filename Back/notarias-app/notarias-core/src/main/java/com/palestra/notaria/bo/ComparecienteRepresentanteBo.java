package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteRepresentante;

public interface ComparecienteRepresentanteBo extends GenericBo<ComparecienteRepresentante>{
	
	List<Compareciente> findByRepresentadoId(String id) throws NotariaException;

	ComparecienteRepresentante findBy(Compareciente representado,Compareciente representante) throws NotariaException;

	Compareciente calculaRepresentadoPorIdRepresentante(String id)
			throws NotariaException;

}
