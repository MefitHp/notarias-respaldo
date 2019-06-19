package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ImpuestoBo;
import com.palestra.notaria.bo.PresupuestoBo;
import com.palestra.notaria.bo.impl.ImpuestoBoImpl;
import com.palestra.notaria.bo.impl.PresupuestoBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoPresupuesto;
import com.palestra.notaria.envio.PresupuestoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Impuesto;
import com.palestra.notaria.modelo.Presupuesto;
import com.palestra.notaria.util.ErrorCodigoMensaje;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/presupuesto")
public class PresupuestoRest {
	
	static Logger logger = Logger.getLogger(GestionDocumentosRest.class);
//	private BitacoraGeneralHelper bitacoraGeneralHelper = new BitacoraGeneralHelper();
	private PresupuestoBo presupuestoBo = new PresupuestoBoImpl();
	private ImpuestoBo impuestoBo = new ImpuestoBoImpl();
	
	@POST
	@Path("/obtenerPresupuestosPorExpediente")
	@Produces({ "application/json", "application/xml" })
	public PresupuestoEnvio obtenerPresupuestosPorExpediente(PresupuestoEnvio datoEnvio,
			@Context HttpServletRequest request) {
		PresupuestoEnvio respuesta = new PresupuestoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null || datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null || datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getExpediente()==null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			List<Presupuesto> lista;
			try {
				DatoPresupuesto datoPresupuesto;
				Double sumaImporte = 0.0;
				Double sumaIva = 0.0;
//				Double sumaSubtotal = 0.0;
				Double sumaTotal = 0.0;
				if(datoEnvio.getActo()!=null)
					lista = this.presupuestoBo.buscarPresupuestos(datoEnvio.getExpediente().getIdexpediente(), datoEnvio.getActo().getIdacto());
				else
					lista = this.presupuestoBo.buscarPresupuestos(datoEnvio.getExpediente().getIdexpediente(), null);
				
				Impuesto impuestoIva = this.impuestoBo.obtenerImpuestoById(null, Constantes.IVA);
				Double iva = impuestoIva.getPorcentaje();
				ArrayList<DatoPresupuesto> litaResultado = new ArrayList<DatoPresupuesto>();
				if(lista!=null && !lista.isEmpty()){
//					respuesta.setLista(new ArrayList<Presupuesto>(lista));
					respuesta.setListaPresupuestosExp(litaResultado);
					String idActo = lista.get(0).getActo().getIdacto();
					datoPresupuesto = new DatoPresupuesto();
					datoPresupuesto.setIdacto(lista.get(0).getActo().getIdacto());
					datoPresupuesto.setNombreacto(lista.get(0).getActo().getDsnombre());
					litaResultado.add(datoPresupuesto);
					boolean b = true;
					for(Presupuesto p:lista){
						if(!idActo.equals(p.getActo().getIdacto())){
							datoPresupuesto.setTotal(datoPresupuesto.getImporte() + datoPresupuesto.getIva());
							datoPresupuesto.setPagado(b);
							datoPresupuesto = new DatoPresupuesto();
							litaResultado.add(datoPresupuesto);
							datoPresupuesto.setIdacto(p.getActo().getIdacto());
							datoPresupuesto.setNombreacto(p.getActo().getDsnombre());
							b = true;
						}
						datoPresupuesto.setImporte(datoPresupuesto.getImporte() + p.getImporte());
						sumaImporte = sumaImporte + p.getImporte();
						if(p.getIsaplicaiva()){
							sumaIva = sumaIva + (p.getImporte() * iva);
							datoPresupuesto.setIva(datoPresupuesto.getIva() + (p.getImporte()*iva));
						}
						idActo = p.getActo().getIdacto();
						if(p.getIspagado()==null || p.getIspagado()==true){
							if(b)
								b = false;
						}
					}
					datoPresupuesto.setTotal(datoPresupuesto.getImporte() + datoPresupuesto.getIva());
					sumaTotal = sumaImporte + sumaIva;
				}
				else
					respuesta.setListaPresupuestosExp(new ArrayList<DatoPresupuesto>());
				respuesta.setSumaImporte(sumaImporte);
				respuesta.setSumaIva(sumaIva);
				respuesta.setSumaTotal(sumaTotal);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}

		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}
	
