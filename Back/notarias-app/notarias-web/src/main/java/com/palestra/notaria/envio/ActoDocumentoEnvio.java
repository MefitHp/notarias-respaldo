package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.DocumentoExpediente;

public class ActoDocumentoEnvio extends GenericEnvio {
	
	private ActoDocumento actoDocumento=null;
	
	private ArrayList<ActoDocumento> actoDocumentos  = null;
	
	private ArrayList<DocumentoExpediente> docExpedienteList=null; 
	
	private DatoActoDocumento datoActoDoc = null;
	
	public DatoActoDocumento getDatoActoDoc() {
		return datoActoDoc;
	}
	
	public void setDatoActoDoc(DatoActoDocumento datoActoDoc) {
		this.datoActoDoc = datoActoDoc;
	}
	
	public ActoDocumento getActoDocumento() {
		return actoDocumento;
	}
	
	public void setActoDocumento(ActoDocumento actoDocumento) {
		this.actoDocumento = actoDocumento;
	}
	
	public ArrayList<DocumentoExpediente> getDocExpedienteList() {
		return docExpedienteList;
	}
	
	public void setDocExpedienteList(
			ArrayList<DocumentoExpediente> docExpedienteList) {
		this.docExpedienteList = docExpedienteList;
	}


	public ArrayList<ActoDocumento> getActoDocumentos() {
		return actoDocumentos;
	}

	public void setActoDocumentos(ArrayList<ActoDocumento> actoDocumentos) {
		this.actoDocumentos = actoDocumentos;
	}
}
