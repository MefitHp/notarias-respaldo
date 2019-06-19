package com.palestra.notaria.bo.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.data.DataInstance;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.HumanTaskInstance;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.platform.LoginException;

import com.palestra.notaria.bo.BitacoraUsuarioBo;
import com.palestra.notaria.bo.TareaBpmBo;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraUsuario;

import pojos.pojos.Escritura;
import pojos.pojos.Impuesto;
import pojos.pojos.Tarea;
import utiles.procesos.BonitaUtilidades;

public class TareaBpmBoImp implements TareaBpmBo {

	private BonitaUtilidades bu;
	
	public TareaBpmBoImp(String username,String password) throws NotariaException {
		// TODO Auto-generated constructor stub
		try {
			bu = new BonitaUtilidades(username, password);
		} catch (BonitaHomeNotSetException | ServerAPIException | UnknownAPITypeException | LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NotariaException("Ocurrió un error al obtener la session de Bonita BPM");
		}
	}

	@Override
	public List<Tarea> getTareasAsignadas() throws NotariaException {
		// solicito las tareas asignadas en bonita
		List<HumanTaskInstance> bonasign;
		try {
			bonasign = bu.bonitaGetAssignedTask();
		} catch (BonitaHomeNotSetException | ServerAPIException | UnknownAPITypeException e) {
			e.printStackTrace();
			throw new NotariaException("Ocurrió un error al obtener los datos de Bonita BPM");
		}
		List<Tarea> tareas = new ArrayList<Tarea>();
		// genero el parser de las tareas asignadas a un pojo de tarea
		for(HumanTaskInstance bt: bonasign){
			Tarea t = new Tarea();
			t.setId(bt.getId());
			t.setNombre(bt.getDisplayName());
			t.setSolicidada(bt.getClaimedDate());
			Impuesto imp;
			Escritura esc;
			DataInstance di;
			
			try {
				di=null;
				di = bu.bonitaGetDato(bt.getParentProcessInstanceId(),"escrituradato");
				if(di!=null){
					esc=(Escritura)di.getValue();
					t.setEscritura(esc);
				}
				
			} catch (BonitaHomeNotSetException | ServerAPIException | UnknownAPITypeException e) {
				System.out.println("Algo malo paso al obtener los datos de la tarea:"+bt.getDisplayName());
				
			}
			
			try {
				di=null;
				di = bu.bonitaGetDato(bt.getParentProcessInstanceId(),"impuesto");
				if(di!=null){
					imp = (Impuesto)di.getValue();
					t.setImpuesto(imp);
				}
			} catch (BonitaHomeNotSetException | ServerAPIException | UnknownAPITypeException e) {
				System.out.println("Algo malo paso al obtener los datos de la tarea:"+bt.getDisplayName());
				
			}
			
			try {
				di=null;
				di = bu.bonitaGetDatoTarea(bt.getId(),"expira");
				if(di!=null){
					t.setVencimiento((Date) di.getValue());
				}
			} catch (BonitaHomeNotSetException | ServerAPIException | UnknownAPITypeException e) {
				System.out.println("Algo malo paso al obtener los datos de la tarea:"+bt.getDisplayName());
				
			}
			
			tareas.add(t);
		}
		return tareas;
	}

	@Override
	public void newFlowEscritura(Escritura es) throws NotariaException {
		try {
			bu.bonitaNewProcess(es);
		} catch (ProcessDefinitionNotFoundException | ProcessActivationException | ProcessExecutionException
				| BonitaHomeNotSetException | ServerAPIException | UnknownAPITypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NotariaException("Ocurrió un error al generar un nuevo flujo de Escritura en Bonita BPM");
		}

	}

	@Override
	public void executeTarea(Long idtask,Map<String, Serializable> inputs) throws NotariaException {
		// TODO Auto-generated method stub
		try {
			BitacoraUsuarioBo busuBo = new BitacoraUsuarioBoImpl();
			BitacoraUsuario bitusu = busuBo.buscarXTarea(idtask.toString());
			if(bitusu!=null){
				bitusu.setActive(false);
				bitusu.setTmstmp(new Timestamp(new Date().getTime()));
				busuBo.update(bitusu);
			}
			bu.bonitaExcecuteTask(idtask,inputs);
		} catch (UserTaskNotFoundException | FlowNodeExecutionException | BonitaHomeNotSetException | ServerAPIException
				| UnknownAPITypeException | ContractViolationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NotariaException("Ocurrió un error al ejecutar la tarea en Bonita BPM");

		}
	}



}