	@POST
	@Path("/obtenerPresupuestosPorActo")
	@Produces({ "application/json", "application/xml" })
	public PresupuestoEnvio obtenerPresupuestosPorActo(PresupuestoEnvio datoEnvio,
			@Context HttpServletRequest request) {
		PresupuestoEnvio respuesta = new PresupuestoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null || datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null || datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getActo() == null || datoEnvio.getActo().getIdacto()==null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			List<Presupuesto> lista;
			try {
				Double sumaImporte = 0.0;
				Double sumaIva = 0.0;
//				Double sumaSubtotal = 0.0;
				Double sumaTotal = 0.0;
				lista = this.presupuestoBo.buscarPresupuestos(null, datoEnvio.getActo().getIdacto());
				Impuesto impuestoIva = this.impuestoBo.obtenerImpuestoById(null, Constantes.IVA);
				Double iva = impuestoIva.getPorcentaje();
				if(lista!=null){
					respuesta.setListaPorActo(new ArrayList<Presupuesto>(lista));
					for(Presupuesto p:lista){
						sumaImporte = sumaImporte + p.getImporte();
						if(p.getIsaplicaiva()){
							sumaIva = sumaIva + (p.getImporte() * iva);
						}
					}
					sumaTotal = sumaImporte + sumaIva;
				}
				else
					respuesta.setListaPorActo(new ArrayList<Presupuesto>());
				respuesta.setSumaImporte(sumaImporte);
				respuesta.setSumaIva(sumaIva);
				respuesta.setSumaTotal(sumaTotal);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
			} catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}
	
