package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.ValorFormulario;

public interface ValorFormularioDao extends
		GenericDao<ValorFormulario, Integer> {
	
	List<ValorFormulario> findByIdForm(String idForm) throws NotariaException;

	ValorFormulario getByIdComponente(Formulario formulario, String idcomponente)
			throws NotariaException;

	ValorFormulario getByIdComponenteActo(String idacto, String nombreVariable)
			throws NotariaException;

	List<ValorFormulario> getOnlyByComponente(Componente componente)
			throws NotariaException;

	void eliminaValorFormulario(Componente componente) throws NotariaException;
	
}
