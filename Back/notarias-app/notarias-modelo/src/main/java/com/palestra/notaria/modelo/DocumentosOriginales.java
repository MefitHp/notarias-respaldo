package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tbbsnm04 database table.
 * 
 */
@Entity
@Table(name="tbbsnm04")
public class DocumentosOriginales implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	private String dsnombre;
	
	private String dsruta;

	private String idsesion;
	
	//bi-directional many-to-one association to Acto
		@ManyToOne(fetch=FetchType.LAZY)
		@JoinColumn(name="idacto")
		private Acto acto;
		
		@Column(columnDefinition="INT")
		private Boolean isentregado;

		@Column(columnDefinition="INT")
		private Boolean isvalidado;
		
	public String getDsnombre() {
			return dsnombre;
		}


		public void setDsnombre(String dsnombre) {
			this.dsnombre = dsnombre;
		}


		public String getIdsesion() {
			return idsesion;
		}


		public void setIdsesion(String idsesion) {
			this.idsesion = idsesion;
		}


		public Acto getActo() {
			return acto;
		}


		public void setActo(Acto acto) {
			this.acto = acto;
		}


		public Boolean getIsentregado() {
			return isentregado;
		}


		public void setIsentregado(Boolean isentregado) {
			this.isentregado = isentregado;
		}


		public Boolean getIsvalidado() {
			return isvalidado;
		}


		public void setIsvalidado(Boolean isvalidado) {
			this.isvalidado = isvalidado;
		}


		private Timestamp tmstmp;


    public DocumentosOriginales() {
    }


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getDsruta() {
		return dsruta;
	}


	public void setDsruta(String dsruta) {
		this.dsruta = dsruta;
	}
	
}