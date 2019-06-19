package com.palestra.notaria.bo.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.exception.UpdateException;
import org.bonitasoft.engine.platform.LoginException;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.DocumentosOriginalesBo;
import com.palestra.notaria.dao.ActoDocumentoDao;
import com.palestra.notaria.dao.ProcessActoDao;
import com.palestra.notaria.dao.TareaProcessActoDao;
import com.palestra.notaria.dao.impl.ActoDocumentoDaoImpl;
import com.palestra.notaria.dao.impl.ProcessActoDaoImp;
import com.palestra.notaria.dao.impl.TareaProcessActoDaoImpl;
import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.dato.DatoDocumentoTarjeton;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.DocumentosOriginales;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.ProcessActo;
import com.palestra.notaria.modelo.TareaProcessActo;
import com.palestra.notaria.modelo.Usuario;

import pojos.pojos.BonitaCommonBean;
import utiles.procesos.BonitaUtilidades;

public class ActoDocumentoBoImpl extends GenericBoImpl<ActoDocumento> implements ActoDocumentoBo{

	private ActoDocumentoDao actoDocumentoDao;
	
	public ActoDocumentoBoImpl() {
		this.actoDocumentoDao = new ActoDocumentoDaoImpl();
		super.dao = this.actoDocumentoDao;
	}
	
	/*20170521 VICTOR: Se comenta por que se asocia al acto */
	/*@Override 
	public void saveDocProcess(Escritura escritura, DatoActoDocumento act,Usuario usr)throws NotariaException{
		try {
			
			//this.actoDocumentoDao.save(act);
			
			BonitaUtilidades bonitautilidades  = new BonitaUtilidades(usr.getCdusuario(),usr.getCdusuario());
			pojos.pojos.ProcesoComun esc = new pojos.pojos.ProcesoComun();
			pojos.pojos.Impuesto impuesto = new pojos.pojos.Impuesto();
			
			esc.setEscritura(escritura.getDsnumescritura());
			esc.setIdescritura(escritura.getIdescritura());
			esc.setExpediente(escritura.getExpediente().getNumexpediente());
			esc.setIdexpediente(escritura.getExpediente().getIdexpediente());
			esc.setReferencia(escritura.getExpediente().getDsreferencia());
			esc.setIdusuario(usr.getIdusuario());
			esc.setIdsesionactual(usr.getIdsesionactual());

			
			//impuesto.setActonombre(act.getActo().getDsnombre());
			//impuesto.setIdacto(act.getActo().getIdacto());
			impuesto.setEscritura(escritura.getDsnumescritura());
			impuesto.setIdexpediente(escritura.getExpediente().getIdexpediente());
			impuesto.setExpediente(escritura.getExpediente().getNumexpediente());
			impuesto.setDocumentotipo(act.getNombre());
			impuesto.setIdactodoc(act.getIdactodoc());
			impuesto.setIspagoRequire(act.getIspagorequire());
			
			
			try {
				bonitautilidades.bonitaNewImpuesto(esc, impuesto);
			} catch (ProcessDefinitionNotFoundException e) {
				// TODO Auto-generated catch block
				//this.actoDocumentoDao.borrar(act);
				e.printStackTrace();
			} catch (ProcessActivationException e) {
				// TODO Auto-generated catch block
				//this.actoDocumentoDao.borrar(act);
				e.printStackTrace();
			} catch (ProcessExecutionException e) {
				// TODO Auto-generated catch block
				//this.actoDocumentoDao.borrar(act);
				e.printStackTrace();
			}
			
			
		} catch (BonitaHomeNotSetException | ServerAPIException | UnknownAPITypeException | LoginException e) {
			// TODO Auto-generated catch block
			//this.actoDocumentoDao.borrar(act);
			e.printStackTrace();
		}
	}*/

	@Override
	public List<DatoActoDocumento> obtenerPrevios(String idexpediente,
			String idacto) throws NotariaException {
		return this.actoDocumentoDao.obtenerPrevios(idexpediente, idacto);
	}

