package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ValorFormularioBo;
import com.palestra.notaria.dao.ValorFormularioDao;
import com.palestra.notaria.dao.impl.ValorFormularioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.ValorFormulario;

public class ValorFormularioBoImpl extends GenericBoImpl<ValorFormulario>
		implements ValorFormularioBo {

	private ValorFormularioDao valorFormularioDao;

	public ValorFormularioBoImpl() {
		this.valorFormularioDao = new ValorFormularioDaoImpl();
		super.dao = this.valorFormularioDao;
	}
	
	@Override
	public List<ValorFormulario> findByIdForm(String idForm) throws NotariaException {
		return this.valorFormularioDao.findByIdForm(idForm);
	}

	@Override
	public ValorFormulario valorPorNombreVariable(Formulario formulario, String nombreVariable) throws NotariaException{
		try{
			ValorFormulario valor = this.valorFormularioDao.getByIdComponente(formulario, nombreVariable);
			
			if(valor!=null && valor.getComponente().getDsnombrevariable().equals(nombreVariable)){
				return valor;
			}else{
				return null;
			}
		}catch(NotariaException e){
			throw new NotariaException("No se logro recuperar el valor-formulario desde el id del elemento", e.getCause());
		}
	}
	
	
	@Override
	public ValorFormulario valorPorNombreVariableActo(String idacto, String nombreVariable) throws NotariaException{
		try{
			ValorFormulario valor = this.valorFormularioDao.getByIdComponenteActo(idacto, nombreVariable);
			
			if(valor!=null && valor.getComponente().getDsnombrevariable().equals(nombreVariable)){
				return valor;
			}else{
				return null;
			}
		}catch(NotariaException e){
			throw new NotariaException("No se logro recuperar el valor-formulario desde el id del elemento y id del acto", e.getCause());
		}				
	}
	
	@Override
	public List<ValorFormulario> getOnlyByComponente(Componente componente) throws NotariaException{
		return this.valorFormularioDao.getOnlyByComponente(componente);
	}

	@Override
	public void eliminaValorFormulario(Componente componente)
			throws NotariaException {
		valorFormularioDao.eliminaValorFormulario(componente);
		
	}
}
