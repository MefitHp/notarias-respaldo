package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.GrupoBo;
import com.palestra.notaria.bo.VariableGrupoBo;
import com.palestra.notaria.bo.impl.GrupoBoImpl;
import com.palestra.notaria.bo.impl.VariableGrupoBoImpl;
import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.VariableGrupoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.VariableGrupo;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/definicion")
public class VariableGrupoRest {

	private VariableGrupoBo varGrupoBo;
	
	private GrupoBo grupoBo;
	
	public VariableGrupoRest(){
		varGrupoBo = new VariableGrupoBoImpl();
		grupoBo = new GrupoBoImpl();
	}
	
//	@POST
//	@Path("/listarPorGrupo")
//	@Produces(MediaType.APPLICATION_JSON)
//	public VariableGrupoEnvio listarPorGrupo(VariableGrupoEnvio vg){
//		VariableGrupoEnvio respuesta = new VariableGrupoEnvio();
//		if (vg == null || vg.getUsuario() == null
//				|| vg.getUsuario().getIdusuario() == null || vg.getUsuario().getIdusuario().isEmpty()
//				|| vg.getUsuario().getIdsesionactual() == null || vg.getUsuario().getIdsesionactual().isEmpty()
//				|| vg.getVarGrupoList() == null || vg.getVarGrupoList().isEmpty()) {
//			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
//			respuesta.setExito(false);
//			return respuesta;
//		}
//		if(NotariaUtils.isSesionValida(vg.getUsuario().getIdsesionactual(),vg.getUsuario().getIdusuario())){
//			try{
//				//VariableGrupoBo varGrupoBo = new VariableGrupoBoImpl();
////				varGrupoBo.findByGrupo(vg.getVarGrupo().getGrupo().getIdgrupo());
//			}catch(Exception e){
//				e.printStackTrace();
//				respuesta.setExito(true);
//				respuesta.setEstatus("ocurrio un error al listar");
//				return respuesta;
//			}
//			respuesta.setExito(true);
//			respuesta.setEstatus("guardado correcto");
//			return respuesta;
//		}else{
//			respuesta.setExito(false);
//			respuesta.setEstatus("sesion invalida");
//			return respuesta;
//		}
//	}
	
	
	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public VariableGrupoEnvio registraVariableGrupo(VariableGrupoEnvio datoEnvio){
		VariableGrupoEnvio respuesta = new VariableGrupoEnvio();
		if (datoEnvio == null 
				|| datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null 
				|| datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null 
				|| datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getVarGrupoList() == null 
				|| datoEnvio.getVarGrupoList().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(datoEnvio.getUsuario().getIdsesionactual(),datoEnvio.getUsuario().getIdusuario())){			
			try{
				
				List<VariableGrupo> enviadas = datoEnvio.getVarGrupoList();
				
				//localiza las variables actualmente almacenadas:
				List<VariableGrupo> persistidas = getVarGrupoBo().buscarVariablesGrupo(enviadas.get(0).getGrupo()); //sacamos el grupo de la primer incidencia para buscar las persistidas
				if (persistidas.size()>0){
					//eliminamos todas y dejamos limpio para que se genere la nueva relación:
					for(VariableGrupo variable:persistidas){
						//System.out.print("Por eliminar a "+variable.getIdgpovars());
						//getVarGrupoBo().delete(variable);
						getVarGrupoBo().eliminarPorId(variable.getIdgpovars());
						//System.out.print("\t\t\t[OK]");
					}
				}
				
				for(VariableGrupo variable:enviadas){
					variable.setIdgpovars(GeneradorId.generaId(variable));
					getVarGrupoBo().save(variable);
				}
				//ArrayList<VariableGrupo> listaGuardada = 
				//		(ArrayList<VariableGrupo>)getGrupoBo().getVariablesByGrupo(datoEnvio.getVarGrupoList().get(0).getGrupo().getIdgrupo());
				ArrayList<VariableGrupo> listaGuardada = (ArrayList<VariableGrupo>)getVarGrupoBo().buscarVariablesGrupo(enviadas.get(0).getGrupo());
				if(listaGuardada==null || listaGuardada.isEmpty()){
					respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
					respuesta.setExito(false);
				}else{
					respuesta.setVarGrupoList(listaGuardada);
					respuesta.setExito(true);
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				}
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
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	@POST
	@Path("/buscarVariables")
	@Produces(MediaType.APPLICATION_JSON)
	public VariableGrupoEnvio buscarVariables(VariableGrupoEnvio datoEnvio){
		VariableGrupoEnvio respuesta = new VariableGrupoEnvio();
		if (datoEnvio == null 
				|| datoEnvio.getUsuario() == null
				|| datoEnvio.getUsuario().getIdusuario() == null 
				|| datoEnvio.getUsuario().getIdusuario().isEmpty()
				|| datoEnvio.getUsuario().getIdsesionactual() == null 
				|| datoEnvio.getUsuario().getIdsesionactual().isEmpty()
				|| datoEnvio.getGrupo() == null 
				|| datoEnvio.getGrupo().getIdgrupo()==null
				|| datoEnvio.getGrupo().getIdgrupo().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(datoEnvio.getUsuario().getIdsesionactual(),datoEnvio.getUsuario().getIdusuario())){
		try{
			VariableGrupoBo bo = new VariableGrupoBoImpl();
			List<VariableGrupo> variables = bo.buscarVariablesGrupo(datoEnvio.getGrupo());
			respuesta.setVarGrupoList((ArrayList<VariableGrupo>)variables);
			return respuesta;
		}catch(NotariaException e){
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_FALLIDA+" "+e.getMessage());
			CodigoError error = new CodigoError();
			error.setCodigo("50001");
			error.setMensaje(e.getMessage());
			List<CodigoError> errores = new ArrayList<>();
			errores.add(error);
			respuesta.setErrores(errores);
			return respuesta;		
		}
		}else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;		
		}
		
	}
	
	@POST
	@Path("/eliminar")
	public VariableGrupoEnvio eliminar(VariableGrupoEnvio vg){
		VariableGrupoEnvio respuesta = new VariableGrupoEnvio();
		if (vg == null || vg.getUsuario() == null
				|| vg.getUsuario().getIdusuario() == null || vg.getUsuario().getIdusuario().isEmpty()
				|| vg.getUsuario().getIdsesionactual() == null || vg.getUsuario().getIdsesionactual().isEmpty()
				|| vg.getVarGrupoList() == null || vg.getVarGrupoList().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(vg.getUsuario().getIdsesionactual(),vg.getUsuario().getIdusuario())){
			boolean iseliminado=false;
			try{
				for(VariableGrupo variable:vg.getVarGrupoList()){
					
					iseliminado=getVarGrupoBo().delete(variable);
					if(!iseliminado){
						respuesta.setEstatus(Constantes.ESTATUS_ERROR_ELIMINAR);
						respuesta.setExito(false);
						return respuesta;
					}
				}
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_ELIMINACION_CORRECTA);
					return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	public VariableGrupoBo getVarGrupoBo() {
		return varGrupoBo;
	}
	
	public void setVarGrupoBo(VariableGrupoBo varGrupoBo) {
		this.varGrupoBo = varGrupoBo;
	}
	
	public GrupoBo getGrupoBo() {
		return grupoBo;
	}
	
	public void setGrupoBo(GrupoBo grupoBo) {
		this.grupoBo = grupoBo;
	}
}
