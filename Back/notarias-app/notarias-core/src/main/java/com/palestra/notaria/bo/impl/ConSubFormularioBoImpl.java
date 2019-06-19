package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ComponenteBo;
import com.palestra.notaria.bo.ConSubFormularioBo;
import com.palestra.notaria.dao.ConSubFormularioDao;
import com.palestra.notaria.dao.impl.ConSubFormularioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ConSubFormulario;

public class ConSubFormularioBoImpl extends GenericBoImpl<ConSubFormulario> implements ConSubFormularioBo{

	private ConSubFormularioDao conSubFormularioDao;
	
	public ConSubFormularioBoImpl() {
		this.conSubFormularioDao = new ConSubFormularioDaoImpl();
		super.dao = this.conSubFormularioDao;
	}
	
	
	
	@Override
	public String obtenerIdConSubFormByShortName(String shortName) throws NotariaException {
		return this.conSubFormularioDao.obtenerIdConSubFormByShortName(shortName);
	}
	
	@Override
	public List<ConSubFormulario> obtenerConSubFormsByShortName(String shortName) throws NotariaException {
		return this.conSubFormularioDao.obtenerConSubFormsByShortName(shortName);
	}
	

	@Override
	public String obtenerIdConFormularioByIdSubFormulario(String idSubFormulario) throws NotariaException {
		return conSubFormularioDao.obtenerIdConFormularioByIdSubFormulario(idSubFormulario);
	}

	@Override
	public ConSubFormulario obtenerPorPosicion(Integer posicion,ConFormularioPK conformulario)
			throws NotariaException {
		
		return conSubFormularioDao.obtenerPorPosicion(posicion, conformulario);
	}
	
	@Override
	public void borrar(ConSubFormulario objeto) throws NotariaException{
		try {
			
			ComponenteBo cbo = new ComponenteBoImpl();
			
			List<Componente>componentesBorrar = cbo.obtenerComponenteXSubformulario(objeto);
			for(Componente componente : componentesBorrar){
				cbo.eliminaComponenteSubFrm(componente, objeto);
			}
			
			
			conSubFormularioDao.delete(objeto);
		} catch (NotariaException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}



	@Override
	public List<ConSubFormulario> findByFormulario(ConFormularioPK formulario) throws NotariaException  {
		List<ConSubFormulario> listaSubfrm = conSubFormularioDao.findByFormulario(formulario);
		return listaSubfrm;
	}
	
	
	
}
