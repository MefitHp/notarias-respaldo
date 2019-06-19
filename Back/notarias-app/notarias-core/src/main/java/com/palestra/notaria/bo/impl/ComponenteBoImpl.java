package com.palestra.notaria.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.bo.ComponenteBo;
import com.palestra.notaria.bo.ConSubFormularioBo;
import com.palestra.notaria.bo.ValorFormularioBo;
import com.palestra.notaria.bo.ValorSubFormularioBo;
import com.palestra.notaria.dao.ComponenteDao;
import com.palestra.notaria.dao.ConSubFormularioDao;
import com.palestra.notaria.dao.impl.ComponenteDaoImpl;
import com.palestra.notaria.dao.impl.ConSubFormularioDaoImpl;
import com.palestra.notaria.dato.DatoVariableFormulario;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ConSubFormulario;
import com.palestra.notaria.modelo.ValorSubFormulario;

public class ComponenteBoImpl extends GenericBoImpl<Componente> implements ComponenteBo{

	private ComponenteDao componenteDao;
	
	public ComponenteBoImpl() {
		this.componenteDao = new ComponenteDaoImpl();
		super.dao = this.componenteDao;
	}
	
	@Override
	public Componente buscarPorNombreCortoConForm(String nombreCorto,
			String nombreComponente) throws NotariaException{
		return this.componenteDao.buscarPorNombreCortoConForm(nombreCorto, nombreComponente);
	}

	@Override
	public List<Componente> listarPorSuboperacion(String idsuboperacion) throws NotariaException{
		return this.componenteDao.listarPorSuboperacion(idsuboperacion);
	}
	
	@Override
	public List<DatoVariableFormulario> listarVariableComponente() throws NotariaException{
		return this.componenteDao.listarVariableComponente();
	}
	
	@Override
	public List<DatoVariableFormulario> listarComponentesSubformulario()throws NotariaException{
		return this.componenteDao.listarComponentesSubformulario();
	}

	
	@Override
	public void eliminaComponenteSubFrm(Componente componente,ConSubFormulario subformulario) throws NotariaException{
		ValorSubFormularioBo valorSfBo = new ValorSubFormularioBoImpl();
		valorSfBo.eliminaValorSubFrm(componente);
		
	}
	
	@Override
	public void eliminaComponente(Componente componente,ConFormulario formulario) throws NotariaException {
		//localiza el componente en la unidad de persistencia
		//Componente persistido = componenteDao.findById(componente.getIdcomponente());
		//if(persistido!=null){
			//una vez localizado se elimina su valor en formularios, si existe
			
			ValorFormularioBo valorBo = new ValorFormularioBoImpl();
			valorBo.eliminaValorFormulario(componente);
			//ahora se elimina de la configuracion del formulario
			
			/*if(componente.getTipocomponente().getDscodigo().equals("subform")){
				ConSubFormularioBo boConSubForm = new ConSubFormularioBoImpl();
				Integer  posicion= componente.getInposicion();
				ConSubFormulario subfrm = boConSubForm.obtenerPorPosicion(posicion, formulario.getId());
				if(subfrm !=null){
					ArrayList<ConSubFormulario> tmpSubformList = (ArrayList<ConSubFormulario>) formulario.getListaSubFormularios();
					for(ConSubFormulario csf:tmpSubformList){
						if(csf.getIdconsubform().equals(subfrm.getIdconsubform())){
							tmpSubformList.remove(csf);
							break;
						}
					}
					formulario.setListaSubFormularios(tmpSubformList);
					boConSubForm.borrar(subfrm);
				}				
			}*/
			componenteDao.delete(componente);


			
			
		//} else {
		//	System.out.println("=====> Componentes: No se ha localizado el componente " + componente.getIdcomponente() + " en la unidad de persistencia.");
		//}
		
	}

	@Override
	public List<Componente> obtenerComponenteXSubformulario(ConSubFormulario objeto)
			throws NotariaException {
		return componenteDao.obtenerComponenteXSubformulario(objeto);
				
	}
}
