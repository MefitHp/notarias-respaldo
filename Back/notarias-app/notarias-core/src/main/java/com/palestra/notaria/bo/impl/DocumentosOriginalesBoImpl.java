package com.palestra.notaria.bo.impl;

import java.util.List;

import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.platform.LoginException;

import com.palestra.notaria.bo.ComentarioBo;
import com.palestra.notaria.bo.DocumentosOriginalesBo;
import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.dao.ActoDao;
import com.palestra.notaria.dao.DocumentosOriginalesDao;
import com.palestra.notaria.dao.TareaProcessActoDao;
import com.palestra.notaria.dao.TramiteDao;
import com.palestra.notaria.dao.impl.ActoDaoImpl;
import com.palestra.notaria.dao.impl.DocumentosOriginalesDaoImpl;
import com.palestra.notaria.dao.impl.TareaProcessActoDaoImpl;
import com.palestra.notaria.dao.impl.TramiteDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.DocumentosOriginales;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.TareaProcessActo;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.ValorFormulario;
import com.palestra.notaria.util.GeneradorId;

import utiles.procesos.BonitaUtilidades;
import utiles.utilidades.victor.BotMail;

public class DocumentosOriginalesBoImpl extends GenericBoImpl<DocumentosOriginales> implements DocumentosOriginalesBo{
	
	private DocumentosOriginalesDao documentosOriginalesDao;
	
	public DocumentosOriginalesBoImpl() {
		this.documentosOriginalesDao = new DocumentosOriginalesDaoImpl();
		super.dao = this.documentosOriginalesDao;
	}

	@Override
	public List<DocumentosOriginales> obtenerListaOriginales(String idacto)throws NotariaException {
		return this.documentosOriginalesDao.obtenerListaOriginales(idacto);
	}

	@Override
	public String buscarArchivoPorId(String idDocumento)throws NotariaException {
		return this.documentosOriginalesDao.buscarArchivoPorId(idDocumento);
	}

	@Override
	public boolean actualizarRutaArchivoOriginal(String idevidencia,
			String ruta)throws NotariaException {
		return this.documentosOriginalesDao.actualizarRutaArchivoOriginal(idevidencia, ruta);
	}
	
	@Override
	public String obtenerTramitePorOriginal(String iddoccor)throws NotariaException {
		return this.documentosOriginalesDao.obtenerTramitePorOriginal(iddoccor);
	}
	
	@Override
	public void borrar(String iddocori) throws NotariaException{
		this.documentosOriginalesDao.borrar(iddocori);
	}
	
	@Override
	public void validaEnvioExpJudiciales(DocumentosOriginales doc, Usuario usuario)throws NotariaException{
		ActoDao actoDao =new ActoDaoImpl();
		TramiteDao tramiteDao = new TramiteDaoImpl();
		FormularioBo formularioBo = new FormularioBoImpl();
		Acto acto = actoDao.buscarPorIdCompleto(doc.getActo().getIdacto());
		String idexpediente = actoDao.getExpedienteIdByActoId(doc.getActo().getIdacto());
		Expediente exp = new Expediente();
		exp.setIdexpediente(idexpediente);
		Tramite tramite = tramiteDao.findByExpediente(exp);
		List<Formulario> formularios = formularioBo
				.buscarFormulariosPorActo(acto.getIdacto());
		String expJudicial = null;
		String demandado = null;
		String cliente = tramite.getCliente().getDsnombrecompleto();
		StringBuilder mensaje = new StringBuilder();
		for(Formulario form:formularios){
			if(form.getConFormulario().getDsnombrecorto().equals("judicial")){
				for(ValorFormulario valor:form.getListaValFormulario()){
					if(valor.getComponente().getDsnombrevariable().equals("demandado")){
						demandado = valor.getValorcadena();
					}
					if(valor.getComponente().getDsnombrevariable().equals("expediente")){
						expJudicial=valor.getValorcadena();
					}
				}
			}
		}
		mensaje.append("Se generó una actualización en el expediente "+expJudicial);
		mensaje.append("\n \n Cliente: "+cliente);
		mensaje.append("\n \n Demandado: "+demandado);
		mensaje.append("\n \n Mensaje: "+doc.getDsnombre());
		mensaje.append("\n \n \n \n Este es un mensaje automático del sistema, por favor no responda a este correo");
		
		
		BotMail.enviaMail("Actualizacion de Exp Judicial: "+expJudicial, mensaje.toString(), "omar_tablas@hotmail.com");
		doc.getDsnombre();
		
		// EJECUTAR TAREA BONITA
		TareaProcessActoDao tareaProcessActoDao = new TareaProcessActoDaoImpl();
		TareaProcessActo tareaProcessActo = tareaProcessActoDao.getActiveByActo(new Acto(acto.getIdacto()));
//		TODO: GUARDAR COMENTARIO AQUI
		Comentario comentario = new Comentario();
		comentario.setDstexto("Se generó una actualización en el expediente: "+doc.getDsnombre());
		comentario.setIdobjeto(idexpediente);
		comentario.setIdcomentario(GeneradorId.generaId(idexpediente));
		comentario.setIdsesion(usuario.getIdsesionactual());
		comentario.setUsuario(usuario);
		ComentarioBo comentBo = new ComentarioBoImpl();
		comentBo.save(comentario);
		try {
			BonitaUtilidades bu = new BonitaUtilidades("golguin","golguin");
//			bu.bonitaAssignTaskActualSession(tareaProcessActo.getIdtarea());
//			Map<String, Serializable> mapBonita = new HashMap<String,Serializable>();
//			mapBonita.put("procesodato", commonBean);
			bu.bonitaExcecuteTask(tareaProcessActo.getIdtarea());
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
	
	public static void main(String[]args) throws NotariaException{
		DocumentosOriginales doc = new DocumentosOriginales();
		doc.setActo(new Acto("ae221e0f266a41567b85db444287dca3"));
		ActoDao actoDao =new ActoDaoImpl();
		TramiteDao tramiteDao = new TramiteDaoImpl();
		Acto acto = actoDao.buscarPorIdCompleto(doc.getActo().getIdacto());
		System.out.println(doc.getDsnombre());
	}

}
