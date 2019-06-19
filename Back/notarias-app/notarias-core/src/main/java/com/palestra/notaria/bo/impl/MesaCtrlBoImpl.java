package com.palestra.notaria.bo.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.platform.LoginException;

import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.AlertaObjetoBo;
import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.MesaCtrlBo;
import com.palestra.notaria.bo.PizarronElementoBo;
import com.palestra.notaria.dao.MesaCtrlDao;
import com.palestra.notaria.dao.impl.MesaCtrlDaoImpl;
import com.palestra.notaria.enums.EnumStatusDoc;
import com.palestra.notaria.enums.EnumStatusPago;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.AlertaObjeto;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.Pago;
import com.palestra.notaria.modelo.PizarronElemento;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;
import utiles.procesos.BonitaUtilidades;


public class MesaCtrlBoImpl extends GenericBoImpl<MesaCtrl> implements MesaCtrlBo {

	public final String URL_ALERTS = "http://localhost:8080/alertas/api/v1/alertas";

	MesaCtrlDao mesactrlDao;
	public MesaCtrlBoImpl() {
		mesactrlDao = new MesaCtrlDaoImpl();
		this.dao = mesactrlDao;
	}
	
	@Override
	public List<MesaCtrl> findAll() throws NotariaException{
		List<MesaCtrl> mesas = getMesactrlDao().findAll();
		return mesas;
	}
	
	@Override
	public List<MesaCtrl> findByEstatusPago(EnumStatusPago estatusPago)throws NotariaException{
		List<MesaCtrl> respuesta = getMesactrlDao().findByEstatusPago(estatusPago);
		return respuesta;
		
	}
	
	@Override
	public MesaCtrl findByActoDocumento(String actodocumento)throws NotariaException{
		MesaCtrl respuesta = getMesactrlDao().findByActoDocumento(actodocumento);
		return respuesta;
	}
	
	@Override
	public MesaCtrl findNoPaso(Escritura esc)throws NotariaException{
		MesaCtrl respuesta = getMesactrlDao().findNoPaso(esc);
		return respuesta;
	}
	
	
	@Override
	public MesaCtrl save(MesaCtrl mesa)throws NotariaException{
		
		ActoDocumentoBo actodocBo = new ActoDocumentoBoImpl();
		Pago pago  = new Pago();
		Escritura escritura = null;
		ActoDocumento actodoc = null;
		if(mesa.getEscritura()==null){
			EscrituraActoBo escactoBo = new EscrituraActoBoImpl();
			escritura = new Escritura();
			actodoc = actodocBo.findById(mesa.getActodocumento().getIdactodoc());
			String escrituraId = escactoBo.obtenIdEscrituraPorActoId(actodoc.getActo().getIdacto());
			escritura.setIdescritura(escrituraId);
			//Coloco la mesa
			if(escritura.getIdescritura()==null){
				throw new NotariaException("No se localizó una escritura relacionada al documento posterior");
			}
		}else{
			escritura = mesa.getEscritura();
		}
		
		EscrituraBo escrituraBo = new EscrituraBoImpl();
		escritura = escrituraBo.buscarPorIdCompleto(escritura);
		Expediente expediente = escritura.getExpediente();
		expediente.setTramite(null);
		
		pago.setEscritura(escritura);
		pago.setExpediente(expediente);
		mesa.setEscritura(escritura);
		mesa.setPago(pago);
		
		// Lo conecto al sistema de alertas SOLO PRUEBA URL
		/*String url= "http://localhost:8080/alertas/api/v1/alertas";
		PostGet cr = new PostGet(url);
		AlertaDto alertaDto = new AlertaDto();
		//alertaDto.setTipoobjeto("POSTERIOR");
		alertaDto.setTipoobjeto(actodoc.getFormatoPdf().getTipoalerta());
		JsonNode respuestaJson = cr.getPost(alertaDto);
		alertaDto= (AlertaDto) PostGet.setMapperJsonToPojo(respuestaJson, AlertaDto.class);
		AlertaObjeto alertaObjeto= new AlertaObjeto();
		alertaObjeto.setIdalerta(alertaDto.getId().toString());
		alertaObjeto.setTipoobjeto(alertaDto.getTipoobjeto());
		alertaObjeto.setStatusalerta(alertaDto.getStatusalerta());
		alertaObjeto.setIsfinalizado(alertaDto.getIsfinalizado());
		alertaObjeto.setIdsesion(mesa.getIdsesion());
		alertaObjeto.setIdalertaobjeto(GeneradorId.generaId(alertaObjeto));
		
		/*
		JsonNode jsonNode = cr.getPost(url, ConfiguracionAlerta.usuario);
		ConfiguracionAlerta.usuario = (Usuario) ConfiguracionAlerta.setMapperJsonToPojo(jsonNode, Usuario.class);
		System.out.println("**********************************************************************");
		System.out.println(jsonNode);
		 */
			//Genero solicitud de pago //
		
		/*pago.setExpediente(expediente);
		pago.setEscritura(escritura);
		pago.setActodocumento(mesa.getActodocumento());
		pago.setIdpago(GeneradorId.generaId(pago));
		pago.setIdsesion(mesa.getIdsesion());
		if(mesa.getIspagorequire()){
			pago.setStatuspago(EnumStatusPago.PENDIENTE);
		}else{
			pago.setStatuspago(EnumStatusPago.PAGADO);
		}
		pagoBo.save(pago);
		// Fin de pago
		actodoc.setIssolicitado(true);
		mesa.setPago(pago);
		mesa.setEscritura(escritura);
		mesa.setIdMesaControl(GeneradorId.generaId(mesa));
		getMesactrlDao().save(mesa);
		
		alertaObjeto.setIdobjeto(mesa.getIdMesaControl());
		alertaObjetoBo.save(alertaObjeto);
		
		// esto esta horrible hay que corregirlo
		mesa.setAlerta(alertaObjeto);
		getMesactrlDao().update(mesa);*/
		
		// esto esta horrible hay que corregirlo
		AlertaObjeto alertaObjeto= new AlertaObjeto();
		AlertaObjetoBo alertaObjetoBo = new AlertaObjetoBoImpl();
		alertaObjeto.setTipoobjeto("POSTERIOR");
		alertaObjeto.setStatusalerta("OK");
		alertaObjeto.setIsfinalizado(false);
		alertaObjeto.setIdsesion(mesa.getIdsesion());
		alertaObjeto.setIdalertaobjeto(GeneradorId.generaId(alertaObjeto));
		if(mesa.getActodocumento()!=null){
			alertaObjeto.setIdobjeto(mesa.getActodocumento().getIdactodoc());
		}else{
			alertaObjeto.setIdobjeto(mesa.getEscritura().getIdescritura());
		}

		if(actodoc !=null){
			actodoc.setIssolicitado(true);
			actodocBo.update(actodoc);
		}
		mesa.setActodocumento(actodoc);
		alertaObjetoBo.save(alertaObjeto);
		mesa.setAlerta(alertaObjeto);
		getMesactrlDao().save(mesa);
		
		mesa = noLazy(mesa);
		return mesa;	
	}
	
