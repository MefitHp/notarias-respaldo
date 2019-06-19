package com.palestra.notaria.resource;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.BitacoraEscrituraBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.MesaCtrlBo;
import com.palestra.notaria.bo.impl.BitacoraEscrituraBoImp;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.bo.impl.MesaCtrlBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.enums.EnumStatusDoc;
import com.palestra.notaria.envio.MesaCtlEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraEscritura;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;


@Path("/mesacontrol")
public class MesaCtrlRest {

	private MesaCtrlBo mesactrlBo;
	
		
	static Logger logger = Logger.getLogger(MesaCtrlRest.class);
	
	public MesaCtrlRest(){
		mesactrlBo = new MesaCtrlBoImpl();
	}
	
	@POST
	@Path("/obtenermesaid")
	@Produces(MediaType.APPLICATION_JSON)
	public MesaCtlEnvio obtieneMesaCtrlId(MesaCtlEnvio mesaEnvio){
		MesaCtlEnvio respuesta = new MesaCtlEnvio();
		if (mesaEnvio == null || mesaEnvio.getUsuario() == null
				|| mesaEnvio.getUsuario().getIdusuario() == null || mesaEnvio.getUsuario().getIdusuario().isEmpty()
				|| mesaEnvio.getUsuario().getIdsesionactual() == null || mesaEnvio.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(mesaEnvio.getUsuario().getIdsesionactual(), mesaEnvio.getUsuario().getIdusuario())){
			try {
				
				MesaCtrl mesa = getMesaBo().findById(mesaEnvio.getDocumento().getIdmesacontrol());
				respuesta.setDocumento(mesa);
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		
	}
	
	@POST
	@Path("/cancela_nopaso")
	@Produces(MediaType.APPLICATION_JSON)
	public MesaCtlEnvio actualiza_nopaso(MesaCtlEnvio mesaEnvio){
		MesaCtlEnvio respuesta = new MesaCtlEnvio();
		if (mesaEnvio == null || mesaEnvio.getUsuario() == null
				|| mesaEnvio.getUsuario().getIdusuario() == null || mesaEnvio.getUsuario().getIdusuario().isEmpty()
				|| mesaEnvio.getUsuario().getIdsesionactual() == null || mesaEnvio.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(mesaEnvio.getUsuario().getIdsesionactual(), mesaEnvio.getUsuario().getIdusuario())){
			try {
				
				List<MesaCtrl> mesas = getMesaBo().findByEscritura(mesaEnvio.getEscritura());
				BitacoraEscrituraBo bebo = new BitacoraEscrituraBoImp();
				if(mesas!=null || mesas.size()<0){
					MesaCtrl mesa = mesas.get(0);
					if(mesaEnvio.getCancelanopaso()==null){
						bebo.save(false,mesaEnvio.getEscritura(),mesaEnvio.getUsuario(),"Se solicitó la cancelación del NO PASO de la escritura:"+mesaEnvio.getEscritura().getDsnumescritura()+" por el usuario "+mesaEnvio.getUsuario().getDsnombre()+" "+ mesaEnvio.getUsuario().getDspaterno()+" "+ mesaEnvio.getUsuario().getDsmaterno());
						mesa.setEstatusdoc(EnumStatusDoc.CANCELA_NO_PASO);
						mesa.setIdsesion(mesaEnvio.getUsuario().getIdsesionactual());
						this.mesactrlBo.update(mesa);
					}else if(mesaEnvio.getCancelanopaso()==false){
						bebo.save(false,mesaEnvio.getEscritura(), mesaEnvio.getUsuario(), "La cancelación no fué aprobada, contacta a Mesa de Control");
						mesa.setEstatusdoc(EnumStatusDoc.NO_PASO);
						mesa.setIdsesion(mesaEnvio.getUsuario().getIdsesionactual());
						this.mesactrlBo.update(mesa);
						
					}else if(mesaEnvio.getCancelanopaso()==true){
						EscrituraBo escbo = new EscrituraBoImpl();
						Escritura esc = escbo.buscarPorIdCompleto(mesaEnvio.getEscritura());
						if(esc!=null){
								SimpleDateFormat ft = new SimpleDateFormat ("dd.MM.yyyy");
								bebo.save(true,mesaEnvio.getEscritura(),mesaEnvio.getUsuario(),"La cancelación del NO PASO de la escritura:"+mesaEnvio.getEscritura().getDsnumescritura()+" fué aprobada por Mesa de Control el día "+ ft.format(new Date()));
								mesactrlBo.borrar(mesa);
								esc.setNopaso(false);
								escbo.update(esc);
						}else{
							respuesta.setExito(false);
							respuesta.setEstatus("La escritura solicitada no existe");
							return respuesta;
						}
					}
					
				}else{
					respuesta.setEstatus(Constantes.ARCHIVO_NO_ENCONTRADO);
					respuesta.setExito(false);
					return respuesta;
				}
				
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		
	}
	
	@POST
	@Path("/obtenerStatus")
	@Produces(MediaType.APPLICATION_JSON)
	public MesaCtlEnvio obtenerStatusDoc(MesaCtlEnvio mesaenvio){
		 mesaenvio.setEstatusDoc(EnumStatusDoc.values());
		 return mesaenvio;		
	}
	
	@POST
	@Path("/actualizaTareaBo")
	@Produces(MediaType.APPLICATION_JSON)
	public MesaCtlEnvio ActualizaTarea(MesaCtlEnvio mesaEnvio){
		MesaCtlEnvio respuesta = new MesaCtlEnvio();
		MesaCtrl mesactrl = null;
		try {
			mesactrl = getMesaBo().findById(mesaEnvio.getDocumento().getIdmesacontrol());
		} catch (NotariaException e) {
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
		}
		if(mesactrl !=null){
			mesactrl.setIdtarea(mesaEnvio.getDocumento().getIdtarea());
			mesaEnvio.setDocumento(mesactrl);
			respuesta = ActualizaMesaCtrl(mesaEnvio);
			
		}else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
		}
		return respuesta;
	}
	
	@POST
	@Path("/actualizar")
	@Produces(MediaType.APPLICATION_JSON)
	public MesaCtlEnvio ActualizaMesaCtrl(MesaCtlEnvio mesaEnvio){
		MesaCtlEnvio respuesta = new MesaCtlEnvio();
		if (mesaEnvio == null || mesaEnvio.getUsuario() == null
				|| mesaEnvio.getUsuario().getIdusuario() == null || mesaEnvio.getUsuario().getIdusuario().isEmpty()
				|| mesaEnvio.getUsuario().getIdsesionactual() == null || mesaEnvio.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(mesaEnvio.getUsuario().getIdsesionactual(), mesaEnvio.getUsuario().getIdusuario())){
			try {
				MesaCtrl mesa = mesaEnvio.getDocumento();
				
				mesactrlBo.update(mesa,mesaEnvio.getUsuario());
				
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public MesaCtlEnvio obtieneMesaCtrl(MesaCtlEnvio mesaEnvio){
		
		MesaCtlEnvio respuesta = new MesaCtlEnvio();
		if (mesaEnvio == null || mesaEnvio.getUsuario() == null
				|| mesaEnvio.getUsuario().getIdusuario() == null || mesaEnvio.getUsuario().getIdusuario().isEmpty()
				|| mesaEnvio.getUsuario().getIdsesionactual() == null || mesaEnvio.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(mesaEnvio.getUsuario().getIdsesionactual(), mesaEnvio.getUsuario().getIdusuario())){
			try {
				List<MesaCtrl> mesas = getMesaBo().findAll();
				respuesta.setDocumentos(mesas);
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		
	
	}
	
	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	public MesaCtlEnvio saveMesaCtrl(MesaCtlEnvio mesaEnvio){
		
		// lo duermo un segundo para evitar duplicados en los documentos;
		try {
		    Thread.sleep(500);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
		MesaCtlEnvio respuesta = new MesaCtlEnvio();
		if (mesaEnvio == null || mesaEnvio.getUsuario() == null
				|| mesaEnvio.getUsuario().getIdusuario() == null || mesaEnvio.getUsuario().getIdusuario().isEmpty()
				|| mesaEnvio.getUsuario().getIdsesionactual() == null || mesaEnvio.getUsuario().getIdsesionactual().isEmpty() || 
					mesaEnvio.getDocumento()==null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(mesaEnvio.getUsuario().getIdsesionactual(), mesaEnvio.getUsuario().getIdusuario())){
			MesaCtrl mesactrl = mesaEnvio.getDocumento();
			mesactrl.setEstatusdoc(EnumStatusDoc.SOLICITADO);
			mesactrl.setIdsesion(mesaEnvio.getUsuario().getIdsesionactual());
		
			try {
					mesactrl = getMesaBo().save(mesactrl);
					respuesta.setDocumento(mesactrl);
			} catch (NotariaException e) {
				try {
				    Thread.sleep(1000);
				} catch(InterruptedException ex) {
				    Thread.currentThread().interrupt();
				}
				
					try {
						
						mesactrl = getMesaBo().save(mesactrl);
						respuesta.setDocumento(mesactrl);
					} catch (NotariaException ex) {
						e.printStackTrace(System.out);
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO+" "+e.getMessage());
						respuesta.setExito(false);
						return respuesta;
					}
			}
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		
	}
	
	@POST
	@Path("/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public MesaCtlEnvio deleteMesaCtrl(MesaCtlEnvio mesaEnvio){
		MesaCtlEnvio respuesta = new MesaCtlEnvio();
		if (mesaEnvio == null || mesaEnvio.getUsuario() == null
				|| mesaEnvio.getUsuario().getIdusuario() == null || mesaEnvio.getUsuario().getIdusuario().isEmpty()
				|| mesaEnvio.getUsuario().getIdsesionactual() == null || mesaEnvio.getUsuario().getIdsesionactual().isEmpty() || 
					mesaEnvio.getDocumento()==null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(mesaEnvio.getUsuario().getIdsesionactual(), mesaEnvio.getUsuario().getIdusuario())){
			try {
				MesaCtrl mesactrl = mesaEnvio.getDocumento();
				getMesaBo().borrar(mesactrl);
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	
	public MesaCtrlBo getMesaBo() {
		return mesactrlBo;
	}
	
}
