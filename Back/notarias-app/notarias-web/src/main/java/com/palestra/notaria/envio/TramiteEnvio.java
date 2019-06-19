package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.palestra.notaria.dato.DatoCompareciente;
import com.palestra.notaria.dato.DatoDocumentoTarjeton;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.Contacto;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Tramite;

public class TramiteEnvio extends GenericEnvio{

	private Tramite tramite;
	private List<Tramite> lista;
	private List<Comentario> comentariosExpediente;
	private Expediente expediente;
	private String idTramite;
	private Contacto contacto;
	private ArrayList<DatoCompareciente> comparecientesCompletos;
	private List<DatoDocumentoTarjeton> documentos;
	private LinkedHashMap<String, String> valoresTarjeton;
	private LinkedHashMap<String,LinkedHashMap<String,String>> datosFormulariosTarjeton;
	private List<Acto> actos;
	
	private String directorio = null;
	
	
	
	public Tramite getTramite() {
		return tramite;
	}
	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}
	public List<Tramite> getLista() {
		return lista;
	}
	public void setLista(List<Tramite> lista) {
		this.lista = lista;
	}
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	public String getIdTramite() {
		return idTramite;
	}
	public void setIdTramite(String idTramite) {
		this.idTramite = idTramite;
	}
	
	
	public List<Comentario> getComentariosExpediente() {
		return comentariosExpediente;
	}
	public void setComentariosExpediente(List<Comentario> comentariosExpediente) {
		this.comentariosExpediente = comentariosExpediente;
	}
	public String getDirectorio() {
		return directorio;
	}
	public void setDirectorio(String directorio) {
		this.directorio = directorio;
	}
	
	public Contacto getContacto() {
		return contacto;
	}
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	
	

	
	
	public List<DatoDocumentoTarjeton> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(List<DatoDocumentoTarjeton> documentos) {
		if(this.documentos!=null){
			this.documentos.addAll(documentos);
		}else{
			this.documentos = documentos;
		}
	}
	
	
	public List<Acto> getActos() {
		return actos;
	}
	public void setActos(List<Acto> actos) {
		this.actos = actos;
	}
	public LinkedHashMap<String, String> getValoresTarjeton() {
		return valoresTarjeton;
	}
	public void setValoresTarjeton(LinkedHashMap<String, String> valoresTarjeton) {
		this.valoresTarjeton = valoresTarjeton;
	}
	public LinkedHashMap<String, LinkedHashMap<String, String>> getDatosFormulariosTarjeton() {
		return datosFormulariosTarjeton;
	}
	public void setDatosFormulariosTarjeton(LinkedHashMap<String, LinkedHashMap<String, String>> datosFormulariosTarjeton) {
		this.datosFormulariosTarjeton = datosFormulariosTarjeton;
	}
	
	public ArrayList<DatoCompareciente> getComparecientesCompletos() {
		return comparecientesCompletos;
	}
	public void setComparecientesCompletos(ArrayList<DatoCompareciente> comparecientesCompletos) {
		if(this.comparecientesCompletos != null){
			this.comparecientesCompletos.addAll(comparecientesCompletos);
		}else{
			this.comparecientesCompletos = comparecientesCompletos;
		}
	}
	
	
}
