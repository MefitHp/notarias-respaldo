package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.PlantillaDocumentoNotarialBo;
import com.palestra.notaria.bo.impl.PlantillaDocumentoNotarialBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.PlantillaDocNotarialEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarialPK;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarialSubOperacion;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/docNotarial")
public class PlantillaDocumentoNotarialRest {

	PlantillaDocumentoNotarialBo docNotarialBo;
	
	public PlantillaDocumentoNotarialRest(){
		docNotarialBo = new PlantillaDocumentoNotarialBoImpl();
	}
	
	@POST
	@Path("/prueba")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CodigoError> prueba(String str){
		PlantillaDocumentoNotarialBo docBo = new PlantillaDocumentoNotarialBoImpl();
		return docBo.validarReferencias(str);
	}
	@POST
	@Path("/validarPlantilla")
	@Produces(MediaType.APPLICATION_JSON)
	public PlantillaDocNotarialEnvio validarPlantilla(PlantillaDocNotarialEnvio pe){
		PlantillaDocNotarialEnvio respuesta = new PlantillaDocNotarialEnvio();
		if (pe == null || pe.getUsuario() == null
				|| pe.getUsuario().getIdusuario() == null || pe.getUsuario().getIdusuario().isEmpty()
				|| pe.getUsuario().getIdsesionactual() == null || pe.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pe.getUsuario().getIdsesionactual(),pe.getUsuario().getIdusuario())){
//			ExpresionBo expBo = new ExpresionBoImpl();
			PlantillaDocumentoNotarialBo plantillaBo = new PlantillaDocumentoNotarialBoImpl();
			List<CodigoError> errores = new ArrayList<CodigoError>();			
			errores.addAll(plantillaBo.isSimetrica(pe.getPlantilla().getTxplantilla()));
			if(errores.size()>0){
				respuesta.setErrores(errores);
				respuesta.setEstatus("Hubo errores de sintaxis");
				respuesta.setExito(false);
				return respuesta;
			}else{
				errores.addAll(plantillaBo.validaVariables(pe.getPlantilla().getTxplantilla()));
				if(errores.size()>0){
					respuesta.setErrores(errores);
					respuesta.setEstatus("Hubo errores al validar variables");
					respuesta.setExito(false);
					return respuesta;
				}else{
					errores.addAll(plantillaBo.validarReferencias(pe.getPlantilla().getTxplantilla()));
					if(errores.size()>0){
						respuesta.setErrores(errores);
						respuesta.setEstatus("Hubo errores al validar referencias");
						respuesta.setExito(false);
						return respuesta;
					}else{
						respuesta.setEstatus("Todo correcto al validar plantilla");
						respuesta.setExito(true);
						return respuesta;
					}
				}
			}
		}else{
			respuesta.setExito(false);
			respuesta.setEstatus("sesion invalida");
			return respuesta;
		}
	}
	
	@POST
	@Path("/eliminar")
	@Produces(MediaType.APPLICATION_JSON)
	public PlantillaDocNotarialEnvio eliminar(PlantillaDocNotarialEnvio pe){
		PlantillaDocNotarialEnvio respuesta = new PlantillaDocNotarialEnvio();
		if (pe == null || pe.getUsuario() == null
				|| pe.getUsuario().getIdusuario() == null || pe.getUsuario().getIdusuario().isEmpty()
				|| pe.getUsuario().getIdsesionactual() == null || pe.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pe.getUsuario().getIdsesionactual(),pe.getUsuario().getIdusuario())){
			try{
				
				getDocNotarialBo().delete(pe.getPlantilla());
				respuesta.setExito(true);
				respuesta.setEstatus("Se eliminó correctamente la plantilla.");
				return respuesta;
		//		respuesta.setNoPublicadosList((ArrayList<PlantillaDocumentoNotarial>)getDocNotarialBo().getNoPublicados());
		//		respuesta.setPublicadosList((ArrayList<PlantillaDocumentoNotarial>) getDocNotarialBo().getPublicados());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus("sesion invalida");
			return respuesta;
		}		
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public PlantillaDocNotarialEnvio listar(PlantillaDocNotarialEnvio pe) {
		PlantillaDocNotarialEnvio respuesta = new PlantillaDocNotarialEnvio();
		if (pe == null || pe.getUsuario() == null
				|| pe.getUsuario().getIdusuario() == null || pe.getUsuario().getIdusuario().isEmpty()
				|| pe.getUsuario().getIdsesionactual() == null || pe.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pe.getUsuario().getIdsesionactual(),pe.getUsuario().getIdusuario())){
			try{
				respuesta.setNoPublicadosList((ArrayList<PlantillaDocumentoNotarial>)getDocNotarialBo().getNoPublicados());
				respuesta.setPublicadosList((ArrayList<PlantillaDocumentoNotarial>) getDocNotarialBo().getPublicados());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus("listado correcto");
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus("sesion invalida");
			return respuesta;
		}
	}
	
	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public PlantillaDocNotarialEnvio guardar(PlantillaDocNotarialEnvio pe){
		PlantillaDocNotarialEnvio respuesta = new PlantillaDocNotarialEnvio();
		if (pe == null || pe.getUsuario() == null
				|| pe.getUsuario().getIdusuario() == null || pe.getUsuario().getIdusuario().isEmpty()
				|| pe.getUsuario().getIdsesionactual() == null || pe.getUsuario().getIdsesionactual().isEmpty()
				|| pe.getPlantilla() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pe.getUsuario().getIdsesionactual(),pe.getUsuario().getIdusuario())){
			//pe.getPlantilla().setId(new PlantillaDocumentoNotarialPK());
			//pe.getPlantilla().getId().setIddocnot(GeneradorId.generaId(pe.getPlantilla()));
			//pe.getPlantilla().getId().setInversion(0);
			pe.getPlantilla().setIdsesion(pe.getUsuario().getIdsesionactual());
			try{
				getDocNotarialBo().save(pe.getPlantilla());
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}

	}
	
	@POST
	@Path("/actualizar")
	@Produces({"application/json", "application/xml" })
	public PlantillaDocNotarialEnvio actualizar(PlantillaDocNotarialEnvio pe){
		PlantillaDocNotarialEnvio respuesta = new PlantillaDocNotarialEnvio();
		if (pe == null || pe.getUsuario() == null
				|| pe.getUsuario().getIdusuario() == null || pe.getUsuario().getIdusuario().isEmpty()
				|| pe.getUsuario().getIdsesionactual() == null || pe.getUsuario().getIdsesionactual().isEmpty()
				|| pe.getPlantilla() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pe.getUsuario().getIdsesionactual(),pe.getUsuario().getIdusuario())){
			pe.getPlantilla().setIdsesion(pe.getUsuario().getIdsesionactual());
			pe.getPlantilla().setDssesion(pe.getUsuario().getIdsesionactual());
			try{

				docNotarialBo.update(pe.getPlantilla());				
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;

			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus("sesion invalida");
			return respuesta;
		}
	}
	
//	@POST
//	@Path("/eliminar")
//	@Produces({ "application/json", "application/xml" })
//	public boolean eliminar(PlantillaDocumentoNotarial docNotarial) {
//
//		return getDocNotarialBo().delete(docNotarial);
//	}
	
	@POST
	@Path("/obtenerPorId")
	@Produces({ "application/json", "application/xml" })
	public PlantillaDocNotarialEnvio obtenerPorId(PlantillaDocNotarialEnvio pe) {
		PlantillaDocNotarialEnvio respuesta = new PlantillaDocNotarialEnvio();
		if (pe == null || pe.getUsuario() == null
				|| pe.getUsuario().getIdusuario() == null || pe.getUsuario().getIdusuario().isEmpty()
				|| pe.getUsuario().getIdsesionactual() == null || pe.getUsuario().getIdsesionactual().isEmpty()
				|| pe.getPlantilla() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pe.getUsuario().getIdsesionactual(),pe.getUsuario().getIdusuario())){
			try{
				respuesta.setPlantilla(getDocNotarialBo().findById(pe.getPlantilla().getId()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getPlantilla()==null){
				respuesta.setEstatus("ocurrio un error al recuperar");
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus("recuperacion correcta");
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus("sesion invalida");
			return respuesta;
		}
	}	

	public PlantillaDocumentoNotarialBo getDocNotarialBo() {
		return docNotarialBo;
	}
	
	public void setDocNotarialBo(PlantillaDocumentoNotarialBo docNotarialBo) {
		this.docNotarialBo = docNotarialBo;
	}
}
