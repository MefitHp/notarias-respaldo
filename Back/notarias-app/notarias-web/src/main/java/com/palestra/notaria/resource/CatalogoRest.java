package com.palestra.notaria.resource;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.CatalogoBo;
import com.palestra.notaria.bo.ElementoCatalogoBo;
import com.palestra.notaria.bo.impl.CatalogoBoImpl;
import com.palestra.notaria.bo.impl.ElementoCatalogoBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.CatalogoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Catalogo;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.util.NotariaUtils;

@Path("/catalogo")
public class CatalogoRest {
	
	static Logger logger = Logger.getLogger(CatalogoRest.class);
	
	ElementoCatalogoBo catalogoBo;
	
	CatalogoBo tipoCatalogoBo;
	
	public CatalogoRest(){
		this.catalogoBo = new ElementoCatalogoBoImpl();
		this.tipoCatalogoBo = new CatalogoBoImpl();
	}

	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public CatalogoEnvio getCatalogo(CatalogoEnvio ce) {
		CatalogoEnvio respuesta = new CatalogoEnvio();
		if (ce == null || ce.getUsuario() == null
				|| ce.getUsuario().getIdusuario() == null || ce.getUsuario().getIdusuario().isEmpty()
				|| ce.getUsuario().getIdsesionactual() == null || ce.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(),ce.getUsuario().getIdusuario())){
			try{
				respuesta.setCatalogoList((ArrayList<ElementoCatalogo>) getCatalogoBo().findAll());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getCatalogoList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
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
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public CatalogoEnvio registraCatalogo(CatalogoEnvio ce) {
		CatalogoEnvio respuesta = new CatalogoEnvio();
		if (ce == null || ce.getUsuario() == null
				|| ce.getUsuario().getIdusuario() == null || ce.getUsuario().getIdusuario().isEmpty()
				|| ce.getUsuario().getIdsesionactual() == null || ce.getUsuario().getIdsesionactual().isEmpty()
				|| ce.getCatalogoList() == null || ce.getCatalogoList().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(),ce.getUsuario().getIdusuario())){
//			ArrayList<ElementoCatalogo> resultList = new ArrayList<ElementoCatalogo>();
			try{
				respuesta.setCatalogo(getTipoCatalogoBo().save(ce.getCatalogo()));
//				for(ElementoCatalogo cat:ce.getCatalogoList()){
//					cat.setIdelemento(GeneradorId.generaId(cat));
//					cat.setIdsesion(ce.getUsuario().getIdsesionactual());
//					resultList.add(getCatalogoBo().save(cat));
//				}
//				respuesta.setCatalogoList(resultList);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	
	@POST
	@Path("/actualizar")
	@Produces({"application/json", "application/xml" })
	public CatalogoEnvio actualizar(CatalogoEnvio ce) {
		CatalogoEnvio respuesta = new CatalogoEnvio();
		if (ce == null || ce.getUsuario() == null
				|| ce.getUsuario().getIdusuario() == null || ce.getUsuario().getIdusuario().isEmpty()
				|| ce.getUsuario().getIdsesionactual() == null || ce.getUsuario().getIdsesionactual().isEmpty()
				|| ce.getCatalogoList() == null || ce.getCatalogoList().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(),ce.getUsuario().getIdusuario())){
			ArrayList<ElementoCatalogo> resultList = new ArrayList<ElementoCatalogo>();
			try{
				for(ElementoCatalogo cat:ce.getCatalogoList()){
				cat.setIdsesion(ce.getUsuario().getIdsesionactual());
				resultList.add(getCatalogoBo().update(cat));
				}
				respuesta.setCatalogoList(resultList);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
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
	
//	@POST
//	@Path("/eliminar")
//	@Produces({ "application/json", "application/xml" })
//	public Response eliminar(Catalogo catalogo) {
//		
//		try{
//			catalogoBo.delete(catalogo);
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		return Response.status(201).entity(catalogo).build();
//
//	}
	
	@POST
	@Path("/listarTiposCatalogo")
	@Produces({ "application/json", "application/xml" })
	public CatalogoEnvio listarTipoCatalogo(CatalogoEnvio ce) {
		CatalogoEnvio respuesta = new CatalogoEnvio();
		if (ce == null || ce.getUsuario() == null
				|| ce.getUsuario().getIdusuario() == null || ce.getUsuario().getIdusuario().isEmpty()
				|| ce.getUsuario().getIdsesionactual() == null || ce.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(),ce.getUsuario().getIdusuario())){
			try{
				respuesta.setTipoCatList((ArrayList<Catalogo>)getTipoCatalogoBo().findAll());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getCatalogoList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
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
	
	
	/**
	 * Lista los elementos del catálogo por su tipo catálogo
	 * 
	 * @param ce
	 * @return
	 */
	@POST
	@Path("/listarPorCatalogo")
	@Produces({ "application/json", "application/xml" })
	public CatalogoEnvio obtenerPorId(CatalogoEnvio ce) {
		CatalogoEnvio respuesta = new CatalogoEnvio();
		if (ce == null || ce.getUsuario() == null
				|| ce.getUsuario().getIdusuario() == null || ce.getUsuario().getIdusuario().isEmpty()
				|| ce.getUsuario().getIdsesionactual() == null || ce.getUsuario().getIdsesionactual().isEmpty()
				|| ce.getElementoCatalogo() == null || ce.getElementoCatalogo().getCatalogo() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ce.getUsuario().getIdsesionactual(),ce.getUsuario().getIdusuario())){
			try{
				respuesta.setCatalogoList((ArrayList<ElementoCatalogo>)getCatalogoBo().listarPorCatalogo(ce.getElementoCatalogo().getCatalogo()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getCatalogoList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
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

	public ElementoCatalogoBo getCatalogoBo() {
		return catalogoBo;
	}

	public void setCatalogoBo(ElementoCatalogoBo catalogoBo) {
		this.catalogoBo = catalogoBo;
	}
	
	public CatalogoBo getTipoCatalogoBo() {
		return tipoCatalogoBo;
	}
	
	public void setTipoCatalogoBo(CatalogoBo tipoCatalogoBo) {
		this.tipoCatalogoBo = tipoCatalogoBo;
	}

}