	@Override 
	public MesaCtrl update (MesaCtrl mesa,Usuario usuario) throws NotariaException{
		
		//AlertaDto alertaDto = new AlertaDto();
		//alertaDto.setId(Long.parseLong(mesa.getAlerta().getIdalerta()));
		
		mesa.setUpdate(new Timestamp(new Date().getTime()));
		if(mesa.getEstatusdoc().toString().equals(EnumStatusDoc.DEVUELTO.toString()) || (mesa.getActodocumento()== null && mesa.getEstatusdoc().toString().equals(EnumStatusDoc.FIRMA.toString())) ){
			//alertaDto =getMesactrlDao().askAlerta(alertaDto, URL_ALERTS+"/termino");
			mesa.setIsterminado(true);
			mesa.setTermino(new Date());
			
			PizarronElementoBo pebo = new PizarronElementoBoImpl();
			PizarronElemento pe = pebo.buscarXescritura(mesa.getEscritura().getDsnumescritura());
			if(pe!=null){
				pe.setStatus("lib-nopaso");
				pebo.update(pe);
			}
			
			// EJECUTO LA TAREA PENDIENTE
			if(mesa.getIdtarea()!=null && !mesa.getIdtarea().isEmpty()){
				try {
					BonitaUtilidades butiles = new BonitaUtilidades(usuario.getCdusuario(),usuario.getCdusuario());
					butiles.bonitaExcecuteTask(Long.parseLong(mesa.getIdtarea()));
					mesa.setIdtarea(null);
				} catch (  BonitaHomeNotSetException | ServerAPIException | UnknownAPITypeException | LoginException e ) {
					// TODO: handle exception
				} catch (UserTaskNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (FlowNodeExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ContractViolationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
			//AlertaObjetoBo aobo= new AlertaObjetoBoImpl();
			//mesa.getAlerta().setStatusalerta(alertaDto.getStatusalerta());
			//aobo.update(mesa.getAlerta());
			AlertaObjetoBo alertabo = new AlertaObjetoBoImpl();
			AlertaObjeto alerta = mesa.getAlerta();
			alerta.setStatusalerta("FINISHED");
			alertabo.update(alerta);
		}
		
		return getMesactrlDao().update(mesa);
	}
	
	@Override
	public void borrar(MesaCtrl mesa)throws NotariaException{
		
			getMesactrlDao().borrar(mesa);
			
	};
	
	
	public MesaCtrlDao getMesactrlDao() {
		return mesactrlDao;
	}
	
	private MesaCtrl noLazy(MesaCtrl mesa){
		if(mesa.getActodocumento()!=null){
			mesa.getActodocumento().setActo(null);
			mesa.getActodocumento().setNotario(null);
			mesa.getActodocumento().getFormatoPdf().setDetalleList(null);
		}
		mesa.getEscritura().setExpediente(null);
		mesa.getEscritura().setLibro(null);
		mesa.getEscritura().setNotario(null);
		return mesa;
	}
	
	private List<MesaCtrl> noLazyMesas(List<MesaCtrl> mesas){
		
		for(MesaCtrl m:mesas){
			m = noLazy(m);
		}
		
		return mesas;
		
	}

	@Override
	public List<MesaCtrl> findByEscritura(Escritura escritura) throws NotariaException {
		return this.mesactrlDao.findByEscritura(escritura);
	}
	
}
