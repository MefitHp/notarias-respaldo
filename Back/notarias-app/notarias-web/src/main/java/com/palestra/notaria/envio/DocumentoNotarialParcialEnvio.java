package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.modelo.DocumentoNotarialParcial;

public class DocumentoNotarialParcialEnvio extends GenericEnvio implements
		Serializable {

	private static final long serialVersionUID = 4530756056048979610L;
	private DocumentoNotarialParcial documentoParcial= null;
	
	private ArrayList<DocumentoNotarialParcial> documentoParcialList=null;
	
	public ArrayList<DocumentoNotarialParcial> getDocumentoParcialList() {
		return documentoParcialList;
	}
	
	public void setDocumentoParcialList(
			ArrayList<DocumentoNotarialParcial> documentoParcialList) {
		this.documentoParcialList = documentoParcialList;
	}
	
	public DocumentoNotarialParcial getDocumentoParcial() {
		return documentoParcial;
	}
	public void setDocumentoParcial(DocumentoNotarialParcial documentoParcial) {
		this.documentoParcial = documentoParcial;
	}
	
}
