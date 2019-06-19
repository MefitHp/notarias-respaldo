package com.palestra.notaria.bo.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.exception.UpdateException;
import org.bonitasoft.engine.platform.LoginException;

import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.bo.ValorFormularioBo;
import com.palestra.notaria.dao.ActoDao;
import com.palestra.notaria.dao.FormularioDao;
import com.palestra.notaria.dao.ProcessActoDao;
import com.palestra.notaria.dao.TareaProcessActoDao;
import com.palestra.notaria.dao.impl.ActoDaoImpl;
import com.palestra.notaria.dao.impl.FormularioDaoImpl;
import com.palestra.notaria.dao.impl.ProcessActoDaoImp;
import com.palestra.notaria.dao.impl.TareaProcessActoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.Operacion;
import com.palestra.notaria.modelo.ProcessActo;
import com.palestra.notaria.modelo.TareaProcessActo;
import com.palestra.notaria.modelo.Usuario;

import pojos.pojos.BonitaCommonBean;
import utiles.procesos.BonitaUtilidades;

public class FormularioBoImpl extends GenericBoImpl<Formulario> implements FormularioBo{
	
	private FormularioDao formularioDao;
	private ActoDao actoDao;
	private ProcessActoDao processActoDao;
	private TareaProcessActoDao tareaProcessActoDao;
	
	public FormularioBoImpl(){
		this.actoDao = new ActoDaoImpl();
		this.processActoDao = new ProcessActoDaoImp();
		this.tareaProcessActoDao = new TareaProcessActoDaoImpl();
		this.formularioDao = new FormularioDaoImpl();
		super.dao = this.formularioDao;
	}

	@Override
	public List<Formulario> findByActoId(String id)throws NotariaException {
		return this.formularioDao.findByActoId(id);
	}

	@Override
	public List<Formulario> buscarFormulariosPorActo(String idacto)throws NotariaException {
		return this.formularioDao.buscarFormulariosPorActo(idacto);
	}
	
	@Override
	public Formulario buscarFormulariosPorActoYnombrecorto(String idacto,String nombrecorto)throws NotariaException {
		return this.formularioDao.buscarFormulariosPorActoYnombrecorto(idacto,nombrecorto);
	}
	
	@Override
	public Formulario buscarFormulariosPorActo(String idacto, ConFormularioPK idConFormulario)throws NotariaException {
		return this.formularioDao.buscarFormulariosPorActo(idacto, idConFormulario);
	}	

	@Override
	public boolean guardarValoresFormulario(Formulario formulario, Usuario usuario)throws NotariaException {
		/* TODO SE COMENTA BLOQUE QUE CONECTA CON BONITA */
//		validaDatoFormularioParaProcesoBPM(formulario, usuario);
		return this.formularioDao.guardarValoresFormulario(formulario);
	}

	@Override
	public void eliminaValorFormulario(Componente componente)throws NotariaException {
		ValorFormularioBo valorFormularioBo = new ValorFormularioBoImpl();
		valorFormularioBo.eliminaValorFormulario(componente);
	}
	
	@Override
	public boolean actualizarValoresFormulario(Formulario formulario)throws NotariaException {
		return this.formularioDao.actualizarValoresFormulario(formulario);
	}
	
	@Override
	public List<Formulario> findByConFormulario(ConFormularioPK pk) throws NotariaException{
		return formularioDao.findByConFormulario(pk);
	}

	public void validaDatoFormularioParaProcesoBPM(Formulario formulario, Usuario usuario) throws NotariaException {
		Operacion operacion = actoDao.getOperacionPorActo(formulario.getActo().getIdacto());
		if(operacion.getDsnombre().equalsIgnoreCase("compraventa")){
			//obtener id proceso de actoproceso
			ProcessActo processActo = new ProcessActo();
			processActo = processActoDao.findByActo(formulario.getActo());
			TareaProcessActo tareaProcessActo = tareaProcessActoDao.getActiveByActo(formulario.getActo());
//			TODO: consultar TareaProcessActo por un acto traerá un listado de tareas y cual se va a setear para validar el estatus del proceso?
//			BonitaCommonBean commonBean = new BonitaCommonBean();
//			commonBean.setIdActo(formulario.getActo().getIdacto());
//			commonBean.setIdproceso(processActo.getIdproceso());
//			commonBean.setIdtarea(processActo.getIdtarea());
			try {
				BonitaUtilidades bu = new BonitaUtilidades(usuario.getCdusuario(), usuario.getCdusuario());
				bu.bonitaAssignTaskActualSession(tareaProcessActo.getIdtarea());
//				Map<String, Serializable> mapBonita = new HashMap<String,Serializable>();
//				mapBonita.put("procesodato", commonBean);
				bu.bonitaExcecuteTask(tareaProcessActo.getIdtarea());
			} catch (BonitaHomeNotSetException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (ServerAPIException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (UnknownAPITypeException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (LoginException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (UserTaskNotFoundException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (FlowNodeExecutionException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (ContractViolationException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (UpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
