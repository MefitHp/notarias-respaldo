package com.palestra.notaria.uif.core.wrapper;

import java.util.Date;

import com.palestra.notaria.uif.core.models.Persona;

public class UifWrapper {

	private Long id;
	private Persona persona;
    private Long libro;
    private Long escritura;
    private Long expediente;
    private String status="pendiente";
    private String archivo;
    private String acto;
    private String tipocompareciente;
    private Date fecha;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public Long getLibro() {
		return libro;
	}
	public void setLibro(Long libro) {
		this.libro = libro;
	}
	public Long getEscritura() {
		return escritura;
	}
	public void setEscritura(Long escritura) {
		this.escritura = escritura;
	}
	public Long getExpediente() {
		return expediente;
	}
	public void setExpediente(Long expediente) {
		this.expediente = expediente;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getArchivo() {
		return archivo;
	}
	public void setArchivo(String archivo) {
		this.archivo = archivo;
	}
	public String getActo() {
		return acto;
	}
	public void setActo(String acto) {
		this.acto = acto;
	}
	public String getTipocompareciente() {
		return tipocompareciente;
	}
	public void setTipocompareciente(String tipocompareciente) {
		this.tipocompareciente = tipocompareciente;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
    
}
