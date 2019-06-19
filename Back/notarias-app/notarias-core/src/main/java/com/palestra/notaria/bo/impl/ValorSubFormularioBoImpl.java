package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ValorSubFormularioBo;
import com.palestra.notaria.dao.ValorSubFormularioDao;
import com.palestra.notaria.dao.impl.ValorSubFormularioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ValorSubFormulario;

public class ValorSubFormularioBoImpl extends GenericBoImpl<ValorSubFormulario> implements ValorSubFormularioBo{

	private ValorSubFormularioDao valorSubFormularioDao;
	
	public ValorSubFormularioBoImpl() {
		this.valorSubFormularioDao = new ValorSubFormularioDaoImpl();
		super.dao = this.valorSubFormularioDao;
	}
	
	@Override
	public Integer obtenerNumeroRegistrosTabla(String idConSubForm, String idForm) throws NotariaException {
		return this.valorSubFormularioDao.obtenerNumeroRegistrosTabla(idConSubForm, idForm);
	}
	
	@Override
	public List<ValorSubFormulario> findByIdForm(String idForm) throws NotariaException {
		return this.valorSubFormularioDao.findByIdForm(idForm);
	}

	@Override
	public void eliminaValorSubFrm(Componente componente)
			throws NotariaException {
			
		this.valorSubFormularioDao.eliminaValorSubFrm(componente);
		
	}

}
