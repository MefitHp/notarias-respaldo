package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.dato.DatoDocumentoObjeto;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.DocumentoObjeto;

public class DocumentoObjetoEnvio extends GenericEnvio implements Serializable{

	private static final long serialVersionUID = -2159115842618869313L;
	private DocumentoObjeto documento = null;
	private List<DatoDocumentoObjeto> listaCombo = null;
	private Acto acto = null;
	private String txtDocumento;
	private ArrayList<DocumentoObjeto> publicados=null;
	private ArrayList<DocumentoObjeto> noPublicados=null;
	
	public DocumentoObjetoEnvio(){
	
	}
	
	public DocumentoObjeto getDocumento() {
		return documento;
	}
	
	public void setDocumento(DocumentoObjeto documento) {
		this.documento = documento;
	}
	
	public List<DatoDocumentoObjeto> getListaCombo() {
		return listaCombo;
	}
	
	public void setListaCombo(List<DatoDocumentoObjeto> listaCombo) {
		this.listaCombo = listaCombo;
	}
	
	public Acto getActo() {
		return acto;
	}
	
	public void setActo(Acto acto) {
		this.acto = acto;
	}
	public String getTxtDocumento() {
		return txtDocumento;
	}
	public void setTxtDocumento(String txtDocumento) {
		this.txtDocumento = txtDocumento;
	}

	public ArrayList<DocumentoObjeto> getPublicados() {
		return publicados;
	}
	
	public void setPublicados(ArrayList<DocumentoObjeto> publicados) {
		this.publicados = publicados;
	}
	
	public ArrayList<DocumentoObjeto> getNoPublicados() {
		return noPublicados;
	}
	
	public void setNoPublicados(ArrayList<DocumentoObjeto> noPublicados) {
		this.noPublicados = noPublicados;
	}
}
