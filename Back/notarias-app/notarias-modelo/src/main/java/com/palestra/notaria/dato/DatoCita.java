package com.palestra.notaria.dato;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;

public class DatoCita implements Serializable{

	private static final long serialVersionUID = 395469006361973575L;
	
	private CalendarioCita calendarioCita;
	
	private ArrayList<DatoInvitadoCita> invitados;
	
	private ArrayList<DocumentoNotarialMaster> documentos;
	
	public CalendarioCita getCalendarioCita() {
		return calendarioCita;
	}
	
	public void setCalendarioCita(CalendarioCita calendarioCita) {
		this.calendarioCita = calendarioCita;
	}

	public ArrayList<DatoInvitadoCita> getInvitados() {
		return invitados;
	}

	public void setInvitados(ArrayList<DatoInvitadoCita> invitados) {
		this.invitados = invitados;
	}

	public ArrayList<DocumentoNotarialMaster> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(ArrayList<DocumentoNotarialMaster> documentos) {
		this.documentos = documentos;
	}
}
