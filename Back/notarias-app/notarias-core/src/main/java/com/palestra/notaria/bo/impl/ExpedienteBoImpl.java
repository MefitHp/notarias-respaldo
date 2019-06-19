package com.palestra.notaria.bo.impl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.platform.LoginException;

import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.dao.ExpedienteDao;
import com.palestra.notaria.dao.impl.ExpedienteDaoImpl;
import com.palestra.notaria.dato.DatoOperacion;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Persona;

import pojos.pojos.BonitaCommonBean;
import utiles.procesos.BonitaUtilidades;

public class ExpedienteBoImpl extends GenericBoImpl<Expediente> implements ExpedienteBo {

	private ExpedienteDao expedienteDao;

	public ExpedienteBoImpl() {
		this.expedienteDao = new ExpedienteDaoImpl();
		
		super.dao = this.expedienteDao;
	}

	@Override
	public Expediente buscarPorIdCompleto(Expediente expediente)throws NotariaException {
		
		return this.expedienteDao.buscarPorIdCompleto(expediente);
	}

	@Override
	public ArrayList<DatoOperacion> buscarOperaciones(String idExpediente, String idOperacion)throws NotariaException {
		try{
			return this.expedienteDao.buscarOperaciones(idExpediente, idOperacion);
			}catch(Exception e){
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public boolean eliminarOperacion(String idExpediente, String idOperacion, String idusuario)throws NotariaException {
		
		return this.expedienteDao.eliminarOperacion(idExpediente, idOperacion, idusuario);
	}

	@Override
	public boolean guardarOperacion(DatoOperacion datoOperacion,String idexpediente, String idsesion, Timestamp tstmp, String idusuario)throws NotariaException {
		return this.expedienteDao.guardarOperacion(datoOperacion, idexpediente, idsesion, tstmp, idusuario);
	}

	@Override
	public boolean actualizarOperacion(DatoOperacion datoOperacion,String idexpediente, String idsesion, Timestamp tstmp, String idusuario)throws NotariaException {
		return this.expedienteDao.actualizarOperacion(datoOperacion, idexpediente, idsesion, tstmp, idusuario);
	}

	@Override
	public boolean esoperacionUnica(String idExpediente, String idOperacion)throws NotariaException {
		return this.expedienteDao.esoperacionUnica(idExpediente, idOperacion);
	}

	@Override
	public String obtenerNumExpedientePorId(String id)throws NotariaException {
		return this.expedienteDao.obtenerNumExpedientePorId(id);
	}
	
	@Override
	public List<Expediente> obtenerExpedientes(Expediente expediente,Compareciente... compareciente) throws NotariaException{
		return this.expedienteDao.obtenerExpedientes(expediente,compareciente);
	}
	
	@Override
	public List<Persona> obtenerClientesDeExpediente() throws NotariaException{
		return this.expedienteDao.obtenerClientesDeExpediente();
	}
	
	@Override
	public String obtenerTramitePorExpedienteId(String idexpediente) throws NotariaException{
		return this.expedienteDao.obtenerTramitePorExpedienteId(idexpediente);
	}
	
	@Override
	public boolean exiteTramiteRegistrado(String idtramite)throws NotariaException {
		return this.expedienteDao.exiteTramiteRegistrado(idtramite);
	}

	@Override
	public List<Expediente> expedientesListar()throws NotariaException{
		return this.expedienteDao.listaExpedientes();
	}

	@Override
	public List<Expediente> obtenerExpedienteXAbogado(String idabogado,String year)
			throws NotariaException {
		return this.expedienteDao.obtenerExpedienteXAbogado(idabogado,year);
	}
	
	@Override
	public List<Expediente> obtenerExpedienteXAbogado(String idabogado)
			throws NotariaException {
		return this.expedienteDao.obtenerExpedienteXAbogado(idabogado);
	}

	@Override
	public void iniciarProcesoOperacion(BonitaCommonBean datos)throws NotariaException {
		
		try {
			BonitaUtilidades bonitaUtilidades = new BonitaUtilidades(datos.getUsuario(),datos.getUsuario());
			Map<String, Serializable> mapBonita = new HashMap<String,Serializable>();
			mapBonita.put("procesodato", datos);
			bonitaUtilidades.bonitaNewProcess("General", "1.0", mapBonita);
		} catch (BonitaHomeNotSetException | ServerAPIException | UnknownAPITypeException | LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ProcessDefinitionNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NotariaException("ERROR EN BPM: No se encontró el proceso definido, consulte a sistemas");
		} catch (ProcessActivationException e) {
			e.printStackTrace();
			throw new NotariaException("ERROR EN BPM:El proceso no está activo, consulte a sistemas");
		} catch (ProcessExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NotariaException("ERROR EN BPM: Algo malo paso al ejecutar el proceso, consulte a sistemas");
		}
		
	}
	
}
