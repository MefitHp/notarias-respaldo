package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm67")
public class Contacto implements Serializable {

	private static final long serialVersionUID = 4978961005625841575L;

	@Id
	private String idcontacto;
	
	
	@ManyToOne
	@JoinColumn(name="idpersona")
	private Persona persona;
	
	private String dstelefono;
	
	private String dscorreoelectronico;
	
	private String idsesion;
	
	private Timestamp tmstmp;

	public String getIdcontacto() {
		return idcontacto;
	}

	public void setIdcontacto(String idcontacto) {
		this.idcontacto = idcontacto;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public String getDstelefono() {
		return dstelefono;
	}

	public void setDstelefono(String dstelefono) {
		this.dstelefono = dstelefono;
	}

	public String getDscorreoelectronico() {
		return dscorreoelectronico;
	}

	public void setDscorreoelectronico(String dscorreoelectronico) {
		this.dscorreoelectronico = dscorreoelectronico;
	}

	public String getIdsesion() {
		return idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	
}