	@Override
	public List<DatoActoDocumento> obtenerPosteriores(String idexpediente,String idacto) throws NotariaException {
		return this.actoDocumentoDao.obtenerPosteriores(idexpediente, idacto);	
	}

	@Override
	public String buscarArchivoPorId(String iddocumento) throws NotariaException {
		return this.actoDocumentoDao.buscarArchivoPorId(iddocumento);
	}

	@Override
	public boolean actualizarRutaArchivo(String iddocumento, String ruta) throws NotariaException {
		return this.actoDocumentoDao.actualizarRutaArchivo(iddocumento, ruta);
	}
	
	@Override
	public boolean actualizarRutaArchivoFormato(String iddocumento, String ruta) throws NotariaException {
		return this.actoDocumentoDao.actualizarRutaArchivoFormato(iddocumento, ruta);
	}

	@Override
	public boolean marcarDocumentoAprovado(String iddocumento, boolean marcar) throws NotariaException {
		return this.actoDocumentoDao.marcarDocumentoAprovado(iddocumento,marcar);
	}

	@Override
	public boolean marcarDocumentoEntregado(String iddocumento, boolean marcar) throws NotariaException {
		return this.actoDocumentoDao.marcarDocumentoEntregado(iddocumento, marcar);
	}

	@Override
	public String buscarIdExpPorDocumento(String iddocumento) throws NotariaException {
		return this.actoDocumentoDao.buscarIdExpPorDocumento(iddocumento);
	}
	
	@Override
	public ActoDocumento obtenerCompletoPorId(String idactodoc) throws NotariaException {
		return this.actoDocumentoDao.obtenerCompletoPorId(idactodoc);
	}
	
	@Override
	public boolean actualizaTexto(String idactodoc, String txtFormato) throws NotariaException {
		return this.actoDocumentoDao.actualizaTexto(idactodoc, txtFormato);
	}
	
	@Override
	public String obtenerIdTramitePorDocumento(String idactodoc) throws NotariaException {
		return this.actoDocumentoDao.obtenerIdTramitePorDocumento(idactodoc);
	}

	@Override
	public Usuario switchNotario(String idusuario) throws NotariaException{
		return this.actoDocumentoDao.switchNotario(idusuario);
	}
	
	@Override
	public Usuario getNotario()throws NotariaException{
		return this.actoDocumentoDao.getNotario();
	}

	@Override
	public boolean tieneDocActo(String idacto) throws NotariaException {
		return this.actoDocumentoDao.tieneDocActo(idacto);
	}

	@Override
	public void borrar(ActoDocumento actoDoc) throws NotariaException {
		this.actoDocumentoDao.borrar(actoDoc);
		
	}
	
