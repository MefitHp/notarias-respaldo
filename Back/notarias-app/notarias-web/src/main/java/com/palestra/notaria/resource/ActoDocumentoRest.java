package com.palestra.notaria.resource;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.DocumentoExpedienteBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.ActoDocumentoBoImpl;
import com.palestra.notaria.bo.impl.DocumentoExpedienteBoImpl;
import com.palestra.notaria.dao.UsuarioDao;
import com.palestra.notaria.dao.impl.UsuarioDaoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.ActoDocumentoEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.DocumentoExpediente;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/actoDocumento")
public class ActoDocumentoRest {
	private ActoDocumentoBo actoDocumentoBo;
	
	private DocumentoExpedienteBo docExpedienteBo;
	
	public ActoDocumentoRest(){
		this.actoDocumentoBo = new ActoDocumentoBoImpl();
		this.docExpedienteBo = new DocumentoExpedienteBoImpl();
	}
	
	@POST
	@Path("/guardar")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoDocumentoEnvio guardar(ActoDocumentoEnvio ae){
		ActoDocumentoEnvio respuesta = new ActoDocumentoEnvio();
//		if(NotariaUtils.requeridosUsuario(ae) || ae.getDocExpedienteList() == null){
		if(NotariaUtils.faltanRequeridosUsuario(ae)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){
			ArrayList<DocumentoExpediente> resultList = new ArrayList<DocumentoExpediente>();
			try{
				ActoDocumento actoDoc = new ActoDocumento();
				actoDoc.setIdactodoc(GeneradorId.generaId(actoDoc));
				actoDoc.setActo(ae.getDocExpedienteList().get(0).getActo());
				actoDoc.setDocumento(ae.getDocExpedienteList().get(0).getDocumento());
				actoDoc.setIsentregado(Boolean.FALSE);
				actoDoc.setIssolicitado(Boolean.FALSE);
				actoDoc.setIdsesion(ae.getUsuario().getIdsesionactual());
				respuesta.setActoDocumento(getActoDocumentoBo().save(actoDoc));
				for(DocumentoExpediente docExp:ae.getDocExpedienteList()){
					docExp.setIddocumentoexpediente(GeneradorId.generaId(docExp));
					docExp.setIdsesion(ae.getUsuario().getIdsesionactual());
					resultList.add(getDocExpedienteBo().save(docExp));
				}
				respuesta.setDocExpedienteList(resultList);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	@POST
	@Path("/guardarFormatoPdf")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoDocumentoEnvio guardarFormatoPdf(ActoDocumentoEnvio ae){
		ActoDocumentoEnvio respuesta = new ActoDocumentoEnvio();
//		if(NotariaUtils.requeridosUsuario(ae) || ae.getDocExpedienteList() == null){
		if(NotariaUtils.faltanRequeridosUsuario(ae)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){
			try{
				saveActoDoc(ae.getActoDocumento(),ae.getUsuario());
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	/**
	 * @param ae
	 * @throws NotariaException
	 */
	private void saveActoDoc(ActoDocumento ad,Usuario user) throws NotariaException {
		ActoDocumento actoDoc = ad;
		actoDoc.setIdactodoc(GeneradorId.generaId(actoDoc));
		actoDoc.setIsentregado(Boolean.FALSE);
		actoDoc.setIssolicitado(Boolean.FALSE);
		
		// VERIFICO QUE SEA DIM O ANEXO 5 PARA VALIDAR QUE YA SE TIENE UNO EN ESTE ACTO
		Boolean hasDim = ad.getFormatoPdf().getDstitulo().contains("DIM");
		Boolean hasAnexo5 = ad.getFormatoPdf().getDstitulo().contains("Anexo 5");
		
		if(hasDim || hasAnexo5){
			ActoBo actobo = new ActoBoImpl();
			Acto acto = actobo.findById(ad.getActo().getIdacto());
			if(hasDim){
				acto.setHasdim(hasDim);
			}
			if(hasAnexo5){
				acto.setHasanexo5(hasAnexo5);
			}
			actobo.update(acto);
			
		}
		actoDoc.setIdsesion(user.getIdsesionactual());		
		getActoDocumentoBo().save(actoDoc);
	}
	
	@POST
	@Path("/guardarFormatosPdf")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoDocumentoEnvio guardarFormatosPdf(ActoDocumentoEnvio ae){
		ActoDocumentoEnvio respuesta = new ActoDocumentoEnvio();
//		if(NotariaUtils.requeridosUsuario(ae) || ae.getDocExpedienteList() == null){
		if(NotariaUtils.faltanRequeridosUsuario(ae) || !(ae.getActoDocumentos().size()>0)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){
			try{
				for(ActoDocumento ad:ae.getActoDocumentos()){
					saveActoDoc(ad, ae.getUsuario());
				}
				respuesta.setExito(true);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	
	@POST
	@Path("/eliminarFormatoPdf")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoDocumentoEnvio eliminarFormatoPdf(ActoDocumentoEnvio ae){
		ActoDocumentoEnvio respuesta = new ActoDocumentoEnvio();
//		if(NotariaUtils.requeridosUsuario(ae) || ae.getDocExpedienteList() == null){
		if(NotariaUtils.faltanRequeridosUsuario(ae)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){
			try{
				ActoDocumento actoDoc = ae.getActoDocumento();
				getActoDocumentoBo().borrar(actoDoc);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	
	@POST
	@Path("/setNotario")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoDocumentoEnvio setNotario(ActoDocumentoEnvio ae){	
		ActoDocumentoEnvio respuesta = new ActoDocumentoEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(ae)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){
			try {
				ActoDocumento actoDocEncontrado = getActoDocumentoBo().findById(ae.getDatoActoDoc().getIdactodoc());
				System.out.println("====>     El valor para switchNotario es: "+ae.getDatoActoDoc().getSwitchNotario());
				if(ae.getDatoActoDoc().getSwitchNotario()){
					// ---> cafaray 300614 Cambio en el Switch de notario, aqui se hace por el notaria en la BD
					//Usuario nuevoNotario = getActoDocumentoBo().switchNotario(actoDocEncontrado.getNotario().getIdusuario());
					// ahora se hace por el que se manda desde pantalla. Obedece a nueva funcionalidad de settear a todos
					// los documentos el mismo notario. Es una barbaridad, pero desde el front se envian las iniciales y no el objeto
					// por ende hay buscar al usuario a través de sus iniciales.
					System.out.println("====>     Entra a la nueva función de búsqueda para sustitucion");
					UsuarioDao usuarioDao = new UsuarioDaoImpl();
					Usuario objNotario = usuarioDao.findByIniciales(ae.getDatoActoDoc().getNotario());					
					Usuario nuevoNotario = getActoDocumentoBo().switchNotario(objNotario!=null?objNotario.getIdusuario():"");
					System.out.println("=====>    Sale con "+objNotario.getDsiniciales() +" --> "+ nuevoNotario.getDsiniciales());
					// <--- cafaray 300614
					actoDocEncontrado.setNotario(nuevoNotario!=null?nuevoNotario:null);
				} /*else{
					System.out.println("====>     Entro como si wl Switch fuese false: "+ae.getDatoActoDoc().getSwitchNotario());
					actoDocEncontrado.setNotario(ae.getDatoActoDoc().getObjNotario());
				}*/
				
				if(getActoDocumentoBo().update(actoDocEncontrado) != null){
					respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
					respuesta.setExito(true);
					return respuesta;
				}else{
					respuesta.setEstatus("ocurrio un error al poner el notario");
					respuesta.setExito(false);
					return respuesta;
				}
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus("ocurrio un error al poner el notario "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/setGestor")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoDocumentoEnvio setGestor(ActoDocumentoEnvio ae){	
		ActoDocumentoEnvio respuesta = new ActoDocumentoEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(ae)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){
			try {
				ActoDocumento actoDocEncontrado = getActoDocumentoBo().findById(ae.getDatoActoDoc().getIdactodoc());
				actoDocEncontrado.setGestor(ae.getDatoActoDoc().getGestor());
				
				if(getActoDocumentoBo().update(actoDocEncontrado) != null){
					respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
					respuesta.setExito(true);
					return respuesta;
				}else{
					respuesta.setEstatus("ocurrio un error al asignar el gestor");
					respuesta.setExito(false);
					return respuesta;
				}
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus("ocurrio un error al asignar el gestor "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/setValuador")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoDocumentoEnvio setValuador(ActoDocumentoEnvio ae){	
		ActoDocumentoEnvio respuesta = new ActoDocumentoEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(ae)){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){
			try {
				ActoDocumento actoDocEncontrado = getActoDocumentoBo().findById(ae.getDatoActoDoc().getIdactodoc());
				
				
				actoDocEncontrado.setValuador(ae.getDatoActoDoc().getValuador());
				
				if(getActoDocumentoBo().update(actoDocEncontrado) != null){
					respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
					respuesta.setExito(true);
					return respuesta;
				}else{
					respuesta.setEstatus("ocurrio un error al asignar el valuador");
					respuesta.setExito(false);
					return respuesta;
				}
			} catch (NotariaException e) {
				e.printStackTrace(System.out);
				respuesta.setEstatus("ocurrio un error al asignar el valuador "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}	
	
	@POST
	@Path("/actualizar")
	@Produces(MediaType.APPLICATION_JSON)
	public ActoDocumentoEnvio actualizar(ActoDocumentoEnvio ae){
		ActoDocumentoEnvio respuesta = new ActoDocumentoEnvio();
		if(NotariaUtils.faltanRequeridosUsuario(ae) || ae.getDocExpedienteList() == null){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(), ae.getUsuario().getIdusuario())){
			if(ae.getActoDocumento().getIsentregado() || ae.getActoDocumento().getIssolicitado()){
				respuesta.setEstatus("No se puede modificar porque ya se encuentra aprobado o aceptado");
				respuesta.setExito(false);
				return respuesta;
			}
			ArrayList<DocumentoExpediente> resultList = new ArrayList<DocumentoExpediente>();
			try{
				
				ae.getActoDocumento().setIdsesion(ae.getUsuario().getIdsesionactual());
				respuesta.setActoDocumento(getActoDocumentoBo().update(ae.getActoDocumento()));
				for(DocumentoExpediente docExp:ae.getDocExpedienteList()){
					docExp.setIdsesion(ae.getUsuario().getIdsesionactual());
					resultList.add(getDocExpedienteBo().update(docExp));
				}
				respuesta.setDocExpedienteList(resultList);
				respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
				respuesta.setExito(true);
				return respuesta;
			}catch(Exception e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO+" "+e.getMessage());
				respuesta.setExito(true);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}	
	
	public ActoDocumentoBo getActoDocumentoBo() {
		return actoDocumentoBo;
	}
	
	public DocumentoExpedienteBo getDocExpedienteBo() {
		return docExpedienteBo;
	}
}
