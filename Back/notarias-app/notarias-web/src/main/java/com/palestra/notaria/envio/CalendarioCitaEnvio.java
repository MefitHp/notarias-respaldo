package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.dato.DatoCita;
import com.palestra.notaria.dato.DatoInvitadoCita;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;
import com.palestra.notaria.modelo.Expediente;

public class CalendarioCitaEnvio extends GenericEnvio{
	
	private DatoCita datocita=null;
	
	private String statusCita;
	
	private ArrayList<CalendarioCita> listaCitas=null;
	
	private ArrayList<DatoInvitadoCita> comboInvitados=null;
	
	private ArrayList<DocumentoNotarialMaster> comboDocumentos=null;
	
	private String nombreInvitado;
	
	private Expediente expediente;

	public DatoCita getDatocita() {
		return datocita;
	}

	public void setDatocita(DatoCita datocita) {
		this.datocita = datocita;
	}

	public ArrayList<CalendarioCita> getListaCitas() {
		return listaCitas;
	}
	
	public void setListaCitas(ArrayList<CalendarioCita> listaCitas) {
		this.listaCitas = listaCitas;
	}

	public ArrayList<DatoInvitadoCita> getComboInvitados() {
		return comboInvitados;
	}
	
	public void setComboInvitados(ArrayList<DatoInvitadoCita> comboInvitados) {
		this.comboInvitados = comboInvitados;
	}
	
	public ArrayList<DocumentoNotarialMaster> getComboDocumentos() {
		return comboDocumentos;
	}
	
	public void setComboDocumentos(
			ArrayList<DocumentoNotarialMaster> comboDocumentos) {
		this.comboDocumentos = comboDocumentos;
	}
	
	public String getNombreInvitado() {
		return nombreInvitado;
	}
	
	public void setNombreInvitado(String nombreInvitado) {
		this.nombreInvitado = nombreInvitado;
	}
	
	public Expediente getExpediente() {
		return expediente;
	}
	
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	
	public String getStatusCita() {
		return statusCita;
	}
	
	public void setStatusCita(String statusCita) {
		this.statusCita = statusCita;
	}

}
