package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.dato.DatoVariableFormulario;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ConSubFormulario;

public interface ComponenteBo extends GenericBo<Componente>{
	
	Componente buscarPorNombreCortoConForm(String nombreCorto, String nombreComponente) throws NotariaException;

	public List<Componente> listarPorSuboperacion(String idsuboperacion) throws NotariaException;
	
	public List<DatoVariableFormulario> listarVariableComponente()throws NotariaException;

	public List<DatoVariableFormulario> listarComponentesSubformulario()throws NotariaException;

	void eliminaComponente(Componente componente,ConFormulario formulario) throws NotariaException;

	void eliminaComponenteSubFrm(Componente componente,
			ConSubFormulario subformulario) throws NotariaException;

	List<Componente> obtenerComponenteXSubformulario(ConSubFormulario objeto) throws NotariaException;
	
}
