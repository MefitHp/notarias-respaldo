package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.dato.FormatoWrapper;
import com.palestra.notaria.modelo.DocumentoSuboperacion;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Suboperacion;
import com.palestra.notaria.modelo.Tramite;

public class DocumentoSuboperacionEnvio extends GenericEnvio {

	private ArrayList<DocumentoSuboperacion> docSubopList=null;
	
	private ArrayList<FormatoWrapper> previosList=new ArrayList<FormatoWrapper>();
	
	private ArrayList<FormatoWrapper> posterioresList=new ArrayList<FormatoWrapper>();
		
	private ArrayList<FormatoWrapper> documentosList = null;
	
	private DocumentoSuboperacion docSubop=null;
	
	private Suboperacion suboperacion=null;
	
	private ElementoCatalogo localidad = null;
	
	private Tramite tramite = null;
	
	private String idexpediente = null;
	private String idescritura = null;
	private String idlocalidad = null;
	
	public Tramite getTramite() {
		return tramite;
	}
	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}
	public ArrayList<FormatoWrapper> getDocumentosList() {
		return documentosList;
	}
	public void setDocumentosList(ArrayList<FormatoWrapper> documentosList) {
		this.documentosList = documentosList;
	}
	public ArrayList<FormatoWrapper> getPreviosList() {
		return previosList;
	}
	public void setPreviosList(ArrayList<FormatoWrapper> previosList) {
		this.previosList = previosList;
	}
	public ArrayList<FormatoWrapper> getPosterioresList() {
		return posterioresList;
	}
	public void setPosterioresList(ArrayList<FormatoWrapper> posterioresList) {
		this.posterioresList = posterioresList;
	}
	public ElementoCatalogo getLocalidad() {
		return localidad;
	}
	public void setLocalidad(ElementoCatalogo localidad) {
		this.localidad = localidad;
	}
	public Suboperacion getSuboperacion() {
		return suboperacion;
	}
	public void setSuboperacion(Suboperacion suboperacion) {
		this.suboperacion = suboperacion;
	}
	
	public DocumentoSuboperacion getDocSubop() {
		return docSubop;
	}
	public void setDocSubop(DocumentoSuboperacion docSubop) {
		this.docSubop = docSubop;
	}
	
	public ArrayList<DocumentoSuboperacion> getDocSubopList() {
		return docSubopList;
	}
	
	public void setDocSubopList(ArrayList<DocumentoSuboperacion> docSubopList) {
		this.docSubopList = docSubopList;
	}
	public String getIdescritura() {
		return idescritura;
	}
	public void setIdescritura(String idescritura) {
		this.idescritura = idescritura;
	}
	public String getIdlocalidad() {
		return idlocalidad;
	}
	public void setIdlocalidad(String idlocalidad) {
		this.idlocalidad = idlocalidad;
	}
	public String getIdexpediente() {
		return idexpediente;
	}
	public void setIdexpediente(String idexpediente) {
		this.idexpediente = idexpediente;
	}
}
