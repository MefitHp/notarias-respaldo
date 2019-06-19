package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the tbcfgm10 database table.
 * 
 */
@Entity
@Table(name="tbcfgm10")
public class Grupo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idgrupo;

	private String dsgrupo;

	private String idsdescripcion;
	
	@Column(columnDefinition="INT")
	private Boolean isactivo;

	private String idsesion;

	private Timestamp tmstmp;

    public Grupo() {
    }

	public Grupo(String dsgrupo) {
		this.dsgrupo = dsgrupo;
	}

	public String getIdgrupo() {
		return this.idgrupo;
	}

	public void setIdgrupo(String idgrupo) {
		this.idgrupo = idgrupo;
	}

	public String getDsgrupo() {
		return this.dsgrupo;
	}

	public void setDsgrupo(String dsgrupo) {
		this.dsgrupo = dsgrupo;
	}

	public String getIdsdescripcion() {
		return this.idsdescripcion;
	}

	public void setIdsdescripcion(String idsdescripcion) {
		this.idsdescripcion = idsdescripcion;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	
	public Boolean getIsactivo() {
		return isactivo;
	}
	
	public void setIsactivo(Boolean isactivo) {
		this.isactivo = isactivo;
	}
}