package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ValorSubFormulario;

public interface ValorSubFormularioBo extends GenericBo<ValorSubFormulario>{
	
	Integer obtenerNumeroRegistrosTabla(String idConSubForm, String idForm) throws NotariaException;
	
	List<ValorSubFormulario> findByIdForm(String idForm) throws NotariaException;
	
	void eliminaValorSubFrm(Componente componente) throws NotariaException;

}
