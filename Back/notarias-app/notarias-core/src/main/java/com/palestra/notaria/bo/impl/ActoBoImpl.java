package com.palestra.notaria.bo.impl;

import java.util.List;

import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.exception.UpdateException;
import org.bonitasoft.engine.platform.LoginException;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.dao.ActoDao;
import com.palestra.notaria.dao.ProcessActoDao;
import com.palestra.notaria.dao.TareaProcessActoDao;
import com.palestra.notaria.dao.impl.ActoDaoImpl;
import com.palestra.notaria.dao.impl.ProcessActoDaoImp;
import com.palestra.notaria.dao.impl.TareaProcessActoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ProcessActo;
import com.palestra.notaria.modelo.ProcessActoPk;
import com.palestra.notaria.modelo.TareaProcessActo;

import pojos.pojos.BonitaCommonBean;
import pojos.pojos.TareaBean;
import utiles.procesos.BonitaUtilidades;

public class ActoBoImpl extends GenericBoImpl<Acto> implements ActoBo{

	private ActoDao actoDao;
	
	public ActoBoImpl() {
		this.actoDao = new ActoDaoImpl();
		super.dao = this.actoDao;
	}
	
	@Override
	public List<Acto> filterActoByIdExpediente(String idExpediente) throws NotariaException{
		return actoDao.filterActoByIdExpediente(idExpediente);
	}
	
	@Override
	public String getExpedienteIdByActoId(String idActo) throws NotariaException{
		return this.actoDao.getExpedienteIdByActoId(idActo);
	}
	
	@Override
	public Acto buscarPorIdCompleto(String id) throws NotariaException{
		return this.actoDao.buscarPorIdCompleto(id);
	}
	
	@Override
	public Acto desactivarActo(String idacto) throws NotariaException{
		return this.actoDao.desactivarActo(idacto);
	}
	
	@Override
	public Boolean validaDimAnexo5(String idacto) throws NotariaException{
		
		Acto acto = this.actoDao.findById(idacto);
		
		if(acto.getHasdim() == null){
			throw new NotariaException("El expediente no tiene asignado ningún DIM, solicitarlo en la sección de otorgantes o validar que no tiene");
		}
		if(acto.getHasanexo5() == null ){
			throw new NotariaException("El expediente no tiene asignado ningún Anexo 5, solicitarlo en la sección de otorgantes o validar que no tiene");
		}
		
		return true;
	}

	@Override
	public ProcessActo saveBonitaProcess(BonitaCommonBean bonitaBean) throws NotariaException {
		
		ProcessActoDao procActDao = new ProcessActoDaoImp();
		ProcessActo processActo =  procActDao.findByActo(new Acto(bonitaBean.getIdActo()));
		ProcessActo pActo = new ProcessActo();
//		16/jul/2018 si ya existe proceso se salta esta parte
		if(processActo ==null){
			ProcessActoPk pk = new ProcessActoPk();
			pk.setActo(bonitaBean.getIdActo());
			pk.setIdproceso(bonitaBean.getIdproceso());
			
			pActo.setIdactoproceso(pk);
			
			pActo = procActDao.save(pActo);
		}
			TareaBean tareaBean = new TareaBean();
			tareaBean.setIdacto(bonitaBean.getIdActo());
			tareaBean.setIdproceso(bonitaBean.getIdproceso().toString());
			tareaBean.setIdtarea(bonitaBean.getIdtarea().toString());
			
			try {
				BonitaUtilidades bonitaUtilidades = new BonitaUtilidades(bonitaBean.getUsuario(),bonitaBean.getUsuario());
//				bonitaUtilidades.bonitaAssignTaskActualSession(bonitaBean.getIdtarea());
//				tareaBean.setNombretarea(bonitaUtilidades.getTaskName(bonitaBean.getIdtarea()));
			} catch (BonitaHomeNotSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServerAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnknownAPITypeException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LoginException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			saveBonitaTask(tareaBean);
		
		
		return pActo;
	}

	@Override
	public void saveBonitaTask(TareaBean tareaBean) throws NotariaException {
		TareaProcessActoDao tareaProcessActoDao = new TareaProcessActoDaoImpl();
		TareaProcessActo tempTarea = tareaProcessActoDao.getActiveByActo(new Acto(tareaBean.getIdacto()));
		if(tempTarea!=null){
			tempTarea.setIsactive(Boolean.FALSE);
			tareaProcessActoDao.update(tempTarea);
		}
		TareaProcessActo tareaProcessActo = new TareaProcessActo();
		tareaProcessActo.setIdtarea(Long.valueOf(tareaBean.getIdtarea()));
		tareaProcessActo.setNombretarea(tareaBean.getNombretarea());
		
		ProcessActo processActo = null;
//		processActo.setActo(new Acto(tareaBean.getIdacto()));
//		processActo.setIdproceso(Long.valueOf(tareaBean.getIdproceso()));
		ProcessActoDao processActoDao = new ProcessActoDaoImp();
		processActo = processActoDao.findByActo(new Acto(tareaBean.getIdacto()));
		tareaProcessActo.setProcessActo(processActo);
		tareaProcessActo.setIsactive(Boolean.TRUE);
		tareaProcessActoDao.save(tareaProcessActo);
		try {
			BonitaUtilidades bonitaUtilidades = new BonitaUtilidades("golguin","golguin");
			bonitaUtilidades.bonitaAssignTaskActualSession(Long.valueOf(tareaBean.getIdtarea()));
			bonitaUtilidades.getTaskName(Long.valueOf(tareaBean.getIdtarea()));
		} catch (BonitaHomeNotSetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServerAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownAPITypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UpdateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
