package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.DocumentoBo;
import com.palestra.notaria.bo.DocumentoSuboperacionBo;
import com.palestra.notaria.bo.FormatoPDFBO;
import com.palestra.notaria.bo.impl.ActoDocumentoBoImpl;
import com.palestra.notaria.bo.impl.DocumentoBoImpl;
import com.palestra.notaria.bo.impl.DocumentoSuboperacionBoImpl;
import com.palestra.notaria.bo.impl.FormatoPDFBOImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.FormatoWrapper;
import com.palestra.notaria.envio.DocumentoSuboperacionEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.Documento;
import com.palestra.notaria.modelo.DocumentoSuboperacion;
import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.util.NotariaUtils;

/**
 * @author omarete
 *
 */
@Path("/documentoSuboperacion")
public class DocumentoSuboperacionRest {

	private DocumentoSuboperacionBo docSubopBo;
	
	private DocumentoBo docBo;
	private FormatoPDFBO pdfBo;
	
	public DocumentoSuboperacionRest(){
		this.docSubopBo = new DocumentoSuboperacionBoImpl();
		this.docBo = new DocumentoBoImpl();
		this.pdfBo = new FormatoPDFBOImpl();
	}
	
/**
 * Metodo que gestiona los documentos por default que tiene una suboperacion
 * 
 * @author omarete
 *
 */	
	@POST
	@Path("/gestorDocumentos")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoSuboperacionEnvio gestorDocumentos(DocumentoSuboperacionEnvio dse){
		DocumentoSuboperacionEnvio respuesta = new DocumentoSuboperacionEnvio();
		if (dse == null || dse.getUsuario() == null
				|| dse.getUsuario().getIdusuario() == null || dse.getUsuario().getIdusuario().isEmpty()
				|| dse.getUsuario().getIdsesionactual() == null || dse.getUsuario().getIdsesionactual().isEmpty()
				|| dse.getLocalidad() == null || dse.getLocalidad().getIdelemento().isEmpty()
				|| dse.getLocalidad().getIdelemento() == null
				|| dse.getSuboperacion() == null || dse.getSuboperacion().getIdsuboperacion().isEmpty()
				|| dse.getSuboperacion().getIdsuboperacion() == null) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(dse.getUsuario().getIdsesionactual(),dse.getUsuario().getIdusuario())){
			try{
				List<DocumentoSuboperacion> docSuboperacionListSaved = getDocSubopBo().listarPreviosPorSubopAndLocalidad(dse.getLocalidad().getIdelemento(), dse.getSuboperacion().getIdsuboperacion());
//				obtener los documentos de la lista de DocumentoSuboperacion
				List<String> docListSaved = new ArrayList<String>();
				List<String> newList = new ArrayList<String>();
				for(DocumentoSuboperacion doc:docSuboperacionListSaved){
					if(doc.getDocumento()!= null){
						docListSaved.add(doc.getDocumento().getIddocumento());
					}else{
						docListSaved.add(doc.getFormatopdf().getIdentificador());
					}
				}
				
				for(FormatoWrapper docNew:dse.getDocumentosList()){
					newList.add(docNew.getIddocumento());
				}
				
				if(docListSaved.isEmpty()){
					System.out.println("no existen docs guardados en BD, se guarda la lista que llega");
					for(FormatoWrapper docNew:dse.getDocumentosList()){
//						newList.add(docNew.getIddocumento());
						
						DocumentoSuboperacion docSubop = new DocumentoSuboperacion();
						docSubop.setLocalidad(dse.getLocalidad());
						docSubop.setSuboperacion(dse.getSuboperacion());
						docSubop.setIsgestionado(docNew.getIsgestionado());
						docSubop.setInorden(docNew.getInorden());
						Documento docFound = getDocBo().findById(docNew.getIddocumento());
						FormatoPDF pdfFound = getPdfBo().findById(docNew.getIddocumento());
						if(docFound != null)
							docSubop.setDocumento(docFound);
						else if(pdfFound != null)
							docSubop.setFormatopdf(pdfFound);
						else
							throw new NotariaException("El id de documento dado no existe");
						
						getDocSubopBo().save(docSubop);
					}
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
					
					
				}
				
//				if(docListSaved.isEmpty()){
//					System.out.println("no existen docs guardados en BD, se guarda la lista que llega");
//					for(String iddoc:newList){
//						DocumentoSuboperacion docSubop = new DocumentoSuboperacion();
//						docSubop.setLocalidad(dse.getLocalidad());
//						docSubop.setSuboperacion(dse.getSuboperacion());
//						docSubop.setIsgestionado(dse.get)
//						Documento docFound = getDocBo().findById(iddoc);
//						FormatoPDF pdfFound = getPdfBo().findById(iddoc);
//						if(docFound != null)
//							docSubop.setDocumento(docFound);
//						else if(pdfFound != null)
//							docSubop.setFormatopdf(pdfFound);
//						else
//							throw new NotariaException("El id de documento dado no existe");
//						
//						getDocSubopBo().save(docSubop);
//					}
//					respuesta.setEstatus("guardado correcto");
//					respuesta.setExito(true);
//					return respuesta;
//				}
				else if(newList.isEmpty()){
					System.out.println("Se estan quitando los docs guardados");
					for(DocumentoSuboperacion doc:docSuboperacionListSaved){
						getDocSubopBo().delete(doc);
					}
					respuesta.setEstatus("Se eliminaron los elementos correctamente");
					respuesta.setExito(true);
					return respuesta;
				}else if(!docListSaved.equals(newList)){

//					for(String doc:newList){
					for(FormatoWrapper doc:dse.getDocumentosList()){
						if(!docListSaved.contains(doc.getIddocumento())){
							System.out.println("Se guardan los nuevos elementos");
							DocumentoSuboperacion docSubop = new DocumentoSuboperacion();
							docSubop.setLocalidad(dse.getLocalidad());
							docSubop.setSuboperacion(dse.getSuboperacion());
							docSubop.setIsgestionado(doc.getIsgestionado());
							docSubop.setInorden(doc.getInorden());							
							Documento docFound = getDocBo().findById(doc.getIddocumento());
							FormatoPDF pdfFound = getPdfBo().findById(doc.getIddocumento());
							if(docFound != null)
								docSubop.setDocumento(docFound);
							else if(pdfFound != null)
								docSubop.setFormatopdf(pdfFound);
							else
								throw new NotariaException("El id de documento dado no existe");
							try {
								DocumentoSuboperacion nuevo = getDocSubopBo().save(docSubop); 
								if(nuevo!=null){
									docListSaved.add(doc.getIddocumento());
								} else {
									System.out.println("Algo ocurrió y no se persistió el elemento "+doc.getIddocumento()+".");								
								}
							}catch(NotariaException e){
								e.printStackTrace(System.out);							   
							}
						}
					}
					for(String doc:docListSaved){
						System.out.println("DOCUMENTO LISTADO"+doc);
						System.out.println("DOCUMENTO SAVED"+docListSaved.size()+"DOCUMENTO NEW LIST"+newList.size());
						
						if(!newList.contains(doc)){
							System.out.println("Se eliminan los elementos que se borraron");
							Documento docFound = getDocBo().findById(doc);
							FormatoPDF pdfFound = getPdfBo().findById(doc);
							DocumentoSuboperacion docSubop = docSubopBo.encuentraDocumentoSubOperacion(
									dse.getSuboperacion().getIdsuboperacion(), dse.getLocalidad().getIdelemento(), 
									docFound!=null?docFound.getIddocumento():null, pdfFound!=null?pdfFound.getIdentificador():null);
							if(docSubop!=null){
								getDocSubopBo().delete(docSubop);	
							}else{
								System.out.printf("DOCUMENTOSUBOPERACION:::No se encontro elemento %s %s %s %s para eliminar.%n",
										dse.getSuboperacion().getIdsuboperacion(), dse.getLocalidad().getIdelemento(), 
										docFound!=null?docFound.getIddocumento():null, pdfFound!=null?pdfFound.getIdentificador():null);
							}
//							DocumentoSuboperacion docSubop = new DocumentoSuboperacion();
//							docSubop.setLocalidad(dse.getLocalidad());
//							docSubop.setSuboperacion(dse.getSuboperacion());
//							Documento docFound = getDocBo().findById(doc);
//							FormatoPDF pdfFound = getPdfBo().findById(doc);
//							if(docFound != null){
//								docSubop.setDocumento(docFound);
//							}else if(pdfFound != null){
//								docSubop.setFormatopdf(pdfFound);
//							}else{
//								throw new NotariaException("El id de documento dado no existe");
//							}
//							getDocSubopBo().delete(docSubop);
						}
					}
					System.out.println("DOCUMENTO SAVED"+docListSaved);
					System.out.println("DOCUMENTO NEW LIST"+newList);
					
					respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
				}else{
					System.out.println("parece ser la misma lista!");
					respuesta.setEstatus("nada que hacer");
					respuesta.setExito(true);
					return respuesta;
				}
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
	@Path("/listarPorSuboperacion")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoSuboperacionEnvio listarPorSuboperacion(DocumentoSuboperacionEnvio dse){
		DocumentoSuboperacionEnvio respuesta = new DocumentoSuboperacionEnvio();
		if (dse == null || dse.getUsuario() == null
				|| dse.getUsuario().getIdusuario() == null || dse.getUsuario().getIdusuario().isEmpty()
				|| dse.getUsuario().getIdsesionactual() == null || dse.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(dse.getUsuario().getIdsesionactual(),dse.getUsuario().getIdusuario())){
			try{
				List<DocumentoSuboperacion> docs=null;
				if(dse.getTramite()==null){
					docs = getDocSubopBo().listarPreviosPorSubopAndLocalidad(dse.getLocalidad().getIdelemento(), dse.getSuboperacion().getIdsuboperacion());
				}else{
					docs = getDocSubopBo().listarPreviosPorSubopAndLocalidad(dse.getTramite().getLocacion().getIdelemento(), dse.getSuboperacion().getIdsuboperacion());
				}
//				List<DocumentoSuboperacion> previosList = new ArrayList<DocumentoSuboperacion>();
//				List<DocumentoSuboperacion> posterioresList = new ArrayList<DocumentoSuboperacion>();
				for(DocumentoSuboperacion ds:docs){
					if(ds.getDocumento()!=null){
						if(ds.getDocumento().getTipodoc().getIdelemento().equals(Constantes.ID_DOCUMENTO_PREVIO)){
							FormatoWrapper fw = new FormatoWrapper();
							fw.setDstitulo(ds.getDocumento().getDstitulo());
							fw.setDsdescripcion(ds.getDocumento().getDsdescripcion());
							fw.setIddocumento(ds.getDocumento().getIddocumento());
							fw.setTipodoc(ds.getDocumento().getTipodoc());
							fw.setInorden(ds.getInorden());
							respuesta.getPreviosList().add(fw);
						}else{
							FormatoWrapper fw = new FormatoWrapper();
							fw.setDstitulo(ds.getDocumento().getDstitulo());
							fw.setDsdescripcion(ds.getDocumento().getDsdescripcion());
							fw.setIddocumento(ds.getDocumento().getIddocumento());
							fw.setTipodoc(ds.getDocumento().getTipodoc());
							fw.setInorden(ds.getInorden());
							respuesta.getPosterioresList().add(fw);
						}
					}else{
						if(ds.getFormatopdf().getTipodoc().getIdelemento().equals(Constantes.ID_DOCUMENTO_PREVIO)){
							FormatoWrapper fw = new FormatoWrapper();
							fw.setDstitulo(ds.getFormatopdf().getDstitulo());
							fw.setDsdescripcion(ds.getFormatopdf().getDsdescripcion());
							fw.setIddocumento(ds.getFormatopdf().getIdentificador());
							fw.setTipodoc(ds.getFormatopdf().getTipodoc());
							fw.setInorden(ds.getInorden());
							respuesta.getPreviosList().add(fw);
						}else{
							FormatoWrapper fw = new FormatoWrapper();
							fw.setDstitulo(ds.getFormatopdf().getDstitulo());
							fw.setDsdescripcion(ds.getFormatopdf().getDsdescripcion());
							fw.setIddocumento(ds.getFormatopdf().getIdentificador());
							fw.setTipodoc(ds.getFormatopdf().getTipodoc());
							fw.setInorden(ds.getInorden());
							respuesta.getPosterioresList().add(fw);
						}
					}
				}
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
	@Path("/listarPosterioresPorSuboperacion")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoSuboperacionEnvio listarPosterioresPorSuboperacion(DocumentoSuboperacionEnvio dse){
		DocumentoSuboperacionEnvio respuesta = new DocumentoSuboperacionEnvio();
		if (dse == null || dse.getUsuario() == null
				|| dse.getUsuario().getIdusuario() == null || dse.getUsuario().getIdusuario().isEmpty()
				|| dse.getUsuario().getIdsesionactual() == null || dse.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(dse.getUsuario().getIdsesionactual(),dse.getUsuario().getIdusuario())){
			try{
				List<DocumentoSuboperacion> docs=getDocSubopBo().listarPosterioresPorSubopAndLocalidad(dse.getLocalidad().getIdelemento(), dse.getSuboperacion().getIdsuboperacion());
				ArrayList<DocumentoSuboperacion> docsData = new ArrayList<DocumentoSuboperacion>();
				for(DocumentoSuboperacion doc: docs){
					docsData.add(doc);
				}
//				List<DocumentoSuboperacion> previosList = new ArrayList<DocumentoSuboperacion>();
//				List<DocumentoSuboperacion> posterioresList = new ArrayList<DocumentoSuboperacion>();
				respuesta.setDocSubopList(docsData);
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
	@Path("/listarPosterioresPorEscrituraSuboperaciones")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoSuboperacionEnvio listarPosterioresPorEscritura(DocumentoSuboperacionEnvio dse){
		DocumentoSuboperacionEnvio respuesta = new DocumentoSuboperacionEnvio();
		if (dse == null || dse.getUsuario() == null
				|| dse.getUsuario().getIdusuario() == null || dse.getUsuario().getIdusuario().isEmpty()
				|| dse.getUsuario().getIdsesionactual() == null || dse.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(dse.getUsuario().getIdsesionactual(),dse.getUsuario().getIdusuario())){
			try{
				
				
				List<DocumentoSuboperacion> docs=getDocSubopBo().listarPosterioresPorEscritura(dse.getIdescritura(),dse.getIdlocalidad());
				ArrayList<DocumentoSuboperacion> docsData = new ArrayList<DocumentoSuboperacion>();
				
				for(DocumentoSuboperacion doc: docs){
					docsData.add(doc);
				}

				respuesta.setDocSubopList(docsData);
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
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public DocumentoSuboperacionEnvio listar(DocumentoSuboperacionEnvio dse){
		DocumentoSuboperacionEnvio respuesta = new DocumentoSuboperacionEnvio();
		if (dse == null || dse.getUsuario() == null
				|| dse.getUsuario().getIdusuario() == null || dse.getUsuario().getIdusuario().isEmpty()
				|| dse.getUsuario().getIdsesionactual() == null || dse.getUsuario().getIdsesionactual().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(dse.getUsuario().getIdsesionactual(),dse.getUsuario().getIdusuario())){
			try{
				respuesta.setDocSubopList((ArrayList<DocumentoSuboperacion>)getDocSubopBo().findAll());

				respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
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
	
	public DocumentoSuboperacionBo getDocSubopBo() {
		return docSubopBo;
	}
	public DocumentoBo getDocBo() {
		return docBo;
	}
	public FormatoPDFBO getPdfBo() {
		return pdfBo;
	}
}