	@POST
	@Path("/obtenerPresupuestoPorId")
	@Produces({ "application/json", "application/xml" })
	public PresupuestoEnvio obtenerPresupuestoPorId(PresupuestoEnvio datoEnvio) {
		PresupuestoEnvio respuesta = new PresupuestoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null || datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null || datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getPresupuesto() == null || datoEnvio.getPresupuesto().getIdpresupuesto() == null 
				|| datoEnvio.getPresupuesto().getIdpresupuesto().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			Presupuesto presupuesto = null;
			try {
				presupuesto = this.presupuestoBo.buscarPorIdCompleto(datoEnvio.getPresupuesto().getIdpresupuesto());
				if(presupuesto!=null){
					presupuesto.getActo().setExpediente(null);
					presupuesto.getActo().setSuboperacion(null);
				}
				
				respuesta.setPresupuesto(presupuesto);
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}
	
	@POST
	@Path("/guardarPresupuesto")
	@Produces({ "application/json", "application/xml" })
	public PresupuestoEnvio guardarPresupuesto(PresupuestoEnvio datoEnvio) {
		PresupuestoEnvio respuesta = new PresupuestoEnvio();
		ArrayList<CodigoError> listaErrores = new ArrayList<>();
		respuesta.setErrores(listaErrores);
		CodigoError error;
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null || datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null || datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getExpediente() == null || datoEnvio.getExpediente().getIdexpediente().isEmpty()
				|| datoEnvio.getPresupuesto() == null || datoEnvio.getActo() == null 
				|| datoEnvio.getActo().getIdacto() == null || datoEnvio.getActo().getIdacto().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(datoEnvio.getPresupuesto().getImporte()==null){
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E29B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E29B1);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if(datoEnvio.getPresupuesto().getConceptoPago()==null){
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E29B2);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E29B2);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if(datoEnvio.getPresupuesto().getIsaplicaiva()==null){
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E29B3);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E29B3);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		
		if(!respuesta.isExito()){
			return respuesta;
		}
		
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			Presupuesto presupuesto = datoEnvio.getPresupuesto();
			Presupuesto resp = null;
			Date fecha = new Date();
			BitacoraGeneralHelper bg = new BitacoraGeneralHelper();
			try {
				if(presupuesto.getIsaplicaiva()){
					Impuesto impuestoIva = this.impuestoBo.obtenerImpuestoById(null, Constantes.IVA);
					Double iva = impuestoIva.getPorcentaje();
					presupuesto.setIva(iva * presupuesto.getImporte());
					presupuesto.setTotal(presupuesto.getImporte() + presupuesto.getIva());
				}
				
				if(presupuesto.getIdpresupuesto()==null || presupuesto.getIdpresupuesto().isEmpty()){
					presupuesto.setIdpresupuesto(GeneradorId.generaId(presupuesto));
					presupuesto.setIdsesion(datoEnvio.getUsuario().getIdsesionactual());
					presupuesto.setTmstmp(new Timestamp(fecha.getTime()));
					presupuesto.setFechaCreacion(fecha);
					presupuesto.setActo(datoEnvio.getActo());
					
					resp = this.presupuestoBo.save(presupuesto);
					if(resp!=null)
						bg.registrarEnBitacora(datoEnvio.getUsuario()
								.getIdusuario(), datoEnvio.getIdExpediente(),
								null, "Presupuesto",
								Constantes.OPERACION_REGISTRO,
								"Se guarda un presupuesto");
				}else{
					presupuesto.setIdsesion(datoEnvio.getUsuario().getIdsesionactual());
					presupuesto.setTmstmp(new Timestamp(fecha.getTime()));
					
					resp = this.presupuestoBo.update(presupuesto);
					if(resp!=null)
						bg.registrarEnBitacora(datoEnvio.getUsuario()
								.getIdusuario(), datoEnvio.getIdExpediente(),
								null, "Presupuesto",
								Constantes.OPERACION_ACTUALIZACION,
								"Se actualiza un presupuesto");
				}
				
				
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			} catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}

		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}
	
	@POST
	@Path("/actualizarPresupuesto")
	@Produces({ "application/json", "application/xml" })
	public PresupuestoEnvio actualizarPresupuesto(PresupuestoEnvio datoEnvio) {
		PresupuestoEnvio respuesta = new PresupuestoEnvio();
		ArrayList<CodigoError> listaErrores = new ArrayList<>();
		respuesta.setErrores(listaErrores);
		CodigoError error;
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null || datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null || datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getIdExpediente() == null || datoEnvio.getIdExpediente().isEmpty()
				|| datoEnvio.getPresupuesto() == null || datoEnvio.getPresupuesto().getIdpresupuesto() == null 
				|| datoEnvio.getPresupuesto().getIdpresupuesto().isEmpty() || datoEnvio.getPresupuesto().getActo() == null 
				|| datoEnvio.getPresupuesto().getActo().getIdacto() == null || datoEnvio.getPresupuesto().getActo().getIdacto().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(datoEnvio.getPresupuesto().getImporte()==null){
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E29B1);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E29B1);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if(datoEnvio.getPresupuesto().getConceptoPago()==null){
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E29B2);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E29B2);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		if(datoEnvio.getPresupuesto().getIsaplicaiva()==null){
			error = new CodigoError();
			error.setCodigo(ErrorCodigoMensaje.CODIGO_E29B3);
			error.setMensaje(ErrorCodigoMensaje.MENSAJE_E29B3);
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			listaErrores.add(error);
		}
		
		if(!respuesta.isExito()){
			return respuesta;
		}
		
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			Presupuesto presupuesto = datoEnvio.getPresupuesto();
			Presupuesto resp = null;
			Date fecha = new Date();
			BitacoraGeneralHelper bg = new BitacoraGeneralHelper();
			try {
//				presupuesto.setIdpresupuesto(GeneradorId.generaId(presupuesto));
				presupuesto.setIdsesion(datoEnvio.getUsuario().getIdsesionactual());
				presupuesto.setTmstmp(new Timestamp(fecha.getTime()));
//				presupuesto.setFechaCreacion(fecha);
				
				resp = this.presupuestoBo.update(presupuesto);
				if(resp!=null)
					bg.registrarEnBitacora(datoEnvio.getUsuario()
							.getIdusuario(), datoEnvio.getIdExpediente(),
							null, "Presupuesto",
							Constantes.OPERACION_ACTUALIZACION,
							"Se actualiza un presupuesto");
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			} catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}
	
	@POST
	@Path("/eliminarPresupuesto")
	@Produces({ "application/json", "application/xml" })
	public PresupuestoEnvio eliminarPresupuesto(PresupuestoEnvio datoEnvio) {
		PresupuestoEnvio respuesta = new PresupuestoEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null || datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null || datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getPresupuesto().getIdpresupuesto() == null || datoEnvio.getPresupuesto().getIdpresupuesto().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			Presupuesto presupuesto = datoEnvio.getPresupuesto();
			boolean resp = false;
			BitacoraGeneralHelper bg = new BitacoraGeneralHelper();
			try {
				resp = this.presupuestoBo.delete(presupuesto);
				if(resp)
					bg.registrarEnBitacora(datoEnvio.getUsuario()
							.getIdusuario(), datoEnvio.getIdExpediente(),
							null, "Presupuesto",
							Constantes.OPERACION_ELIMINACION,
							"Se elimina un presupuesto");
				respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}
	
	
	@POST
	@Path("/eliminarPresupuestosDelActo")
	@Produces({ "application/json", "application/xml" })
	public PresupuestoEnvio eliminarPresupuestosDelActo(PresupuestoEnvio datoEnvio) {
		PresupuestoEnvio respuesta = new PresupuestoEnvio();
		
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null || datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null || datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getIdActo() == null || datoEnvio.getIdActo().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
//			Presupuesto presupuesto = datoEnvio.getPresupuesto();
//			boolean resp = false;
//			BitacoraGeneralHelper bg = new BitacoraGeneralHelper();
			try {
				this.presupuestoBo.elimiarPresupuestosPorActo(datoEnvio.getIdActo(), datoEnvio.getUsuario().getIdusuario());
				respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
		}
		return respuesta;
	}
	

	
}
