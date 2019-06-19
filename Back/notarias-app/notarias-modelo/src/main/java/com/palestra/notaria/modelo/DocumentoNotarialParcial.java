package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

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
import javax.persistence.Transient;


/**
 * The persistent class for the tbbsnm33 database table.
 * 
 */
@Entity
@Table(name="tbbsnm33")
public class DocumentoNotarialParcial implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iddocnotpar;
	
    @Temporal( TemporalType.DATE)
	private Date fecha;

	private String idsesion;

	private Timestamp tmstmp;

    @Lob()
	private String txtdoc;

	private Integer version;
	
	@Column(columnDefinition="INT")
	private Boolean iscerrado;

	//bi-directional many-to-one association to Escritura
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idescritura")
	private Escritura escritura;
	
	@Transient
	private Boolean isGenerarMaster;

    public DocumentoNotarialParcial() {
    }

	public String getIddocnotpar() {
		return this.iddocnotpar;
	}

	public void setIddocnotpar(String iddocnotpar) {
		this.iddocnotpar = iddocnotpar;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public String getTxtdoc() {
		return this.txtdoc;
	}

	public void setTxtdoc(String txtdoc) {
		this.txtdoc = txtdoc;
	}

	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Escritura getEscritura() {
		return escritura;
	}
	
	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}
	
	public Boolean getIscerrado() {
		return iscerrado;
	}
	
	public void setIscerrado(Boolean iscerrado) {
		this.iscerrado = iscerrado;
	}
	
	public Boolean getIsGenerarMaster() {
		return isGenerarMaster;
	}
	
	public void setIsGenerarMaster(Boolean isGenerarMaster) {
		this.isGenerarMaster = isGenerarMaster;
	}
}