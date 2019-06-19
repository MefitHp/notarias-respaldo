package com.palestra.notaria.resource;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.platform.LoginException;

import com.palestra.notaria.bo.MesaCtrlBo;
import com.palestra.notaria.bo.PagoBo;
import com.palestra.notaria.bo.impl.MesaCtrlBoImpl;
import com.palestra.notaria.bo.impl.PagoBoImp;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.CajaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.Pago;
import com.palestra.notaria.util.NotariaUtils;

import utiles.procesos.BonitaUtilidades;

@Path("/caja")
public class CajaRest {
	
	private PagoBo pagoBo;
	
	
	static Logger logger = Logger.getLogger(CajaRest.class);
	
	public  CajaRest() {
		// TODO Auto-generated constructor stub
		pagoBo = new PagoBoImp();
	}

	@POST
	@Path("/listarPagos")
	@Produces(MediaType.APPLICATION_JSON)
	public CajaEnvio obtienePagos(CajaEnvio cajaenvio){
		
		CajaEnvio respuesta = new CajaEnvio();
		MesaCtrlBo mesactrlBo = new MesaCtrlBoImpl();
		
		if (NotariaUtils.faltanRequeridosUsuario(cajaenvio)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(cajaenvio.getUsuario()
				.getIdsesionactual(), cajaenvio.getUsuario()
				.getIdusuario())) {
			
			try {
				List<MesaCtrl> pagos = mesactrlBo.findByEstatusPago(cajaenvio.getPago().getStatuspago());
				respuesta.setPagos(pagos);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;
			} catch (NotariaException e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			
			
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		
		
	}
	
	@POST
	@Path("/cambiastatus")
	@Produces(MediaType.APPLICATION_JSON)
	public CajaEnvio cambia(CajaEnvio cajaenvio){
		
		CajaEnvio respuesta = new CajaEnvio();
		
		if (NotariaUtils.faltanRequeridosUsuario(cajaenvio)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(cajaenvio.getUsuario()
				.getIdsesionactual(), cajaenvio.getUsuario()
				.getIdusuario())) {
			
			try {
				
				Pago pagoenvio = cajaenvio.getPago();
				Pago pago = getPagoBo().findById(pagoenvio.getIdpago());
				pago.setStatuspago(pagoenvio.getStatuspago());
				if(cajaenvio.getIdtarea()!=null && !cajaenvio.getIdtarea().isEmpty()){
					// EJECUTO LA TAREA PENDIENTE
					try {
						BonitaUtilidades butiles = new BonitaUtilidades(cajaenvio.getUsuario().getCdusuario(),cajaenvio.getUsuario().getCdusuario());
						butiles.bonitaExcecuteTask(Long.parseLong(cajaenvio.getIdtarea()));
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
				getPagoBo().update(pago);
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				return respuesta;
		
			} catch (NotariaException e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				respuesta.setExito(false);
				return respuesta;
			}
			
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		
		
	}
	
	public PagoBo getPagoBo() {
		return pagoBo;
	}
	
	
	
	
}
