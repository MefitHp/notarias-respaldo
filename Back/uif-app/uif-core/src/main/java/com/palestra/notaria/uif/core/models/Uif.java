package com.palestra.notaria.uif.core.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="uifs")
public class Uif implements Serializable{

	 	/**
	 * 
	 */
	private static final long serialVersionUID = -1150119923987532648L;
		@Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
	    private Long id;
		@ManyToOne()
	    private Persona persona;
	    private Long libro;
	    private Long escritura;
	    private Long expediente;
	    private String status="pendiente";
	    private String archivo;
	    private String acto;
	    private String tipocompareciente;
	    private Date fecha;
	    
	    @Temporal(TemporalType.TIMESTAMP)
		private Date created;
		@Temporal(TemporalType.TIMESTAMP)
		private Date updated;
		
	    
	    
	public Uif() {
		// TODO Auto-generated constructor stub
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	
	@PrePersist
    protected void onCreate() {
    updated = created = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
    updated = new Date();
    }
    
    
	
	public enum TipoPersonas{
		
		FISICA("fisica"),MORAL("moral");
		
		private String prefijo;
		private TipoPersonas(String s){
			prefijo = s;
		}
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

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
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

	public Persona getIdpersona() {
		return persona;
	}

	public void setIdpersona(Persona idpersona) {
		this.persona = idpersona;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	
}





