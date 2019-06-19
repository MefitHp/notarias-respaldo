package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.dato.FormatoWrapper;
import com.palestra.notaria.modelo.Documento;
import com.palestra.notaria.modelo.DocumentoEscaneado;
import com.palestra.notaria.modelo.DocumentoVersiones;
import com.palestra.notaria.modelo.DocumentosOriginales;

public class DocumentoEnvio extends GenericEnvio{
	
	private String idExpediente = null;
	private String idacto = null;
	private String nombreDoc = null;
	private ArrayList<DatoActoDocumento> listaPrevios;
	private ArrayList<DatoActoDocumento> listaPosteriores;
	private ArrayList<DocumentoEscaneado> listaEscaneados;
	private ArrayList<DocumentosOriginales> listaOrigiales;
	private DocumentosOriginales original;
	private DocumentoEscaneado documentoEscaneado;
	private String rutaTemDocEscaneado= null;
	private String rutaArchivoDescarga = null;
	private String idactodoc = null;
	private DocumentosOriginales documentoOriginal= null;
	private List<DocumentosOriginales> listaDocumentosOriginales = null;
	private Boolean hasdim;
	private Boolean hasanexo5;
	
	
	private Documento documento=null;
	
	private DocumentoVersiones documentoVersiones=null;
	
	private ArrayList<DocumentoVersiones> docNoPublicadosList=null;
	
	private ArrayList<FormatoWrapper> documentoList=new ArrayList<FormatoWrapper>();
	
	private ArrayList<Documento> docPublicadosList=null;
	
	public ArrayList<Documento> getDocPublicadosList() {
		return docPublicadosList;
	}
	public void setDocPublicadosList(ArrayList<Documento> docPublicadosList) {
		this.docPublicadosList = docPublicadosList;
	}
	
	public DocumentoVersiones getDocumentoVersiones() {
		return documentoVersiones;
	}
	
	public ArrayList<DocumentoVersiones> getDocNoPublicadosList() {
		return docNoPublicadosList;
	}
	
	public void setDocNoPublicadosList(
			ArrayList<DocumentoVersiones> docNoPublicadosList) {
		this.docNoPublicadosList = docNoPublicadosList;
	}
	
	public void setDocumentoVersiones(DocumentoVersiones documento) {
		this.documentoVersiones = documento;
	}
	
	public Documento getDocumento() {
		return documento;
	}
	
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	
	public ArrayList<FormatoWrapper> getDocumentoList() {
		return documentoList;
	}
	public void setDocumentoList(ArrayList<FormatoWrapper> documentoList) {
		this.documentoList = documentoList;
	}
	public ArrayList<DatoActoDocumento> getListaPrevios() {
		return listaPrevios;
	}
	public void setListaPrevios(ArrayList<DatoActoDocumento> listaPrevios) {
		this.listaPrevios = listaPrevios;
	}
	public ArrayList<DatoActoDocumento> getListaPosteriores() {
		return listaPosteriores;
	}
	public void setListaPosteriores(ArrayList<DatoActoDocumento> listaPosteriores) {
		this.listaPosteriores = listaPosteriores;
	}
	public ArrayList<DocumentoEscaneado> getListaEscaneados() {
		return listaEscaneados;
	}
	public void setListaEscaneados(ArrayList<DocumentoEscaneado> listaEscaneados) {
		this.listaEscaneados = listaEscaneados;
	}
	public ArrayList<DocumentosOriginales> getListaOrigiales() {
		return listaOrigiales;
	}
	public void setListaOrigiales(ArrayList<DocumentosOriginales> listaOrigiales) {
		this.listaOrigiales = listaOrigiales;
	}
	public DocumentosOriginales getOriginal() {
		return original;
	}
	public void setOriginal(DocumentosOriginales original) {
		this.original = original;
	}
	public DocumentoEscaneado getDocumentoEscaneado() {
		return documentoEscaneado;
	}
	public void setDocumentoEscaneado(DocumentoEscaneado documentoEscaneado) {
		this.documentoEscaneado = documentoEscaneado;
	}
	public String getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}
	public String getIdacto() {
		return idacto;
	}
	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}
	public String getRutaTemDocEscaneado() {
		return rutaTemDocEscaneado;
	}
	public void setRutaTemDocEscaneado(String rutaTemDocEscaneado) {
		this.rutaTemDocEscaneado = rutaTemDocEscaneado;
	}
	public String getRutaArchivoDescarga() {
		return rutaArchivoDescarga;
	}
	public void setRutaArchivoDescarga(String rutaArchivoDescarga) {
		this.rutaArchivoDescarga = rutaArchivoDescarga;
	}
	public String getIdactodoc() {
		return idactodoc;
	}
	public void setIdactodoc(String idactodoc) {
		this.idactodoc = idactodoc;
	}
	public DocumentosOriginales getDocumentoOriginal() {
		return documentoOriginal;
	}
	public void setDocumentoOriginal(DocumentosOriginales documentoOriginal) {
		this.documentoOriginal = documentoOriginal;
	}
	public List<DocumentosOriginales> getListaDocumentosOriginales() {
		return listaDocumentosOriginales;
	}
	public void setListaDocumentosOriginales(
			List<DocumentosOriginales> listaDocumentosOriginales) {
		this.listaDocumentosOriginales = listaDocumentosOriginales;
	}
	
	public Boolean getHasdim() {
		return hasdim;
	}
	public void setHasdim(Boolean hasdim) {
		this.hasdim = hasdim;
	}
	public Boolean getHasanexo5() {
		return hasanexo5;
	}
	public void setHasanexo5(Boolean hasanexo5) {
		this.hasanexo5 = hasanexo5;
	}
	public String getNombreDoc() {
		return nombreDoc;
	}
	public void setNombreDoc(String nombreDoc) {
		this.nombreDoc = nombreDoc;
	}
}
