package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.Usuario;

public interface FormularioBo extends GenericBo<Formulario>{
	
	List<Formulario> findByActoId(String id)throws NotariaException;
	
	public List<Formulario> buscarFormulariosPorActo(String idacto)throws NotariaException;
	
	public boolean guardarValoresFormulario(Formulario formulario, Usuario usuario)throws NotariaException;
	
	public boolean actualizarValoresFormulario(Formulario formulario) throws NotariaException;

	Formulario buscarFormulariosPorActo(String idacto,
			ConFormularioPK idConFormulario)throws NotariaException;

	List<Formulario> findByConFormulario(ConFormularioPK pk)
			throws NotariaException;

	void eliminaValorFormulario(Componente componente) throws NotariaException;

	Formulario buscarFormulariosPorActoYnombrecorto(String idacto, String nombrecorto) throws NotariaException;
	

}
