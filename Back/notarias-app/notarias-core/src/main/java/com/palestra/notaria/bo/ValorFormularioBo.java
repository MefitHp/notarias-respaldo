package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.ValorFormulario;

public interface ValorFormularioBo  extends GenericBo<ValorFormulario>{
	
	List<ValorFormulario> findByIdForm(String idForm) throws NotariaException;


	/***
	 * valorPorNombreVariable recupera el objeto ValorFormulario asociado a un formulario dado y a traves de sus componente se filtra el nombre de la variable
	 * @param formulario com.palestra.notaria.modelo.Formulario el formulario del que se buscarán los valores
	 * @param nombreVariable java.lang.String nombre de la variable que viene desde el token y que existe en la plantilla
	 * @return com.palestra.notaria.modelo.ValorFormulario si existe un valor dentro de la tabla
	 * @throws NotariaException
	 */
	ValorFormulario valorPorNombreVariable(Formulario formulario,
			String nombreVariable) throws NotariaException;


	ValorFormulario valorPorNombreVariableActo(String idacto,
			String nombreVariable) throws NotariaException;


	List<ValorFormulario> getOnlyByComponente(Componente componente)
			throws NotariaException;
	
	void eliminaValorFormulario(Componente componente) throws NotariaException;

}