	@Override
	public void startBonitaJudicialesFlow(BonitaCommonBean datos)throws NotariaException {
		ProcessActoDao processActoDao = new ProcessActoDaoImp();
		ProcessActo processActo =  processActoDao.findByActo(new Acto(datos.getIdActo()));
		Map<String, Serializable> mapBonita = new HashMap<String,Serializable>();
		if(processActo == null){
		
			try {
				BonitaUtilidades bonitaUtilidades = new BonitaUtilidades(datos.getUsuario(),datos.getUsuario());
				
				mapBonita.put("procesodato", datos);
				bonitaUtilidades.bonitaNewProcess("Judiciales", "2.1", mapBonita);
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
		}else{
			TareaProcessActoDao tareaProcessActoDao = new TareaProcessActoDaoImpl();
			TareaProcessActo tareaProcessActo = tareaProcessActoDao.getActiveByActo(new Acto(datos.getIdActo()));
			try {
				BonitaUtilidades bu = new BonitaUtilidades("golguin","golguin");
//				bu.bonitaAssignTaskActualSession(tareaProcessActo.getIdtarea());
//				Map<String, Serializable> mapBonita = new HashMap<String,Serializable>();
//				mapBonita.put("procesodato", commonBean);
				mapBonita.put("procesodato", datos);
				bu.bonitaExcecuteTask(tareaProcessActo.getIdtarea(),mapBonita);
				tareaProcessActo.setIsactive(Boolean.FALSE);
				String taskName = bu.getTaskName(Long.valueOf(tareaProcessActo.getIdtarea()));
				tareaProcessActo.setNombretarea(taskName);
				tareaProcessActoDao.update(tareaProcessActo);
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
			}
		}
	}
	
	@Override
	public List<DatoDocumentoTarjeton> obtenerXidActo(Acto acto) throws NotariaException {
		List<DatoDocumentoTarjeton> respuesta = new ArrayList<DatoDocumentoTarjeton>();
		DocumentosOriginalesBo documentosOriginalesBo = new DocumentosOriginalesBoImpl();
		//OBTENGO PREVIOS, POSTERIORES Y ORIGINALES
		List<DocumentosOriginales> documentos = documentosOriginalesBo.obtenerListaOriginales(acto.getIdacto());
		List<DatoActoDocumento> formatos = this.actoDocumentoDao.obtenerXidActo(acto);
		
		for(DatoActoDocumento formato : formatos){
			
			StringBuilder baja = new StringBuilder(boolTotxt(formato.getIsaprobado()));
			StringBuilder entregado = new StringBuilder(boolTotxt(formato.getIsentregado()));
			StringBuilder evidencia = new StringBuilder(boolTotxt(formato.getRutaEvidencia()!=null));

			DatoDocumentoTarjeton datofor = new DatoDocumentoTarjeton();
			
			datofor.setDsnombre(formato.getNombre());
			if(formato.getMesacontrol()!=null){
				datofor.setStatus(formato.getMesacontrol().getEstatusdoc().toString());
				datofor.setFechaactualizacion(formato.getMesacontrol().getUpdated());

			}
			datofor.setEntregado(entregado.toString());
			datofor.setTieneevidencia(evidencia.toString());
			datofor.setBaja(baja.toString());
			respuesta.add(datofor);
		}
		for(DocumentosOriginales doc : documentos){
			DatoDocumentoTarjeton datoori = new DatoDocumentoTarjeton() ;
			StringBuilder baja = new StringBuilder(boolTotxt(doc.getIsvalidado()));
			StringBuilder entregado = new StringBuilder(boolTotxt(doc.getIsentregado()));
			StringBuilder evidencia = new StringBuilder(boolTotxt(doc.getDsruta()!=null));
			datoori.setDsnombre(doc.getDsnombre());
			datoori.setEntregado(entregado.toString());
			datoori.setTieneevidencia(evidencia.toString());
			datoori.setBaja(baja.toString());
			respuesta.add(datoori);
			
		}
		
		return respuesta;
	}
	
	private String boolTotxt(Boolean pregunta){
		if(pregunta != null && pregunta) 
			return "Si";
		else
			return "No";
	}

	@Override
	public List<DatoActoDocumento> obtenerPosteriores(String idexpediente) throws NotariaException {
		List<DatoActoDocumento> respuesta = new ArrayList<DatoActoDocumento>();
		ActoBo actoBo = new ActoBoImpl();
		List<Acto> actos = actoBo.filterActoByIdExpediente(idexpediente);
		
		if(actos.size()>0){
			ActoDocumentoBo actodocbo = new ActoDocumentoBoImpl();
			for(Acto acto:actos){
				List<DatoActoDocumento> actosdocstmp = actodocbo.obtenerPosteriores(idexpediente,acto.getIdacto());
				if(actosdocstmp.size()>0){
					respuesta.addAll(actosdocstmp);
				}
			}
		}
		return respuesta;
	}

	@Override
	public ArrayList<DatoActoDocumento> obtenerDocXnombre(String nombreDoc,String idexpediente,String idacto) throws NotariaException {
		return this.actoDocumentoDao.obtenerDocXnombre(nombreDoc,idexpediente,idacto);
	} 
	
}
