package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tbbsnm33b")
public class DocumentoNotarialParcialBackup implements Serializable {

	private static final long serialVersionUID = -1111596836026537252L;

	@Id
	private String iddocnotpar;

	//@Temporal(TemporalType.DATE)
	private Date fecha;

	private String idsesion;

	private Timestamp tmstmp;

    @Lob()
	private String txtdoc;

	private int version;
	
	@Column(columnDefinition="INT")
	private Boolean iscerrado;

	private String idescritura;
	
	@Transient
	private Boolean isGenerarMaster;

    public DocumentoNotarialParcialBackup() {
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

	public int getVersion() {
		return this.version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getIdescritura() {
		return idescritura;
	}
	
	public void setIdescritura(String idescritura) {
		this.idescritura = idescritura;
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
