package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Firma;

public class FirmaEnvio extends GenericEnvio implements Serializable{
	
	private static final long serialVersionUID = 2185926627004588479L;
	
	
	private Escritura escritura = null;
	private Expediente expediente = null;
	private String idacto = null;
	private Boolean solicitaPreventivo = null;
	private Compareciente compareciente= null;
	private Firma firma= null;
	private Boolean completas = null;
	private ArrayList<Compareciente> comparecientes = null;
	
	
	public Compareciente getCompareciente() {
		return compareciente;
	}
	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
	}
	public Firma getFirma() {
		return firma;
	}
	public void setFirma(Firma firma) {
		this.firma = firma;
	}
	public ArrayList<Compareciente> getComparecientes() {
		return comparecientes;
	}
	public void setComparecientes(ArrayList<Compareciente> comparecientes) {
		this.comparecientes = comparecientes;
	}
	public Escritura getEscritura() {
		return escritura;
	}
	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}
	public Boolean getSolicitaPreventivo() {
		return solicitaPreventivo;
	}
	public void setSolicitaPreventivo(Boolean solicitaPreventivo) {
		this.solicitaPreventivo = solicitaPreventivo;
	}
	public Boolean getCompletas() {
		return completas;
	}
	public void setCompletas(Boolean completas) {
		this.completas = completas;
	}
	public String getIdacto() {
		return idacto;
	}
	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}
	
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	
	
	
	
	
}
