package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.DocumentoSuboperacionBo;
import com.palestra.notaria.bo.OperacionBo;
import com.palestra.notaria.bo.SuboperacionBo;
import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.bo.impl.DocumentoSuboperacionBoImpl;
import com.palestra.notaria.bo.impl.OperacionBoImpl;
import com.palestra.notaria.bo.impl.SuboperacionBoImpl;
import com.palestra.notaria.bo.impl.TramiteBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.FormatoWrapper;
import com.palestra.notaria.envio.OperacionEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoSuboperacion;
import com.palestra.notaria.modelo.Operacion;
import com.palestra.notaria.modelo.Suboperacion;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/operacion")
public class OperacionRest {
	
	private OperacionBo operacionBo;
	
	private SuboperacionBo suboperacionBo;
	
	private TramiteBo tramiteBo;
	
	private DocumentoSuboperacionBo docSubopBo;
	
	public OperacionRest(){
		operacionBo = new OperacionBoImpl();
		suboperacionBo = new SuboperacionBoImpl();
		this.tramiteBo = new TramiteBoImpl();
		this.docSubopBo = new DocumentoSuboperacionBoImpl();
	}
	
	@POST
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public OperacionEnvio listar(OperacionEnvio oe){
		OperacionEnvio respuesta = new OperacionEnvio();
		if (oe == null || oe.getUsuario() == null
				|| oe.getUsuario().getIdusuario() == null || oe.getUsuario().getIdusuario().isEmpty()
				|| oe.getUsuario().getIdsesionactual() == null || oe.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(),oe.getUsuario().getIdusuario())){
			try{
				respuesta.setOperacionList((ArrayList<Operacion>)getOperacionBo().findAll());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getOperacionList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	@POST
	@Path("/listarSuboperaciones")
	@Produces(MediaType.APPLICATION_JSON)
	public OperacionEnvio listarSuboperacion(OperacionEnvio oe){
		OperacionEnvio respuesta = new OperacionEnvio();
		if (oe == null || oe.getUsuario() == null
				|| oe.getUsuario().getIdusuario() == null || oe.getUsuario().getIdusuario().isEmpty()
				|| oe.getUsuario().getIdsesionactual() == null || oe.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(),oe.getUsuario().getIdusuario())){
			try{
				respuesta.setSuboperacionList((ArrayList<Suboperacion>)getSuboperacionBo().findAll());
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getSuboperacionList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	@POST
	@Path("/listarSubOperacionesPorOperacion")
	@Produces({ "application/json", "application/xml" })
	public OperacionEnvio listarSubOperacionesPorOperacion(OperacionEnvio oe){
		OperacionEnvio respuesta = new OperacionEnvio();
		if (oe == null || oe.getUsuario() == null
				|| oe.getUsuario().getIdusuario() == null || oe.getUsuario().getIdusuario().isEmpty()
				|| oe.getUsuario().getIdsesionactual() == null || oe.getUsuario().getIdsesionactual().isEmpty()
				|| oe.getOperacion() == null || oe.getOperacion().getIdoperacion() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(),oe.getUsuario().getIdusuario())){
			try{
				
				List<Suboperacion> lista = this.operacionBo.OperacionesPorOperacion(oe.getOperacion().getIdoperacion());
				respuesta.setSuboperacionList(new ArrayList<Suboperacion>(lista));
				
				if(oe.getTramite() != null){
					String idLocacion = "";
					if(oe.getTramite().getLocacion() != null){
						idLocacion = this.tramiteBo.buscarPorIdCompleto(oe.getTramite()).getLocacion().getIdelemento();
					}
					for(Suboperacion sub:lista){
						//sub.setPreviosList(new ArrayList<FormatoWrapper>());
						//sub.setPosterioresList(new ArrayList<FormatoWrapper>());
						/*List<DocumentoSuboperacion> docSubopList = this.docSubopBo.listarPreviosPorSubopAndLocalidad(idLocacion, sub.getIdsuboperacion());
						for(DocumentoSuboperacion doc:docSubopList){
							System.out.println("formato pdf "+doc.getFormatopdf());
							if(doc.getDocumento()!=null){
								if(doc.getDocumento().getTipodoc().getIdelemento().equals(Constantes.ID_DOCUMENTO_POSTERIOR)){
									FormatoWrapper wrapper = new FormatoWrapper();
									wrapper.setIddocumento(doc.getDocumento().getIddocumento());
									wrapper.setDstitulo(doc.getDocumento().getDstitulo());
									wrapper.setDsdescripcion(doc.getDocumento().getDsdescripcion());
									wrapper.setTipodoc(doc.getDocumento().getTipodoc());
									sub.getPosterioresList().add(wrapper);
								}else{
									FormatoWrapper wrapper = new FormatoWrapper();
									wrapper.setIddocumento(doc.getDocumento().getIddocumento());
									wrapper.setDstitulo(doc.getDocumento().getDstitulo());
									wrapper.setDsdescripcion(doc.getDocumento().getDsdescripcion());
									wrapper.setTipodoc(doc.getDocumento().getTipodoc());
									sub.getPreviosList().add(wrapper);
								}
							}else{
								if(doc.getFormatopdf().getTipodoc().getIdelemento().equals(Constantes.ID_DOCUMENTO_POSTERIOR)){
									FormatoWrapper wrapper = new FormatoWrapper();
									wrapper.setIddocumento(doc.getFormatopdf().getIdentificador());
									wrapper.setDstitulo(doc.getFormatopdf().getDstitulo());
									wrapper.setDsdescripcion(doc.getFormatopdf().getDsdescripcion());
									wrapper.setTipodoc(doc.getFormatopdf().getTipodoc());
									sub.getPosterioresList().add(wrapper);
								}else{
									FormatoWrapper wrapper = new FormatoWrapper();
									wrapper.setIddocumento(doc.getFormatopdf().getIdentificador());
									wrapper.setDstitulo(doc.getFormatopdf().getDstitulo());
									wrapper.setDsdescripcion(doc.getFormatopdf().getDsdescripcion());
									wrapper.setTipodoc(doc.getFormatopdf().getTipodoc());
									sub.getPreviosList().add(wrapper);
								}
							}
						}*/
					}
				}
//				String idLocacion = this.tramiteBo.buscarPorIdCompleto(oe.getTramite()).getLocacion().getIdelemento();
//				List<DocumentoSuboperacion> docSubopList = this.docSubopBo.listarPreviosPorSubopAndLocalidad(idLocacion, oe.getSuboperacion().getIdsuboperacion());
//				for(DocumentoSuboperacion doc:docSubopList){
//					if(doc.getDocumento().getTipodoc().getIdelemento().equals(Constantes.ID_DOCUMENTO_POSTERIOR))
//						respuesta.getPosterioresList().add(doc.getDocumento());
//					else
//						respuesta.getPreviosList().add(doc.getDocumento());
//				}
				
				
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getSuboperacionList()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public OperacionEnvio guardar(OperacionEnvio oe){
		OperacionEnvio respuesta = new OperacionEnvio();
		if (oe == null || oe.getUsuario() == null
				|| oe.getUsuario().getIdusuario() == null || oe.getUsuario().getIdusuario().isEmpty()
				|| oe.getUsuario().getIdsesionactual() == null || oe.getUsuario().getIdsesionactual().isEmpty()
				|| oe.getOperacion() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(),oe.getUsuario().getIdusuario())){
			oe.getOperacion().setIdoperacion(GeneradorId.generaId(oe.getOperacion()));
			oe.getOperacion().setIdsesion(oe.getUsuario().getIdsesionactual());
			try{
				respuesta.setOperacion(getOperacionBo().save(oe.getOperacion()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getOperacion()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	@POST
	@Path("/guardarSuboperacion")
	@Produces(MediaType.APPLICATION_JSON)
	public OperacionEnvio guardarSuboperacion(OperacionEnvio oe){
		OperacionEnvio respuesta = new OperacionEnvio();
		if (oe == null || oe.getUsuario() == null
				|| oe.getUsuario().getIdusuario() == null || oe.getUsuario().getIdusuario().isEmpty()
				|| oe.getUsuario().getIdsesionactual() == null || oe.getUsuario().getIdsesionactual().isEmpty()
				|| oe.getSuboperacion() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(),oe.getUsuario().getIdusuario())){
			oe.getSuboperacion().setIdsuboperacion(GeneradorId.generaId(oe.getSuboperacion()));
			oe.getSuboperacion().setIdsesion(oe.getUsuario().getIdsesionactual());
			try{
				respuesta.setSuboperacion(getSuboperacionBo().save(oe.getSuboperacion()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getSuboperacion()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}
	
	@POST
	@Path("/actualizar")
	@Produces(MediaType.APPLICATION_JSON)
	public OperacionEnvio actualizar(OperacionEnvio oe){
		OperacionEnvio respuesta = new OperacionEnvio();
		if (oe == null || oe.getUsuario() == null
				|| oe.getUsuario().getIdusuario() == null || oe.getUsuario().getIdusuario().isEmpty()
				|| oe.getUsuario().getIdsesionactual() == null || oe.getUsuario().getIdsesionactual().isEmpty()
				|| oe.getOperacion() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(),oe.getUsuario().getIdusuario())){
			oe.getOperacion().setIdsesion(oe.getUsuario().getIdsesionactual());
			try{
				respuesta.setOperacion(getOperacionBo().update(oe.getOperacion()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getOperacion()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}	
	
	@POST
	@Path("/actualizarSuboperacion")
	@Produces(MediaType.APPLICATION_JSON)
	public OperacionEnvio actualizarSuboperacion(OperacionEnvio oe){
		OperacionEnvio respuesta = new OperacionEnvio();
		if (oe == null || oe.getUsuario() == null
				|| oe.getUsuario().getIdusuario() == null || oe.getUsuario().getIdusuario().isEmpty()
				|| oe.getUsuario().getIdsesionactual() == null || oe.getUsuario().getIdsesionactual().isEmpty()
				|| oe.getSuboperacion() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(),oe.getUsuario().getIdusuario())){
			oe.getSuboperacion().setIdsesion(oe.getUsuario().getIdsesionactual());
			try{
				respuesta.setSuboperacion(getSuboperacionBo().update(oe.getSuboperacion()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getSuboperacion()==null){
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_ACTUALIZACION);
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
			return respuesta;
		}
		else{
			respuesta.setExito(false);
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			return respuesta;
		}
	}		
	
	@POST
	@Path("/obtenerSuboperacionPorId")
	@Produces(MediaType.APPLICATION_JSON)
	public OperacionEnvio obtenerSuboperacionPorId(OperacionEnvio oe){
		OperacionEnvio respuesta = new OperacionEnvio();
		if (oe == null || oe.getUsuario() == null
				|| oe.getUsuario().getIdusuario() == null || oe.getUsuario().getIdusuario().isEmpty()
				|| oe.getUsuario().getIdsesionactual() == null || oe.getUsuario().getIdsesionactual().isEmpty()
				|| oe.getSuboperacion() == null || oe.getSuboperacion().getIdsuboperacion() == null
				|| oe.getSuboperacion().getIdsuboperacion().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(),oe.getUsuario().getIdusuario())){
			try{
				respuesta.setSuboperacion(getSuboperacionBo().findById(oe.getSuboperacion().getIdsuboperacion()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getSuboperacion()==null){
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
	
	@POST
	@Path("/obtenerPorId")
	@Produces(MediaType.APPLICATION_JSON)
	public OperacionEnvio obtenerPorId(OperacionEnvio oe){
		OperacionEnvio respuesta = new OperacionEnvio();
		if (oe == null || oe.getUsuario() == null
				|| oe.getUsuario().getIdusuario() == null || oe.getUsuario().getIdusuario().isEmpty()
				|| oe.getUsuario().getIdsesionactual() == null || oe.getUsuario().getIdsesionactual().isEmpty()
				|| oe.getOperacion() == null || oe.getOperacion().getIdoperacion() == null
				|| oe.getOperacion().getIdoperacion().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(oe.getUsuario().getIdsesionactual(),oe.getUsuario().getIdusuario())){
			try{
				respuesta.setOperacion(getOperacionBo().findById(oe.getOperacion().getIdoperacion()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getOperacion()==null){
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

	public OperacionBo getOperacionBo() {
		return operacionBo;
	}

	public void setOperacionBo(OperacionBo operacionBo) {
		this.operacionBo = operacionBo;
	}

	public SuboperacionBo getSuboperacionBo() {
		return suboperacionBo;
	}

	public void setSuboperacionBo(SuboperacionBo suboperacionBo) {
		this.suboperacionBo = suboperacionBo;
	}
}
