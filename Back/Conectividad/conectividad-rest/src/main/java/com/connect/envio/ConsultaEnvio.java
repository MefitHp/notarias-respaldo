package com.connect.envio;

import java.util.ArrayList;
import java.util.List;

import com.connect.modelo.VistaExpediente;

public class ConsultaEnvio {
	
	private List<VistaExpediente> vistaExpedienteList = new ArrayList<VistaExpediente>(); 
	
	private String estatus = null;
	
	private boolean exito = false;
	
	private VistaExpediente vistaExpediente = null;
	
	private String carpeta = null;
	
	private List<String> archivosList = new ArrayList<String>();
	
	private Long totalPags = null;
	
	private String numExpediente = null;
	
	public String getNumExpediente() {
		return numExpediente;
	}
	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}
	public Long getTotalPags() {
		return totalPags;
	}
	public void setTotalPags(Long totalPags) {
		this.totalPags = totalPags;
	}
	
	public String getCarpeta() {
		return carpeta;
	}
	public void setCarpeta(String carpeta) {
		this.carpeta = carpeta;
	}
	public List<String> getArchivosList() {
		return archivosList;
	}
	public void setArchivosList(List<String> archivosList) {
		this.archivosList = archivosList;
	}
	public VistaExpediente getVistaExpediente() {
		return vistaExpediente;
	}
	public void setVistaExpediente(VistaExpediente vistaExpediente) {
		this.vistaExpediente = vistaExpediente;
	}

	public List<VistaExpediente> getVistaExpedienteList() {
		return vistaExpedienteList;
	}

	public void setVistaExpedienteList(List<VistaExpediente> vistaExpedienteList) {
		this.vistaExpedienteList = vistaExpedienteList;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}
}
