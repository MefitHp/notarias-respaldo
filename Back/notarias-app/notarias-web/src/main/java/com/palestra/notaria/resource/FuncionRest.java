package com.palestra.notaria.resource;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import com.ibm.icu.util.Calendar;
import com.palestra.notaria.bo.FuncionBo;
import com.palestra.notaria.bo.impl.FuncionBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.FuncionEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Funcion;
import com.palestra.notaria.modelo.FuncionParametro;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/funciones")
public class FuncionRest {
	//Logger logger = Logger.getLogger(FuncionRest.class);
	
	public FuncionRest() {}
	
	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public FuncionEnvio guardar(FuncionEnvio datoEnvio){
		FuncionEnvio respuesta = new FuncionEnvio();
		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getFuncion() == null) {			
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		System.out.printf("=====> Se recibio un elemento funcion %s%n", datoEnvio.getFuncion().getNombre());
		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			Funcion funcion = null;
			try{				
				FuncionBo bo = new FuncionBoImpl();				
				funcion = bo.obtenerPorNombre(datoEnvio.getFuncion().getNombre());
				if (funcion==null){
					funcion = datoEnvio.getFuncion();
					funcion.setIdentificador(GeneradorId.generaId(funcion));
					funcion.setIdsesion(datoEnvio.getUsuario().getIdsesionactual());
					funcion.setTmstmp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
					for(FuncionParametro param:datoEnvio.getFuncion().getDetalleList()){
						param.setIdfuncion(funcion);
						param.setIdsesion(datoEnvio.getUsuario().getIdsesionactual());
						param.setTmstmp(new Timestamp(new Date().getTime()));
					}				
					bo.save(funcion);
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
				}else{
					String message = String.format("Al parecer ya existe una funcion con el nombre %s [%s]",funcion.getNombre(), funcion.getIdentificador());
					//logger.error("=====> "+message);
					respuesta.setEstatus(message);
					respuesta.setExito(false);
					return respuesta;					
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} 
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public FuncionEnvio listar(FuncionEnvio datoEnvio){
		FuncionEnvio respuesta = new FuncionEnvio();		

		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			try{				
				FuncionBo bo = new FuncionBoImpl();
				List<Funcion> funciones = bo.listarTodas();
				for(Funcion funcion:funciones){
					System.out.println("=====> funcion "+funcion.getNombre());
				}
				respuesta.getFunciones().addAll(funciones);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;				
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} 
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/actualizar")
	@Produces(MediaType.APPLICATION_JSON)
	public FuncionEnvio actualizar(FuncionEnvio datoEnvio){
		FuncionEnvio respuesta = new FuncionEnvio();
		

		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getFuncion() == null
				|| datoEnvio.getFuncion().getIdentificador()==null
				|| datoEnvio.getFuncion().getIdentificador().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			Funcion funcion = null;
			try{				
				FuncionBo bo = new FuncionBoImpl();
				if(bo.obtenerPorNombre(datoEnvio.getFuncion().getNombre())==null){
					funcion = bo.obtenerFuncion(datoEnvio.getFuncion().getIdentificador());
					if (funcion!=null){						
						//funcion.setDetalleList(datoEnvio.getFuncion().getDetalleList());
						bo.actualizarParametros(datoEnvio.getFuncion());
						respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
						respuesta.setExito(true);
						return respuesta;
					}else{
						String message = String.format("Al parecer no existe la funcion con el nombre %s [%s]",datoEnvio.getFuncion().getNombre(), datoEnvio.getFuncion().getIdentificador());
						//logger.error("=====> "+message);
						respuesta.setEstatus(message);
						respuesta.setExito(false);
						return respuesta;					
					}
				}else{
					String message = String.format("Al parecer ya existe una funcion con el nombre %s [%s]",datoEnvio.getFuncion().getNombre(), datoEnvio.getFuncion().getIdentificador());
					//logger.error("=====> "+message);
					respuesta.setEstatus(message);
					respuesta.setExito(false);
					return respuesta;										
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} 
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/eliminar")
	@Produces(MediaType.APPLICATION_JSON)
	public FuncionEnvio eliminar(FuncionEnvio datoEnvio){
		FuncionEnvio respuesta = new FuncionEnvio();
		

		if (datoEnvio == null || datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null
				|| datoEnvio.getUsuario().getIdusuario().trim().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null
				|| datoEnvio.getUsuario().getIdsesionactual().trim().isEmpty()
				|| datoEnvio.getFuncion() == null
				|| datoEnvio.getFuncion().getIdentificador()==null
				|| datoEnvio.getFuncion().getIdentificador().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(datoEnvio.getUsuario()
				.getIdsesionactual(), datoEnvio.getUsuario().getIdusuario())) {
			Funcion funcion = null;
			try{				
				FuncionBo bo = new FuncionBoImpl();
				funcion = bo.obtenerFuncion(datoEnvio.getFuncion().getIdentificador());
				if (funcion!=null){						
					//funcion.setDetalleList(datoEnvio.getFuncion().getDetalleList());
					bo.eliminarFuncion(datoEnvio.getFuncion());
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
				}else{
					String message = String.format("Al parecer no existe la funcion con el nombre %s [%s]",datoEnvio.getFuncion().getNombre(), datoEnvio.getFuncion().getIdentificador());
					//logger.error("=====> "+message);
					respuesta.setEstatus(message);
					respuesta.setExito(false);
					return respuesta;					
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			} 
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
			respuesta.setExito(false);
			return respuesta;
		}
	}
}
