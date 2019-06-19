package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm76")
@NamedQueries(value={
		@NamedQuery(name="findByObjeto",query="SELECT a FROM AlertaObjeto a where a.idobjeto=:idobjeto")
})

public class AlertaObjeto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3115023861743518639L;
	
	@Id
	private String idalertaobjeto;
		
	private String idobjeto;
	
	private String tipoobjeto;
	
	@Column(columnDefinition="INT")
	
	private Boolean isfinalizado;
	
	private String statusalerta;
	
	private Timestamp updated;
	
	private String idsesion;

	private Timestamp tmstmp;
	
	
	public Timestamp getUpdated() {
		return updated;
	}

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
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

	public String getIdalertaobjeto() {
		return idalertaobjeto;
	}

	public void setIdalertaobjeto(String idalertaobjeto) {
		this.idalertaobjeto = idalertaobjeto;
	}

	

	public String getIdobjeto() {
		return idobjeto;
	}

	public void setIdobjeto(String idobjeto) {
		this.idobjeto = idobjeto;
	}


	public String getTipoobjeto() {
		return tipoobjeto;
	}

	public void setTipoobjeto(String tipoobjeto) {
		this.tipoobjeto = tipoobjeto;
	}

	public Boolean getIsfinalizado() {
		return isfinalizado;
	}

	public void setIsfinalizado(Boolean isfinalizado) {
		this.isfinalizado = isfinalizado;
	}

	public String getStatusalerta() {
		return statusalerta;
	}

	public void setStatusalerta(String statusalerta) {
		this.statusalerta = statusalerta;
	}
	

}
