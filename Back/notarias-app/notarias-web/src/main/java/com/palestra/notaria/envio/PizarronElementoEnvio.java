package com.palestra.notaria.envio;

import java.util.Date;
import java.util.List;

import com.palestra.notaria.dato.DatoAvisoDecena;
import com.palestra.notaria.dato.DatoElementoPizarron;
import com.palestra.notaria.modelo.AvisoDecena;
import com.palestra.notaria.modelo.ControlFolios;
import com.palestra.notaria.modelo.Libro;

public class PizarronElementoEnvio extends GenericEnvio {

	public List<DatoElementoPizarron> pizarrones;
	public ControlFolios controlfolios;
	public DatoElementoPizarron pizarron;
	public List<DatoElementoPizarron> pendientes;
	public List<DatoAvisoDecena> avisos;
	public Boolean actualizatodo;
	
	public Libro libro;
	
	public Integer decena;
	
	public Date ultimafecha;
	
	public String password;
	
	public String idabogado;
	
	
	public List<DatoElementoPizarron> getPizarrones() {
		return pizarrones;
	}
	public void setPizarrones(List<DatoElementoPizarron> pizarrones) {
		this.pizarrones = pizarrones;
	}
	public DatoElementoPizarron getPizarron() {
		return pizarron;
	}
	public void setPizarron(DatoElementoPizarron pizarron) {
		this.pizarron = pizarron;
	}
	public List<DatoElementoPizarron> getPendientes() {
		return pendientes;
	}
	public void setPendientes(List<DatoElementoPizarron> pendientes) {
		this.pendientes = pendientes;
	}
	public Libro getLibro() {
		return libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	public ControlFolios getControlfolios() {
		return controlfolios;
	}
	public void setControlfolios(ControlFolios controlfolios) {
		this.controlfolios = controlfolios;
	}
	public Integer getDecena() {
		return decena;
	}
	public void setDecena(Integer decena) {
		this.decena = decena;
	}
	public Date getUltimafecha() {
		return ultimafecha;
	}
	public void setUltimafecha(Date ultimafecha) {
		this.ultimafecha = ultimafecha;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<DatoAvisoDecena> getAvisos() {
		return avisos;
	}
	public void setAvisos(List<DatoAvisoDecena> avisos) {
		this.avisos = avisos;
	}
	public String getIdabogado() {
		return idabogado;
	}
	public void setIdabogado(String idabogado) {
		this.idabogado = idabogado;
	}
	public Boolean getActualizatodo() {
		return actualizatodo;
	}
	public void setActualizatodo(Boolean actualizatodo) {
		this.actualizatodo = actualizatodo;
	}
	

}
